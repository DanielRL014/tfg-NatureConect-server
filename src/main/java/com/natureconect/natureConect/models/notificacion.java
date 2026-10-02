package com.natureconect.natureConect.models;

import java.util.List;

/**
 * clase para las notificaciones
 */
public class notificacion {
    private String id_publicacion;
    private List<Like> likes;

    public notificacion(String id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(String id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
}
