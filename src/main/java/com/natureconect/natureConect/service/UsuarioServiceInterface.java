package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Usuario;

/**
 *  interfaz para el servidio de usuario
 */
public interface UsuarioServiceInterface {
    public Usuario register(String nombre, String email, String password);
    public Usuario login(String nombre,String password);
}
