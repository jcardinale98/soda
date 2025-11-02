package com.soda.service;

import com.soda.domain.Usuario;
import com.soda.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuario(Integer id_listadoUsuarios) {
        return usuarioRepository.findById(id_listadoUsuarios);
    }

    @Transactional
    public void save(Usuario usuario, MultipartFile imagenFile) {
        usuario = usuarioRepository.save(usuario);
        if (imagenFile != null && !imagenFile.isEmpty()) {
            String rutaImagen = firebaseStorageService.cargaImagen(
                    imagenFile, "usuarios", usuario.getId_listadoUsuarios().longValue());
            usuario.setFotografia(rutaImagen);
            usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public void delete(Integer id_listadoUsuarios) {
        if (!usuarioRepository.existsById(id_listadoUsuarios)) {
            throw new IllegalArgumentException("El usuario con ID " + id_listadoUsuarios + " no existe");
        }
        try {
            usuarioRepository.deleteById(id_listadoUsuarios);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el usuario, tiene dependencias asociadas");
        }
    }
}
