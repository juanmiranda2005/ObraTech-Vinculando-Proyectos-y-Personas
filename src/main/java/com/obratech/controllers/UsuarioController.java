package com.obratech.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obratech.entity.Cliente;
import com.obratech.entity.Contratista;
import com.obratech.entity.Usuario;
import com.obratech.repository.ClienteRepository;
import com.obratech.repository.ContratistaRepository;
import com.obratech.service.UsuarioService;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;
    private final ContratistaRepository contratistaRepository;

    public UsuarioController(UsuarioService usuarioService,
            ClienteRepository clienteRepository,
            ContratistaRepository contratistaRepository) {
        this.usuarioService = usuarioService;
        this.clienteRepository = clienteRepository;
        this.contratistaRepository = contratistaRepository;
    }

    // 📌 Registro de usuario
    @PostMapping("/registro")
    public String registerUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String role,
            Model model) {

        System.out.println("📩 Username recibido: " + username);
        System.out.println("🔑 Password recibido: " + password);
        System.out.println("🎭 Role recibido: " + role);

        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(password);

        if (role == null || role.equalsIgnoreCase("none") || role.isBlank()) {
            u.setRole("ROLE_USER");
        } else {
            switch (role.toLowerCase()) {
                case "contratista" -> u.setRole("ROLE_CONTRACTOR");
                case "cliente" -> u.setRole("ROLE_CLIENT");
                default -> u.setRole("ROL_USER");
            }
        }

        try {
            // Guardar usuario
            Usuario saved = usuarioService.register(u);

            // Crear entidad según el rol
            String assignedRole = saved.getRole().toLowerCase();
            if (assignedRole.contains("cliente")) {
                Cliente c = new Cliente();
                clienteRepository.save(c);
            } else if (assignedRole.contains("contratista")) {
                Contratista c = new Contratista();
                contratistaRepository.save(c);
            }

            model.addAttribute("mensaje", "✅ Usuario registrado correctamente");
            return "login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("mensaje", "⚠️ " + e.getMessage());
            return "registro";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensaje", "⚠️ El nombre de usuario ya está registrado.");
            return "registro";
        } catch (Exception e) {
            model.addAttribute("mensaje", "❌ Ocurrió un error inesperado.");
            return "registro";
        }
    }

}
