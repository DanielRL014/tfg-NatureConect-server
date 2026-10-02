package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.Like;
import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * repositorio para like
 */
public interface LikeRepository extends CrudRepository<Like, Integer>{
    Optional<Like> findByIdUsuarioAndIdPublicacion(Usuario usuario, Publicacion publicacion);
    List<Like> findByIdPublicacionAndFechaGreaterThanEqual(Publicacion idPublicacion, LocalDate fecha);
}
