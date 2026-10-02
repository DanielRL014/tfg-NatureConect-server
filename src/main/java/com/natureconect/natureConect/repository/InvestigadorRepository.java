package com.natureconect.natureConect.repository;

import com.natureconect.natureConect.models.investigador;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * repositorio para investigador
 */
public interface InvestigadorRepository extends CrudRepository<investigador, Integer> {
    Optional<investigador> findByCorreo(String correo);

    Optional<investigador> findByCorreoAndPassword(String correo, String password);
}
