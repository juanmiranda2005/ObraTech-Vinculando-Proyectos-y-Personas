package com.obratech.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    private String tipoProyecto;
    private String ubicacion;
    private Double presupuesto;
    private Integer plazoEstimado;
    private LocalDate fechaInicio;
    private LocalDate fechaEntrega;
    private LocalDate fechaLimitePostulacion;
    private LocalDateTime fechaLimite;

    private String estadoAsignacion;
    private String estadoEjecucion;

    private Double areaTotal;
    private Integer numeroPisos;

    @Column(length = 2000)
    private String tipoContratacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "persona_proyecto",
        joinColumns = @JoinColumn(name = "proyecto_id"),
        inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private Set<Persona> personas = new HashSet<>();

    public Proyecto() {}

    public Proyecto(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estadoAsignacion = "Sin asignar";
        this.estadoEjecucion = "Pendiente";
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Integer getPlazoEstimado() {
        return plazoEstimado;
    }

    public void setPlazoEstimado(Integer plazoEstimado) {
        this.plazoEstimado = plazoEstimado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaLimitePostulacion() {
        return fechaLimitePostulacion;
    }

    public void setFechaLimitePostulacion(LocalDate fechaLimitePostulacion) {
        this.fechaLimitePostulacion = fechaLimitePostulacion;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(String estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }

    public String getEstadoEjecucion() {
        return estadoEjecucion;
    }

    public void setEstadoEjecucion(String estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }

    public Double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Integer getNumeroPisos() {
        return numeroPisos;
    }

    public void setNumeroPisos(Integer numeroPisos) {
        this.numeroPisos = numeroPisos;
    }

    public String getTipoContratacion() {
        return tipoContratacion;
    }

    public void setTipoContratacion(String tipoContratacion) {
        this.tipoContratacion = tipoContratacion;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(Set<Persona> personas) {
        this.personas = personas;
    }
}
