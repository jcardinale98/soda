package com.soda.controller;

import com.soda.domain.Reposteria;
import com.soda.service.ReposteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reposteria")
public class ReposteriaController {

    @Autowired
    private ReposteriaService reposteriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var reposterias = reposteriaService.getReposterias();
        model.addAttribute("reposterias", reposterias);
        return "cafeyreposteria/listado"; // ← CORREGIDO
    }

    @GetMapping("/modificar/{id_listadoReposteriaCafe}")
    public String modificar(@PathVariable("id_listadoReposteriaCafe") Integer id_listadoReposteriaCafe, Model model) {
        var reposteria = reposteriaService.getReposteria(id_listadoReposteriaCafe).orElse(null);
        model.addAttribute("reposteria", reposteria);
        return "cafeyreposteria/modifica"; // ← CORREGIDO
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "id_listadoReposteriaCafe", required = false) Integer id_listadoReposteriaCafe,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam(value = "disponible", defaultValue = "true") Boolean disponible,
            @RequestParam(value = "imagen", required = false) String imagen,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Crear o actualizar repostería
            Reposteria reposteria;
            if (id_listadoReposteriaCafe != null && id_listadoReposteriaCafe > 0) {
                reposteria = reposteriaService.getReposteria(id_listadoReposteriaCafe).orElse(new Reposteria());
            } else {
                reposteria = new Reposteria();
            }

            // Actualizar datos
            reposteria.setDescripcion(descripcion);
            reposteria.setPrecio(precio);
            reposteria.setDisponible(disponible);

            // Guardar
            reposteriaService.save(reposteria, imagenFile);
            redirectAttributes.addFlashAttribute("todoOk", "Elemento guardado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/reposteria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("id_listadoReposteriaCafe") Integer id_listadoReposteriaCafe,
            RedirectAttributes redirectAttributes) {
        try {
            if (id_listadoReposteriaCafe == null || id_listadoReposteriaCafe <= 0) {
                redirectAttributes.addFlashAttribute("error", "ID inválido");
                return "redirect:/reposteria/listado";
            }

            reposteriaService.delete(id_listadoReposteriaCafe);
            redirectAttributes.addFlashAttribute("todoOk", "Elemento eliminado correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/reposteria/listado";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/reposteria/listado";
    }
}
