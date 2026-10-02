package com.natureconect.natureConect.models;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * clase de una publicacion y un booleano diciendo si tiene like o no
 */
public class publicacionConLike {
    private String idPublicacion;
    private Usuario idUsuario;
    private String idFoto;
    private Integer meGustas;
    private String latitud;
    private String longitud;
    private LocalDate fecha;
    private Set<AvesPublicacion> avesPublicacions = new LinkedHashSet<>();
    private Set<EtiquetaPublicacion> etiquetaPublicacions = new LinkedHashSet<>();
    private Set<Like> likes = new LinkedHashSet<>();
    private boolean hasLiked;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }
}
