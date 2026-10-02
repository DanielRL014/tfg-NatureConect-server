package com.natureconect.natureConect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * modelo de ave publicacion con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "aves_publicacion")
public class AvesPublicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    @JsonBackReference
    private Publicacion idPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ave", nullable = false)
    private Ave idAve;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publicacion getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Publicacion idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Ave getIdAve() {
        return idAve;
    }

    public void setIdAve(Ave idAve) {
        this.idAve = idAve;
    }

}