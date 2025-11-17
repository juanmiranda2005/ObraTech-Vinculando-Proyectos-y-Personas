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

import com.obratech.entity.Contratista;
import com.obratech.entity.Usuario;
import com.obratech.repository.ContratistaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contratistas")
public class ContratistaControllers {

    @Autowired
    private ContratistaRepository contratistaRepository;

    // Listar todos los contratistas
    @GetMapping
    public String listarContratistas(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Contratista> contratistas = contratistaRepository.findAll();
        model.addAttribute("contratistas", contratistas);
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalContratistas", contratistas.size());

        return "listar-contratistas";
    }

    // Ver detalles de un contratista
    @GetMapping("/{id}")
    public String verDetallesContratista(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Contratista contratista = contratistaRepository.findById(id).orElse(null);

        if (contratista == null) {
            return "redirect:/contratistas";
        }

        model.addAttribute("contratista", contratista);
        model.addAttribute("usuario", usuario);
        return "detalles-contratista";
    }

    // Mostrar formulario para crear contratista
    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Solo administradores pueden crear contratistas
        if (!usuario.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/desboard";
        }

        model.addAttribute("contratista", new Contratista());
        model.addAttribute("usuario", usuario);
        return "crear-contratista";
    }

    // Crear nuevo contratista
    @PostMapping("/crear")
    public String crearContratista(
            @ModelAttribute Contratista contratista,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/desboard";
        }

        try {
            // Guardar contratista
            contratistaRepository.save(contratista);
            model.addAttribute("mensaje", "Contratista creado exitosamente");
            return "redirect:/contratistas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el contratista: " + e.getMessage());
            model.addAttribute("contratista", contratista);
            return "crear-contratista";
        }
    }

    // Mostrar formulario para editar contratista
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/desboard";
        }

        Contratista contratista = contratistaRepository.findById(id).orElse(null);

        if (contratista == null) {
            return "redirect:/contratistas";
        }

        model.addAttribute("contratista", contratista);
        model.addAttribute("usuario", usuario);
        return "editar-contratista";
    }

    // Guardar cambios de contratista
    @PostMapping("/{id}/editar")
    public String editarContratista(
            @PathVariable Long id,
            @ModelAttribute Contratista contratistaEditado,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/desboard";
        }

        Contratista contratista = contratistaRepository.findById(id).orElse(null);

        if (contratista == null) {
            return "redirect:/contratistas";
        }

        try {
            // Actualizar campos permitidos
            contratista.setNombre(contratistaEditado.getNombre());
            contratista.setApellido(contratistaEditado.getApellido());
            contratista.setEmail(contratistaEditado.getEmail());
            contratista.setTelefono(contratistaEditado.getTelefono());
            contratista.setEspecialidad(contratistaEditado.getEspecialidad());
            contratista.setExperiencia(contratistaEditado.getExperiencia());
            contratista.setCalificacionPromedio(contratistaEditado.getCalificacionPromedio());

            contratistaRepository.save(contratista);
            return "redirect:/contratistas/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "Error al editar el contratista: " + e.getMessage());
            model.addAttribute("contratista", contratista);
            return "editar-contratista";
        }
    }

    // Eliminar contratista
    @PostMapping("/{id}/eliminar")
    public String eliminarContratista(
            @PathVariable Long id,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/desboard";
        }

        try {
            contratistaRepository.deleteById(id);
        } catch (Exception e) {
            // Log error
        }

        return "redirect:/contratistas";
    }

    // Buscar contratistas por especialidad
    @GetMapping("/buscar/{especialidad}")
    public String buscarPorEspecialidad(
            @PathVariable String especialidad,
            HttpSession session,
            Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Contratista> contratistas = contratistaRepository.findAll()
                .stream()
                .filter(c -> c.getEspecialidad() != null && 
                            c.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase()))
                .toList();

        model.addAttribute("contratistas", contratistas);
        model.addAttribute("usuario", usuario);
        model.addAttribute("especialidad", especialidad);
        model.addAttribute("totalContratistas", contratistas.size());

        return "listar-contratistas";
    }
}
