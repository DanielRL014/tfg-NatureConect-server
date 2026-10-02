package com.natureconect.natureConect.service;


import com.natureconect.natureConect.models.Etiqueta;
import com.natureconect.natureConect.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * servicio para etiquetas
 */
@Service
public class EtiquetaService implements EtiquetaServiceInterface {
    /**
     * repositorio de etiquetas
     */
    @Autowired
    private EtiquetaRepository etrp;

    /**
     * metodo para obtener las etiquetas
     * @return
     * @throws IOException
     */
    @Override
    public List<Etiqueta> obtenerEtiquetas() throws IOException {
        return (List<Etiqueta>) etrp.findAll();
    }

    /**
     * metodo para buscar etiquetas por un nombre
     * @param texto nombre abuscar
     * @return
     * @throws IOException
     */
    @Override
    public List<Etiqueta> buscarEtiqueta(String texto) throws IOException {
        List<Etiqueta> etiquetas;

        if (texto != null && !texto.trim().isEmpty()) {
            etiquetas = etrp.findByNombre(texto) ;
        } else {
            etiquetas = (List<Etiqueta>) etrp.findAll();
        }

        return etiquetas;
    }
}
