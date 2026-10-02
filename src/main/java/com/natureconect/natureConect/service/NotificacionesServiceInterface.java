package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.notificacion;

import java.util.List;

/**
 *  interfaz para el servidio de las notificaciones
 */
public interface NotificacionesServiceInterface {
    public List<notificacion> obtenerNotificacioens(Integer idUsuari, String fecha);
}
