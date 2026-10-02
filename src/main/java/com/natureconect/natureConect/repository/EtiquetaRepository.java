package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.Etiqueta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repositorio para etiqueta
 */
@Repository
public interface EtiquetaRepository extends CrudRepository<Etiqueta, Integer> {
    @Query("SELECT a FROM Etiqueta a " +
            "WHERE LOWER(a.nombre) LIKE LOWER(CONCAT( :texto, '%')) ")
    List<Etiqueta> findByNombre(@Param("texto") String texto);
}
