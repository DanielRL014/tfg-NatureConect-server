package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Usuario;
import com.natureconect.natureConect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * servicio para los usuarios
 */
@Service
public class UsuarioService implements UsuarioServiceInterface{

    /**
     * repositorio de los usuarios
     */
    @Autowired
    private UsuarioRepository usrp;

    /**
     * metodo para guardar un usuario
     * @param nombre nomrbe del usuario
     * @param email correo del usuario
     * @param password contraseña del usuario
     * @return
     */
    @Override
    public Usuario register(String nombre, String email, String password) {
        try {
            if(usrp.findByEmailUsuario(email).isPresent()){
                throw new IllegalArgumentException("El correo ya está en uso");
            }else {
                if(usrp.findByNombreUsuario(nombre).isPresent()){
                    throw new IllegalArgumentException("El nombre ya está en uso");
                }else {
                    Usuario u = new Usuario();
                    u.setEmailUsuario(email);
                    u.setNombreUsuario(nombre);
                    u.setPassword(password);
                    return usrp.save(u);
                }
            }

        }catch (DataAccessException e){
            System.err.println("Error en acceso a datos: " + e.getMessage());
            throw new RuntimeException("Error en acceso a datos: " + e.getMessage(), e);
        }

    }

    /**
     * metodo pra iniciar sersion
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return
     */
    @Override
    public Usuario login(String nombre, String password) {
        try {
            Optional <Usuario> usuario = usrp.findByNombreUsuario(nombre);
            if(usuario.isPresent()&& BCrypt.checkpw(password, usuario.get().getPassword())){
                return usuario.get();
            }else{
                throw new IllegalArgumentException("Credenciales incorrectas.");
            }
        }catch (DataAccessException e){
            System.err.println("Error en acceso a datos: " + e.getMessage());
            throw new RuntimeException("Error en acceso a datos: " + e.getMessage(), e);
        }
    }
}
