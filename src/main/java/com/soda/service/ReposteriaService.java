package com.soda.service;

import com.soda.domain.Reposteria;
import com.soda.repository.ReposteriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReposteriaService {

    @Autowired
    private ReposteriaRepository reposteriaRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Reposteria> getReposterias() {
        return reposteriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reposteria> getReposteria(Integer id_listadoReposteriaCafe) {
        return reposteriaRepository.findById(id_listadoReposteriaCafe);
    }

    @Transactional
    public void save(Reposteria reposteria, MultipartFile imagenFile) {
        reposteria = reposteriaRepository.save(reposteria);

        if (!imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.cargaImagen(
                        imagenFile, "reposteria", reposteria.getId_listadoReposteriaCafe().longValue());
                reposteria.setImagen(rutaImagen);
                reposteriaRepository.save(reposteria);
            } catch (Exception e) {
                throw new RuntimeException("Error al subir la imagen a Firebase", e);
            }
        }
    }

    @Transactional
    public void delete(Integer id_listadoReposteriaCafe) {
        if (!reposteriaRepository.existsById(id_listadoReposteriaCafe)) {
            throw new IllegalArgumentException("El elemento con ID " + id_listadoReposteriaCafe + " no existe");
        }
        try {
            reposteriaRepository.deleteById(id_listadoReposteriaCafe);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar, tiene dependencias asociadas");
        }
    }
}
