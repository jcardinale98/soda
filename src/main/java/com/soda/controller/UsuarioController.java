package com.soda.controller;

import com.soda.domain.Usuario;
import com.soda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listado";
    }

    @GetMapping("/modificar/{id_listadoUsuarios}")
    public String modificar(@PathVariable("id_listadoUsuarios") Integer id_listadoUsuarios, Model model) {
        var usuario = usuarioService.getUsuario(id_listadoUsuarios).orElse(null);
        model.addAttribute("usuario", usuario);
        return "usuario/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id_listadoUsuarios", required = false) Integer id_listadoUsuarios,
            @RequestParam("username") String username,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("correo") String correo,
            @RequestParam(value = "fotografia", required = false) String fotografia,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Crear o actualizar usuario
            Usuario usuario;
            if (id_listadoUsuarios != null && id_listadoUsuarios > 0) {
                // Actualización - obtener usuario existente
                usuario = usuarioService.getUsuario(id_listadoUsuarios).orElse(new Usuario());
            } else {
                // Nuevo usuario
                usuario = new Usuario();
            }

            // Actualizar datos
            usuario.setUsuario(username);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setCorreo(correo);

            // Guardar
            usuarioService.save(usuario, imagenFile);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario guardado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace(); // Para debugging
        }
        return "redirect:/usuario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id_listadoUsuarios") Integer id_listadoUsuarios,
            RedirectAttributes redirectAttributes) {
        try {
            if (id_listadoUsuarios == null || id_listadoUsuarios <= 0) {
                redirectAttributes.addFlashAttribute("error", "ID de usuario inválido");
                return "redirect:/usuario/listado";
            }

            usuarioService.delete(id_listadoUsuarios);
            redirectAttributes.addFlashAttribute("todoOk", "Usuario eliminado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/usuario/listado";
    }
    
    
    
    

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuario/listado";
    }
}
