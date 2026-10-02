package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.ClasificacionResultado;
import com.natureconect.natureConect.service.modeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * controlador para los modelo
 */
@RestController
@RequestMapping("/api/deteccion")
public class modeloController {
    /**
     * servicio de los modelos
     */
    @Autowired
    private modeloService modeloService;

    /**
     * mdetodo para identificar las aves de una imagen
     * @param file imagen mandad
     * @return
     */
    @PostMapping(value = "/clasificar")
    public ResponseEntity<?> clasificarImagen(@RequestParam("imagen") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("imagen_", ".jpg");
            file.transferTo(tempFile);

            ClasificacionResultado resultado = modeloService.procesarImagen(tempFile);

            tempFile.delete();


            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar la imagen: " + e.getMessage()));
        }
    }
}
