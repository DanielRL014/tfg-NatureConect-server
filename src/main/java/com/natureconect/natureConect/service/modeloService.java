package com.natureconect.natureConect.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natureconect.natureConect.models.ClasificacionResultado;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * servicio para el uso de los modelos
 */
@Service
public class modeloService {
    /**
     * metodo para lazar la ejecucion de los modelos y devolver los resultados de la identificacion
     * @param imagen imagen en la que se detectan las aves
     * @return
     * @throws IOException
     */
    public ClasificacionResultado procesarImagen(File imagen) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python3", "src/main/python/ia.py", imagen.getAbsolutePath());
        pb.redirectErrorStream(true);

        Process process = pb.start();


        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String jsonLine = null;

        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("{") && line.trim().endsWith("}")) {
                jsonLine = line;
                break;
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("No se encontró una línea con JSON válido en la salida del script.");
        }

        ObjectMapper mapper = new ObjectMapper();
        ClasificacionResultado resultado = mapper.readValue(jsonLine, ClasificacionResultado.class);
        return resultado;

    }
}
