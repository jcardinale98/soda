/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.soda.controller;

import com.soda.domain.Producto;
import com.soda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos();
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/modificar/{id_listadoProductos}")
    public String modificar(@PathVariable("id_listadoProductos") Integer id_listadoProductos, Model model) {
        var producto = productoService.getProducto(id_listadoProductos).orElse(null);
        model.addAttribute("producto", producto);
        return "producto/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id_listadoProductos", required = false) Integer id_listadoProductos,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("categoria") String categoria,
            @RequestParam(value = "disponible", defaultValue = "true") Boolean disponible,
            @RequestParam(value = "imagen", required = false) String imagen,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Crear o actualizar producto
            Producto producto;
            if (id_listadoProductos != null && id_listadoProductos > 0) {
                producto = productoService.getProducto(id_listadoProductos).orElse(new Producto());
            } else {
                producto = new Producto();
            }

            // Actualizar datos
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCategoria(categoria);
            producto.setDisponible(disponible);

            // Guardar
            productoService.save(producto, imagenFile);
            redirectAttributes.addFlashAttribute("todoOk", "Producto guardado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id_listadoProductos") Integer id_listadoProductos,
                          RedirectAttributes redirectAttributes) {
        try {
            if (id_listadoProductos == null || id_listadoProductos <= 0) {
                redirectAttributes.addFlashAttribute("error", "ID de producto inválido");
                return "redirect:/producto/listado";
            }

            productoService.delete(id_listadoProductos);
            redirectAttributes.addFlashAttribute("todoOk", "Producto eliminado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/producto/listado";
    }
    
    @GetMapping("/")
    public String inicio() {
        return "redirect:/producto/listado";
    }
}
