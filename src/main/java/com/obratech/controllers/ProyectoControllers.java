package com.obratech.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obratech.entity.Proyecto;
import com.obratech.entity.Usuario;
import com.obratech.repository.ProyectoRepository;
import com.obratech.service.ProyectoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/proyectos")
public class ProyectoControllers {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Mostrar formulario para publicar un nuevo proyecto
    @GetMapping("/publicar")
    public String mostrarFormularioProyecto(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("usuario", usuario);
        return "publicar-proyecto-new";
    }

    // Publicar (guardar) un nuevo proyecto
    @PostMapping("/publicar")
    public String publicarProyecto(
            @ModelAttribute Proyecto proyecto,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Estados automáticos
        proyecto.setEstadoAsignacion("Sin asignar");
        proyecto.setEstadoEjecucion("Pendiente");
        proyecto.setFechaCreacion(java.time.LocalDateTime.now());
        proyecto.setCliente(usuario);

        // Fecha por defecto si no se especifica
        if (proyecto.getFechaInicio() == null) {
            proyecto.setFechaInicio(LocalDate.now());
        }

        // Guardar proyecto
        proyectoService.publicarProyecto(proyecto, usuario);

        return "redirect:/proyectos/mis-proyectos";
    }

    // Ver todos los proyectos del cliente actual
    @GetMapping("/mis-proyectos")
    public String verMisProyectos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Proyecto> proyectos = proyectoRepository.findAll();
        proyectos = proyectos.stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getId().equals(usuario.getId()))
                .toList();

        long enProgreso = proyectos.stream()
                .filter(p -> "En Progreso".equals(p.getEstadoEjecucion()))
                .count();

        long completados = proyectos.stream()
                .filter(p -> "Completado".equals(p.getEstadoEjecucion()))
                .count();

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalProyectos", proyectos.size());
        model.addAttribute("proyectosEnProgreso", enProgreso);
        model.addAttribute("proyectosCompletados", completados);

        return "mis-proyectos";
    }

    // Ver detalles de un proyecto
    @GetMapping("/{id:\\d+}")
    public String verDetallesProyecto(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto == null || (proyecto.getCliente() != null && !proyecto.getCliente().getId().equals(usuario.getId()))) {
            return "redirect:/proyectos/mis-proyectos";
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("usuario", usuario);
        return "detalles-proyecto";
    }

    // Editar proyecto
    @GetMapping("/{id:\\d+}/editar")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto == null || (proyecto.getCliente() != null && !proyecto.getCliente().getId().equals(usuario.getId()))) {
            return "redirect:/proyectos/mis-proyectos";
        }

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("usuario", usuario);
        return "editar-proyecto";
    }

    // Guardar cambios de proyecto
    @PostMapping("/{id:\\d+}/editar")
    public String editarProyecto(
            @PathVariable Long id,
            @ModelAttribute Proyecto proyectoEditado,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto == null || (proyecto.getCliente() != null && !proyecto.getCliente().getId().equals(usuario.getId()))) {
            return "redirect:/proyectos/mis-proyectos";
        }

        proyecto.setTitulo(proyectoEditado.getTitulo());
        proyecto.setDescripcion(proyectoEditado.getDescripcion());
        proyecto.setUbicacion(proyectoEditado.getUbicacion());
        proyecto.setTipoProyecto(proyectoEditado.getTipoProyecto());
        proyecto.setPresupuesto(proyectoEditado.getPresupuesto());
        proyecto.setFechaInicio(proyectoEditado.getFechaInicio());
        proyecto.setFechaEntrega(proyectoEditado.getFechaEntrega());
        proyecto.setPlazoEstimado(proyectoEditado.getPlazoEstimado());
        proyecto.setAreaTotal(proyectoEditado.getAreaTotal());

        proyectoRepository.save(proyecto);

        return "redirect:/proyectos/" + id;
    }

    // Eliminar proyecto
    @PostMapping("/{id:\\d+}/eliminar")
    public String eliminarProyecto(
            @PathVariable Long id,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        if (proyecto != null && (proyecto.getCliente() == null || proyecto.getCliente().getId().equals(usuario.getId()))) {
            proyectoRepository.deleteById(id);
        }

        return "redirect:/proyectos/mis-proyectos";
    }
}
