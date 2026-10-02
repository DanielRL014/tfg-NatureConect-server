package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.investigador;

/**
 *  interfaz para el servidio de investigador
 */
public interface InvestigadorServiceInterface {
    public investigador registrar(String nombre, String apellidos, String institucion , String corrreo, String password);
    public investigador login(String correo,String password);
}
