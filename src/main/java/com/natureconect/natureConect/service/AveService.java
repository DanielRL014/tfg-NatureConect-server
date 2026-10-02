package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Ave;
import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.repository.AveRepository;
import com.natureconect.natureConect.repository.FamiliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * servicio para ave
 */
@Service
public class AveService implements AveServiceInterface {
    /**
     * repositorio de las aves
     */
    @Autowired
    private AveRepository averp;
    /**
     * repositorio de las familias
     */
    @Autowired
    private FamiliaRepository farp;

    /**
     * metodo para obtener todas las aves
     * @return
     * @throws IOException
     */
    @Override
    public List<Ave> obtenerAves() throws IOException {
        return (List<Ave>) averp.findAll();
    }

    /**
     * metodo para buscar un ave por su nombre
     * @param texto nombre a buscar
     * @return
     * @throws IOException
     */
    @Override
    public List<Ave> buscarAves(String texto) throws IOException {
        List<Ave> aves;

        if (texto != null && !texto.trim().isEmpty()) {
            aves = averp.findByNombreComunContaining(texto) ;
        } else {
            aves = (List<Ave>) averp.findAll();
        }

        return aves;
    }

    /**
     * metodo para obtener las aves de una familia
     * @param texto nombre de la familia
     * @return
     * @throws IOException
     */
    @Override
    public List<Ave> filtarFamilia(String texto) throws IOException {
        List<Ave> aves;

        if (texto != null && !texto.trim().isEmpty()) {
            aves = new ArrayList<Ave>( farp.findByNombre(texto).getAves());
        } else {
            aves = (List<Ave>) averp.findAll();
        }

        return aves;
    }

}
