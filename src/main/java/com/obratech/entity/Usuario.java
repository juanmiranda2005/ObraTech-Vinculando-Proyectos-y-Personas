package com.obratech.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Este será el correo del usuario
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // 🔹 Contraseña (idealmente encriptada)
    @Column(name = "password", nullable = false)
    private String password;

    // 🔹 Tipo o rol de usuario (ADMIN, CONTRATISTA, CLIENTE, etc.)
    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER";

    // 🔹 Fecha de creación (opcional pero útil)
    @Column(name = "creado", nullable = false)
    private LocalDateTime creado;

    public Usuario() {}

    @PrePersist
    protected void onCreate() {
        if (this.creado == null) {
            this.creado = LocalDateTime.now();
        }
    }

    // ✅ Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreado() {
        return creado;
    }
    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", password='(oculto)'" +
                ", creado=" + creado +
                '}';
    }
}
