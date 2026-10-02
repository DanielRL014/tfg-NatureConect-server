package com.natureconect.natureConect.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * modelo de ùblicacion con las relaciones y metodos de getters y setters
 */
@Entity
@Table(name = "publicacion")
public class Publicacion {
    @Id
    @Column(name = "id_publicacion", nullable = false, length = 250)
    private String idPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(name = "id_foto", nullable = false, length = 250)
    private String idFoto;

    @ColumnDefault("0")
    @Column(name = "me_gustas", nullable = false)
    private Integer meGustas;

    @Column(name = "latitud", nullable = false, length = 200)
    private String latitud;

    @Column(name = "longitud", nullable = false, length = 200)
    private String longitud;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @OneToMany(mappedBy = "idPublicacion")
    private Set<AvesPublicacion> avesPublicacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPublicacion")
    private Set<EtiquetaPublicacion> etiquetaPublicacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPublicacion")
    private Set<Like> likes = new LinkedHashSet<>();

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public Integer getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(Integer meGustas) {
        this.meGustas = meGustas;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Set<AvesPublicacion> getAvesPublicacions() {
        return avesPublicacions;
    }

    public void setAvesPublicacions(Set<AvesPublicacion> avesPublicacions) {
        this.avesPublicacions = avesPublicacions;
    }

    public Set<EtiquetaPublicacion> getEtiquetaPublicacions() {
        return etiquetaPublicacions;
    }

    public void setEtiquetaPublicacions(Set<EtiquetaPublicacion> etiquetaPublicacions) {
        this.etiquetaPublicacions = etiquetaPublicacions;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}