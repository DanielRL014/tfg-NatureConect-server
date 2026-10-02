package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.investigador;
import com.natureconect.natureConect.repository.InvestigadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * servicio para investigador
 */
@Service
public class InvestigadorService implements InvestigadorServiceInterface {
    /**
     * repositorio de investigador
     */
    @Autowired
    private InvestigadorRepository inrp;

    /**
     * metodo para guardar un investigador
     * @param nombre nombre del investigador
     * @param apellidos apellidos del investigador
     * @param institucion intitucion a la que perteneces
     * @param corrreo correo del investigador
     * @param password contraseña del investigadoe
     * @return
     */
    @Override
    public investigador registrar(String nombre,String apellidos,String institucion ,String corrreo,String password) {
        try {
            if(inrp.findByCorreo(corrreo).isPresent()){
                throw new IllegalArgumentException("El correo ya está en uso");
            }else{
                investigador investigador = new investigador();
                investigador.setNombre(nombre);
                investigador.setApellidos(apellidos);
                investigador.setInstitucion(institucion);
                investigador.setCorreo(corrreo);
                investigador.setPassword(password);
                return inrp.save(investigador);
            }

        }catch (DataAccessException e){
            System.err.println("Error en acceso a datos: " + e.getMessage());
            throw new RuntimeException("Error en acceso a datos: " + e.getMessage(), e);
        }

    }

    /**
     * metodo para el inicio de seseion del investigador
     * @param correo correo del investigadoe
     * @param password contraseña del investigador
     * @return
     */
    @Override
    public investigador login(String correo,String password) {
        try {
            Optional<investigador> investigador = inrp.findByCorreo(correo);

            if(investigador.isPresent() && BCrypt.checkpw(password, investigador.get().getPassword())){
                return investigador.get();
            }else{
                throw new IllegalArgumentException("El correo no esta en uso o la contraseña es incorrecta");
            }
        }catch (DataAccessException e){
            System.err.println("Error en acceso a datos: " + e.getMessage());
            throw new RuntimeException("Error en acceso a datos: " + e.getMessage(), e);
        }
    }
}
