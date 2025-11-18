
package com.obratech.entity;

import jakarta.persistence.*;

@Entity
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Persona contratista;

    @ManyToOne
    private Proyecto proyecto;

    private int puntuacion; // 1-5

    @Column(length = 2000)
    private String comentario;

    public Calificacion() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getContratista() {
        return contratista;
    }

    public void setContratista(Persona contratista) {
        this.contratista = contratista;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
