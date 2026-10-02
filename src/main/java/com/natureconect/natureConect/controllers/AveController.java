package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.Ave;
import com.natureconect.natureConect.service.AveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * controlador  de ave
 *
 */
@RestController
@RequestMapping("/api/ave")
public class AveController {
    /**
     * servicio de ave
     */
    @Autowired
    private AveService avenService;

    /**
     * metodo para obtener las aves
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<?> getAves(){
        try {
            List<Ave> aves = avenService.obtenerAves();

            if (aves.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "aves enviadas",
                    "data", aves
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para obtener un ave dado el nombre
     * @param texto nombre del ave
     * @return
     */
    @PostMapping ("/buscarAve")
    public ResponseEntity<?> getAves(@RequestParam("texto") String texto){
        try{
            List<Ave> aves = avenService.buscarAves(texto);

            if (aves.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "aves enviadas",
                    "data", aves
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para obtener la aves de una familia
     * @param texto nombre de la famila
     * @return
     */
    @PostMapping ("/filtrarFamilia")
    public ResponseEntity<?> filtrarFamilia(@RequestParam("texto") String texto){
        try{
            List<Ave> aves = avenService.filtarFamilia(texto);

            if (aves.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "aves enviadas",
                    "data", aves
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
