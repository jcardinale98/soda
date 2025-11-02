package com.soda.controller;

import com.soda.domain.Usuario;
import com.soda.service.UsuarioService;
import java.util.Optional;
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
        model.addAttribute("usuario", new Usuario()); // ← necesario para el modal "agregar"
        return "usuario/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuario(id);
        if (usuarioOpt.isEmpty()) {
            ra.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuario/listado";
        }
        model.addAttribute("usuario", usuarioOpt.get());
        return "usuario/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
                          @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
                          RedirectAttributes ra) {
        try {
            usuarioService.save(usuario, imagenFile);
            ra.addFlashAttribute("todoOk", "Usuario guardado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar el usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id_listadoUsuarios") Integer id, RedirectAttributes ra) {
        try {
            usuarioService.delete(id);
            ra.addFlashAttribute("todoOk", "Usuario eliminado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/usuario/listado";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuario/listado";
    }
}
