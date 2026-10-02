package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Like;
import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.models.Usuario;
import com.natureconect.natureConect.models.notificacion;
import com.natureconect.natureConect.repository.LikeRepository;
import com.natureconect.natureConect.repository.PublicacionRepository;
import com.natureconect.natureConect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * servicio para las notificaciones
 */
@Service
public class NotificacionesService implements NotificacionesServiceInterface {
    /**
     * repositorio de las publicaciones
     */
    @Autowired
    private PublicacionRepository publicacionRepository;
    /**
     * repositorio para los likes
     */
    @Autowired
    private LikeRepository likeRepository;
    /**
     * repositorio para los usuarios
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * metodo par obtener las notificaciones
     * @param idUsuari identificador del usuario
     * @param fecha fecha desde la que se piden las notificaciones
     * @return
     */
    @Override
    public List<notificacion> obtenerNotificacioens(Integer idUsuari, String fecha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuari);
        if (usuarioOpt.isEmpty()) {
            return List.of(); // o lanzar excepción si prefieres
        }

        Usuario usuario = usuarioOpt.get();
        LocalDate fechaDesde;
        try {
            fechaDesde = LocalDate.parse(fecha);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Usa yyyy-MM-dd.");
        }

        List<Publicacion> publicaciones = publicacionRepository.findByIdUsuario(usuario);
        List<notificacion> notificaciones = new ArrayList<>();

        for (Publicacion pub : publicaciones) {
            List<Like> likes = likeRepository.findByIdPublicacionAndFechaGreaterThanEqual(pub, fechaDesde);
            if (!likes.isEmpty()) {
                notificacion notif = new notificacion(pub.getIdPublicacion());
                notif.setLikes(likes);
                notificaciones.add(notif);
            }
        }

        return notificaciones;
    }

}
