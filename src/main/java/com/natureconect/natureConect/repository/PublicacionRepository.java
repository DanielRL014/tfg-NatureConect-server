package com.natureconect.natureConect.repository;


import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * repositorio para publicacion
 */
@Repository
public interface PublicacionRepository extends CrudRepository<Publicacion, String> {

    @Query("SELECT p FROM Publicacion p " +
            "WHERE NOT EXISTS (" +
            "   SELECT a FROM AvesPublicacion ap " +
            "   JOIN ap.idAve a " +
            "   WHERE ap.idPublicacion = p " +
            "   AND a.proteccion IN ('Vulnerable', 'En peligro de extinción')" +
            ")")
    List<Publicacion> findPublicacionesSinAvesProtegidas();

    List<Publicacion> findByIdUsuario(Usuario usuario);

    @Query("SELECT DISTINCT p FROM Publicacion p " +
            "JOIN AvesPublicacion ap ON ap.idPublicacion = p " +
            "JOIN Ave a ON ap.idAve = a " +
            "WHERE LOWER(a.nombreComun) LIKE LOWER(CONCAT( :texto, '%')) AND a.proteccion NOT IN ('Vulnerable', 'En peligro de extinción')")
    List<Publicacion> findDistinctByNombreComunAveContaining(@Param("texto") String texto);

    @Query("SELECT DISTINCT p FROM Publicacion p " +
            "JOIN AvesPublicacion ap ON ap.idPublicacion = p " +
            "JOIN Ave a ON ap.idAve = a " +
            "WHERE LOWER(a.idFamilizaAve.nombreFamilia) LIKE LOWER(CONCAT( :texto, '%')) " +
            "AND a.proteccion NOT IN ('Vulnerable', 'En peligro de extinción')")
    List<Publicacion> findDistinctByFamiliaAveContaining(@Param("texto") String texto);

    @Query("SELECT p FROM Publicacion p " +
            "JOIN p.avesPublicacions ap " +
            "JOIN ap.idAve a " +
            "WHERE a.id = :idAve AND p.fecha BETWEEN :desde AND :hasta")
    List<Publicacion> findByAveAndFechaBetween(
            @Param("idAve") Long idAve,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );
}
