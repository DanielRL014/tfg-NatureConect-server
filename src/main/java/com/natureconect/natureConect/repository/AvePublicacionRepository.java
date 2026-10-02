package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.AvesPublicacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * repositorio para ave publicacion
 */
@Repository
public interface AvePublicacionRepository extends CrudRepository<AvesPublicacion, Integer> {
}
