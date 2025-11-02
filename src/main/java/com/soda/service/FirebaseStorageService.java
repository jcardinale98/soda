package com.soda.service;

import com.google.auth.Credentials;
import com.google.auth.ServiceAccountSigner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage.SignUrlOption;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageService {

    // Ajusta estos valores a TU proyecto real de Firebase
    final String BucketName = "tienda-6c05b.appspot.com";
    final String rutaSuperiorStorage = "caso01";
    final String rutaJsonFile = "firebase";
    final String archivoJsonFile = "tienda-6c05b-firebase-adminsdk-1avhq-78f35285a4.json";

    public String cargaImagen(MultipartFile archivoLocalCliente, String carpeta, Long id) {
        try {
            String extension = archivoLocalCliente.getOriginalFilename();
            String fileName = "img" + sacaNumero(id) + extension;

            File file = this.convertToFile(archivoLocalCliente);
            String URL = this.uploadFile(file, carpeta, fileName);
            file.delete();

            return URL;
        } catch (IOException e) {
            throw new RuntimeException("Error subiendo imagen a Firebase", e);
        }
    }

    private String uploadFile(File file, String carpeta, String fileName) throws IOException {
        ClassPathResource json = new ClassPathResource(rutaJsonFile + File.separator + archivoJsonFile);
        BlobId blobId = BlobId.of(BucketName, rutaSuperiorStorage + "/" + carpeta + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        Credentials credentials = GoogleCredentials.fromStream(json.getInputStream());
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return storage
                .signUrl(blobInfo, 3650, TimeUnit.DAYS, SignUrlOption.signWith((ServiceAccountSigner) credentials))
                .toString();
    }

    private File convertToFile(MultipartFile archivoLocalCliente) throws IOException {
        File tempFile = File.createTempFile("img", null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(archivoLocalCliente.getBytes());
        }
        return tempFile;
    }

    private String sacaNumero(long id) {
        return String.format("%019d", id);
    }
}