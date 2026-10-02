package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.Familia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * repositorio para familia
 */
@Repository
public interface  FamiliaRepository extends CrudRepository<Familia, Integer> {
    @Query("SELECT a FROM Familia a " +
            "WHERE LOWER(a.nombreFamilia) LIKE LOWER(CONCAT('%', :texto, '%')) ")
    Familia findByNombre(@Param("texto") String texto);
}
