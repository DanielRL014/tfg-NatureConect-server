package com.natureconect.natureConect.repository;

import org.springframework.data.repository.CrudRepository;
import com.natureconect.natureConect.models.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * repositorio para usuario
 */
@Repository
public interface UsuarioRepository   extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailUsuario(String emailUsuario);
    Optional<Usuario> findByNombreUsuarioAndPassword(String nombreUsuario, String password);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);


}
