package com.natureconect.natureConect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * modelo de etiquetas con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "etiquetas")
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etiqueta", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @OneToMany(mappedBy = "idEtiqueta")
    @JsonBackReference
    private Set<EtiquetaPublicacion> etiquetaPublicacions = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<EtiquetaPublicacion> getEtiquetaPublicacions() {
        return etiquetaPublicacions;
    }

    public void setEtiquetaPublicacions(Set<EtiquetaPublicacion> etiquetaPublicacions) {
        this.etiquetaPublicacions = etiquetaPublicacions;
    }

}