package com.obratech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obratech.entity.Usuario;
import com.obratech.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControllers {

    @Autowired
    private UsuarioService usuarioService;

    // 📄 Mostrar la página de login
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String mensaje, Model model) {
        if (mensaje != null) {
            model.addAttribute("success", mensaje);
        }
        return "login"; // tu archivo login.html
    }

    // 📌 Login manual
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        System.out.println("Intentando iniciar sesión con: " + username);

        // Validar campos vacíos
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("mensaje", "⚠️ Usuario y contraseña son obligatorios.");
            return "login";
        }

        try {
            Usuario usuario = usuarioService.autenticar(username, password);

            if (usuario != null) {
                // Guardar el usuario en la sesión
                session.setAttribute("usuario", usuario);
                System.out.println("✅ Usuario autenticado: " + usuario.getUsername() + " | Rol: " + usuario.getRole());

                // Redirigir a tu dashboard
                return "redirect:/desboard";
            } else {
                model.addAttribute("mensaje", "❌ Usuario o contraseña incorrectos.");
                return "login";
            }

        } catch (Exception e) {
            model.addAttribute("mensaje", "⚠️ Ocurrió un error al iniciar sesión.");
            return "login";
        }
    }

    // 🚪 Cerrar sesión (GET)
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // elimina los datos del usuario en sesión
        return "redirect:/login?mensaje=Sesión cerrada correctamente";
    }

    // 🚪 Cerrar sesión (POST)
    @PostMapping("/logout")
    public String cerrarSesionPost(HttpSession session) {
        session.invalidate(); // elimina los datos del usuario en sesión
        return "redirect:/login?mensaje=Sesión cerrada correctamente";
    }
}