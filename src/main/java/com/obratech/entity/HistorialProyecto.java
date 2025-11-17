package com.obratech.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistorialProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    public HistorialProyecto() {}

    public HistorialProyecto(String estadoAnterior, String estadoNuevo, Proyecto proyecto) {
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = LocalDateTime.now();
        this.proyecto = proyecto;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
