package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.notificacion;
import com.natureconect.natureConect.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * controlador de las notificacones
 */
@RestController
@RequestMapping("/api/notificaciones")
public class notificacionesController {
    /**
     * servicio para las notificaciones
     */
    @Autowired
    private NotificacionesService notificacionesService;

    /**
     * metodo para obtener las notificaciones
     * @param idUsuario identificador del usuario
     * @param fecha fecha desde la que se quieren las notificaciones
     * @return
     */
    @PostMapping("/notificaciones")
    public ResponseEntity<?> notificaciones( @RequestParam("id_usuario") String idUsuario,
                                             @RequestParam("fecha") String fecha) {
        try {
            Integer id = Integer.parseInt(idUsuario); // convertir a Integer
            List<notificacion> notificaciones = notificacionesService.obtenerNotificacioens(id, fecha);

            if (notificaciones.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "No hay notificaciones desde la fecha proporcionada."
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Notificaciones encontradas",
                    "data", notificaciones
            ));

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }


}
