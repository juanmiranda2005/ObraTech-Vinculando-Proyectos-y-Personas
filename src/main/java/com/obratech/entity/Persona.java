package com.obratech.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    @ManyToMany(mappedBy = "personas", fetch = FetchType.LAZY)
    private Set<Proyecto> proyectos = new HashSet<>();

    public Persona() {}

    public Persona(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}
