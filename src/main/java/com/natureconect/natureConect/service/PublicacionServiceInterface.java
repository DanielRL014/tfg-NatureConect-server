package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.models.publicacionConLike;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *  interfaz para el servidio de las publicaciones
 */
public interface PublicacionServiceInterface {
    public Publicacion crearPublicacion(Integer idUsuario,  String latitud, String longitud) throws IOException;
    public List<Publicacion> obtenerPublicacionesU() throws IOException;
    public Optional<Publicacion> obtenerPublicacion(String idPublicacion) throws IOException;
    public Boolean anadirAvePublicacion(String idPublicacion, Integer idAve) throws IOException;
    public Boolean anadirEtiquetaPublicacion(String idPublicacion, Integer idEtiqueta) throws IOException;
    public List<Publicacion> obtenerPublicacionesUP(Integer idUsuario) throws IOException;
    public Boolean darLike(String idPublicacion, Integer idUsuario) throws IOException;
    public Boolean quitarLike(String idPublicacion, Integer idUsuario) throws IOException;
    public List<Publicacion> buscarPorAve(String texto) throws IOException;
    public List<Publicacion> filtarFamilia(String texto) throws IOException;
    public boolean subir(MultipartFile imagen) throws IOException;
    public publicacionConLike obtenerPublicacionLike(String idPublicacion, Integer idUsuario) throws IOException;
}
