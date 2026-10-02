package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.Familia;
import com.natureconect.natureConect.service.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * contorler de familia
 */
@RestController
@RequestMapping("/api/familia")
public class FamiliaController {
    /**
     * servicio de familia
     */
    @Autowired
    private FamiliaService familiaService;

    /**
     * metodo que  devuelve las familias
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<?> getFamilias(){
        try {
            List<Familia> familias = familiaService.getFamilias();

            if (familias.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "familias enviadas",
                    "data", familias
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

}
