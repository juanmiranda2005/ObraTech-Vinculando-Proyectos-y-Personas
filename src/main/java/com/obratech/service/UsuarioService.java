package com.obratech.service;

import com.obratech.entity.Usuario;
import com.obratech.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<Usuario> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Usuario autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = repo.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // 🔹 comparación directa sin encriptar
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

 
    public Usuario register(Usuario u) {
        // 🔹 Validar nombre de usuario vacío
        if (u.getUsername() == null || u.getUsername().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }

        // 🔹 Validar contraseña vacía
        if (u.getPassword() == null || u.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        // 🔹 Verificar si el nombre de usuario ya existe
        Optional<Usuario> usuarioExistente = repo.findByUsername(u.getUsername());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }

        // 🔹 Si el rol está vacío, asignar uno por defecto
        if (u.getRole() == null || u.getRole().isBlank()) {
            u.setRole("ROLE_USER");
        }

        // 🔹 Guardar usuario
        return repo.save(u);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
