package com.obratech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obratech.entity.Proyecto;
import com.obratech.entity.Usuario;
import com.obratech.repository.ProyectoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Enviar postulación a un proyecto - ACEPTAR GET y POST
    @PostMapping("/postular/{id}")
    public String postularseProyecto(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_WORKER") && !usuario.getRole().equals("ROLE_USER")) {
            return "redirect:/desboard";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto == null) {
            return "redirect:/postulaciones";
        }

        try {
            // Aquí se guardaría la postulación en la base de datos
            // Por ahora solo redirigimos con un mensaje de éxito
            model.addAttribute("mensaje", "¡Postulación enviada exitosamente! El cliente revisará tu solicitud pronto.");
            return "redirect:/proyectos/asignados";
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar tu postulación: " + e.getMessage());
            model.addAttribute("proyecto", proyecto);
            return "trabajadores/detalles-postulacion";
        }
    }

    // Ver todos los proyectos disponibles para postularse
    @GetMapping
    public String verProyectosDisponibles(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_WORKER") && !usuario.getRole().equals("ROLE_USER")) {
            return "redirect:/desboard";
        }

        List<Proyecto> proyectos = proyectoRepository.findAll()
                .stream()
                .filter(p -> p.getFechaLimitePostulacion() != null && 
                           p.getFechaLimitePostulacion().isAfter(java.time.LocalDate.now()))
                .toList();

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalProyectos", proyectos.size());

        return "trabajadores/proyectos-disponibles";
    }

    // Handle POST to root (redirect to avoid 405)
    @PostMapping
    public String postulacionesRoot() {
        return "redirect:/postulaciones";
    }

    // Ver detalles de proyecto para postulación - ONLY GET
    @GetMapping("/ver/{id}")
    public String verDetallesProyecto(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_WORKER") && !usuario.getRole().equals("ROLE_USER")) {
            return "redirect:/desboard";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto == null) {
            return "redirect:/postulaciones";
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("usuario", usuario);
        return "trabajadores/detalles-postulacion";
    }
}
