package com.obratech.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
     private String username;

    @Column(name = "role", nullable = false)
    private String role = "ROLE_USER";

    @Column(name = "creado", nullable = false)
    private LocalDateTime creado;

    public Cliente() {}



    @PrePersist
    protected void onCreate() {
        if (this.creado == null) this.creado = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreado() { return creado; }
    public void setCreado(LocalDateTime creado) { this.creado = creado; }

    
    public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
     public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
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