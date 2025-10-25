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
        return "usuario/modifica"; // ✅ Ruta correcta
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        try {
            usuarioService.save(usuario, imagenFile);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/eliminar/{id_listadoUsuarios}")
    public String eliminar(@PathVariable("id_listadoUsuarios") Integer id_listadoUsuarios, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.delete(id_listadoUsuarios);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuario/listado";
    }

}
