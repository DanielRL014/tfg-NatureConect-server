package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.EtiquetaPublicacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * repositorio para etiqueta publicacion
 */
@Repository
public interface EtiquetaPublicacionRepository extends CrudRepository<EtiquetaPublicacion, Integer> {
}
