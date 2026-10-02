package com.natureconect.natureConect.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * modelo de familia con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "familia")
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_familia", nullable = false)
    private Integer id;

    @Column(name = "nombre_familia", nullable = false, length = 200)
    private String nombreFamilia;

    @OneToMany(mappedBy = "idFamilizaAve")
    private Set<Ave> aves = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public Set<Ave> getAves() {
        return aves;
    }

    public void setAves(Set<Ave> aves) {
        this.aves = aves;
    }

}