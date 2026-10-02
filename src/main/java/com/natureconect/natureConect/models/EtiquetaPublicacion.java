package com.natureconect.natureConect.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * modelo de etiquetas publicacion con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "etiqueta_publicacion")
public class EtiquetaPublicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne( optional = false)
    @JoinColumn(name = "id_etiqueta", nullable = false)
    private Etiqueta idEtiqueta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    @JsonBackReference
    private Publicacion idPublicacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Etiqueta getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Etiqueta idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public Publicacion getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Publicacion idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

}