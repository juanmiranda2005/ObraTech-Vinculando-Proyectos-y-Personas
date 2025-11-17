package com.obratech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.obratech.entity.Trabajador;
import com.obratech.entity.Usuario;
import com.obratech.repository.TrabajadorRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorControllers {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    // Listar todos los trabajadores
    @GetMapping
    public String listarTrabajadores(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Trabajador> trabajadores = trabajadorRepository.findAll();
        model.addAttribute("trabajadores", trabajadores);
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalTrabajadores", trabajadores.size());

        return "listar-trabajadores";
    }

    // Ver detalles de un trabajador
    @GetMapping("/{id}")
    public String verDetallesTrabajador(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Trabajador trabajador = trabajadorRepository.findById(id).orElse(null);

        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        model.addAttribute("trabajador", trabajador);
        model.addAttribute("usuario", usuario);
        return "detalles-trabajador";
    }

    // Mostrar formulario para crear trabajador
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Solo contratistas pueden crear trabajadores
        if (!usuario.getRole().equals("ROLE_CONTRACTOR")) {
            return "redirect:/desboard-trabajador";
        }

        model.addAttribute("trabajador", new Trabajador());
        model.addAttribute("usuario", usuario);
        return "crear-trabajador";
    }

    // Crear nuevo trabajador
    @PostMapping("/crear")
    public String crearTrabajador(
            @ModelAttribute Trabajador trabajador,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_CONTRACTOR")) {
            return "redirect:/desboard-trabajador";
        }

        try {
            // Guardar trabajador
            trabajadorRepository.save(trabajador);
            model.addAttribute("mensaje", "Trabajador creado exitosamente");
            return "redirect:/trabajadores";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el trabajador: " + e.getMessage());
            model.addAttribute("trabajador", trabajador);
            return "crear-trabajador";
        }
    }

    // Mostrar formulario para editar trabajador
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_CONTRACTOR")) {
            return "redirect:/desboard-trabajador";
        }

        Trabajador trabajador = trabajadorRepository.findById(id).orElse(null);

        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        model.addAttribute("trabajador", trabajador);
        model.addAttribute("usuario", usuario);
        return "editar-trabajador";
    }

    // Guardar cambios de trabajador
    @PostMapping("/{id}/editar")
    public String editarTrabajador(
            @PathVariable Long id,
            @ModelAttribute Trabajador trabajadorEditado,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_CONTRACTOR")) {
            return "redirect:/desboard-trabajador";
        }

        Trabajador trabajador = trabajadorRepository.findById(id).orElse(null);

        if (trabajador == null) {
            return "redirect:/trabajadores";
        }

        try {
            // Actualizar campos permitidos
            trabajador.setNombre(trabajadorEditado.getNombre());
            trabajador.setApellido(trabajadorEditado.getApellido());
            trabajador.setEmail(trabajadorEditado.getEmail());
            trabajador.setTelefono(trabajadorEditado.getTelefono());
            trabajador.setOficio(trabajadorEditado.getOficio());
            trabajador.setExperiencia(trabajadorEditado.getExperiencia());
            trabajador.setDisponibilidad(trabajadorEditado.getDisponibilidad());

            trabajadorRepository.save(trabajador);
            return "redirect:/trabajadores/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "Error al editar el trabajador: " + e.getMessage());
            model.addAttribute("trabajador", trabajador);
            return "editar-trabajador";
        }
    }

    // Eliminar trabajador
    @PostMapping("/{id}/eliminar")
    public String eliminarTrabajador(
            @PathVariable Long id,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_CONTRACTOR")) {
            return "redirect:/desboard-trabajador";
        }

        try {
            trabajadorRepository.deleteById(id);
        } catch (Exception e) {
            // Log error
        }

        return "redirect:/trabajadores";
    }

    // Buscar trabajadores por oficio
    @GetMapping("/buscar/{oficio}")
    public String buscarPorOficio(
            @PathVariable String oficio,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Trabajador> trabajadores = trabajadorRepository.findAll()
                .stream()
                .filter(t -> t.getOficio() != null && 
                           t.getOficio().toLowerCase().contains(oficio.toLowerCase()))
                .toList();

        model.addAttribute("trabajadores", trabajadores);
        model.addAttribute("usuario", usuario);
        model.addAttribute("oficio", oficio);
        model.addAttribute("totalTrabajadores", trabajadores.size());

        return "listar-trabajadores";
    }

    // Listar trabajadores disponibles
    @GetMapping("/disponibles")
    public String listarDisponibles(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Trabajador> trabajadores = trabajadorRepository.findAll()
                .stream()
                .filter(t -> t.getDisponibilidad() != null && t.getDisponibilidad())
                .toList();

        model.addAttribute("trabajadores", trabajadores);
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalTrabajadores", trabajadores.size());

        return "listar-trabajadores";
    }
}
