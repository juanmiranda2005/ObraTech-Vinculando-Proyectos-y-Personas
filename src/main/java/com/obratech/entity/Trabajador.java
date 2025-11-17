package com.obratech.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajador")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "oficio")
    private String oficio;

    @Column(name = "experiencia")
    private Integer experiencia;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad = true;

    private LocalDateTime creado = LocalDateTime.now();
    private Long usuarioId;

    public Trabajador() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getOficio() { return oficio; }
    public void setOficio(String oficio) { this.oficio = oficio; }

    public Integer getExperiencia() { return experiencia; }
    public void setExperiencia(Integer experiencia) { this.experiencia = experiencia; }

    public Boolean getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Boolean disponibilidad) { this.disponibilidad = disponibilidad; }

    public LocalDateTime getCreado() { return creado; }
    public void setCreado(LocalDateTime creado) { this.creado = creado; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", oficio='" + oficio + '\'' +
                ", experiencia=" + experiencia +
                ", disponibilidad=" + disponibilidad +
                ", creado=" + creado +
                ", usuarioId=" + usuarioId +
                '}';
    }
}