package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.Ave;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repositorio para ave
 */
@Repository
public interface AveRepository extends CrudRepository<Ave, Integer> {
    @Query("SELECT a FROM Ave a " +
            "WHERE LOWER(a.nombreComun) LIKE LOWER(CONCAT( :texto, '%')) ")
    List<Ave> findByNombreComunContaining(@Param("texto") String texto);
}
