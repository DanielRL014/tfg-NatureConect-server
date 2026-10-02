package com.natureconect.natureConect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * modelo de ave con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "ave")
public class Ave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ave", nullable = false)
    private Integer id;

    @Column(name = "nombre_comun", nullable = false, length = 200)
    private String nombreComun;

    @Column(name = "nombre_cientifico", nullable = false, length = 200)
    private String nombreCientifico;

    @ManyToOne( optional = false)
    @JoinColumn(name = "id_familiza_ave", nullable = false)
    @JsonIgnoreProperties("aves")
    private Familia idFamilizaAve;

    @Column(name = "proteccion", length = 100)
    @JsonIgnore
    private String proteccion;

    @OneToMany(mappedBy = "idAve")
    @JsonBackReference
    private Set<AvesPublicacion> avesPublicacions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public Familia getIdFamilizaAve() {
        return idFamilizaAve;
    }

    public void setIdFamilizaAve(Familia idFamilizaAve) {
        this.idFamilizaAve = idFamilizaAve;
    }

    public String getProteccion() {
        return proteccion;
    }

    public void setProteccion(String proteccion) {
        this.proteccion = proteccion;
    }

    public Set<AvesPublicacion> getAvesPublicacions() {
        return avesPublicacions;
    }

    public void setAvesPublicacions(Set<AvesPublicacion> avesPublicacions) {
        this.avesPublicacions = avesPublicacions;
    }

}