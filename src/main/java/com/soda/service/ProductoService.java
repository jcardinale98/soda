/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soda.service;

import com.soda.domain.Producto;
import com.soda.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer id_listadoProductos) {
        return productoRepository.findById(id_listadoProductos);
    }

    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {
        producto = productoRepository.save(producto);

        if (!imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.cargaImagen(
                        imagenFile, "productos", producto.getId_listadoProductos().longValue());
                producto.setImagen(rutaImagen);
                productoRepository.save(producto);
            } catch (Exception e) {
                throw new RuntimeException("Error al subir la imagen a Firebase", e);
            }
        }
    }

    @Transactional
    public void delete(Integer id_listadoProductos) {
        if (!productoRepository.existsById(id_listadoProductos)) {
            throw new IllegalArgumentException("El producto con ID " + id_listadoProductos + " no existe");
        }
        try {
            productoRepository.deleteById(id_listadoProductos);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el producto, tiene dependencias asociadas");
        }
    }
}
