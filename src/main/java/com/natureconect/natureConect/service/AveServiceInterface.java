package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Ave;

import java.io.IOException;
import java.util.List;

/**
 * interfaz para el servidio de ave
 */
public interface AveServiceInterface {
    public List<Ave> obtenerAves() throws IOException;
    public List<Ave> buscarAves(String texto) throws IOException;
    public List<Ave> filtarFamilia(String texto) throws IOException;
}
