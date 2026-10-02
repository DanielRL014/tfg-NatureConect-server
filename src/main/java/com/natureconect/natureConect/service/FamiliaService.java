package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Familia;
import com.natureconect.natureConect.repository.FamiliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * servicio para familia
 */
@Service
public class FamiliaService implements FamiliaServiceInterface {
    /**
     * repositorio para familia
     */
    @Autowired
    private FamiliaRepository farp;

    /**
     * metodo para obtener las familias
     * @return
     */
    @Override
    public List<Familia> getFamilias() {
        return (List<Familia>) farp.findAll();
    }
}
