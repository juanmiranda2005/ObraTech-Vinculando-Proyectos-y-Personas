package com.obratech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.obratech.entity.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class DesboardController {

    @GetMapping("/desboard")
    public String mostrarDesboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            // No hay sesión activa, redirige al login
            return "redirect:/login";
        }

        // Añadir el usuario al modelo para que Thymeleaf lo reconozca
        model.addAttribute("usuario", usuario);

        // Redireccionar según el rol del usuario
        if (usuario.getRole() != null) {
            switch (usuario.getRole()) {
                case "ROLE_CONTRACTOR":
                    // Dashboard para contratistas
                    return "desboard-contratista";
                case "ROLE_WORKER":
                case "ROLE_USER": // Tratamos ROLE_USER como trabajador
                    // Dashboard para trabajadores
                    return "desboard-trabajador";
                case "ROLE_CLIENT":
                    // Dashboard para clientes
                    return "desboard";
                case "ROLE_ADMIN":
                    // Dashboard para administradores
                    return "desboard";
                default:
                    // Por defecto también va a trabajador
                    return "desboard-trabajador";
            }
        }

        // Dashboard por defecto (trabajador)
        return "desboard-trabajador";
    }
}
