package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.Etiqueta;
import com.natureconect.natureConect.service.EtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * controlador para las etiquetas
 */
@RestController
@RequestMapping("/api/etiqueta")
public class EtiquetaController {
    /**
     * servicio de las equitqueas
     */
    @Autowired
    private EtiquetaService etiquetaService;

    /**
     * metodo que devuelve las aves
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<?> getEtiquetas(){
        try {
            List<Etiqueta> etiquetas = etiquetaService.obtenerEtiquetas();

            if (etiquetas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "etiquetas enviadas",
                    "data", etiquetas
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para buscar una etiqueta
     * @param texto nombre de la etiqueta a buscar
     * @return
     */
    @PostMapping("/buscarEtiqueta")
    public ResponseEntity<?> getEtiquetaB(@RequestParam("texto") String texto){
        try{
            List<Etiqueta> etiquetas = etiquetaService.buscarEtiqueta(texto);

            if (etiquetas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "etiquetas enviadas",
                    "data", etiquetas
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
