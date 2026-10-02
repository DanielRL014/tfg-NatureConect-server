package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.Ave;
import com.natureconect.natureConect.models.Etiqueta;

import java.io.IOException;
import java.util.List;

/**
 *  interfaz para el servidio de etiqueta
 */
public interface EtiquetaServiceInterface {
    public List<Etiqueta> obtenerEtiquetas() throws IOException;
    public List<Etiqueta> buscarEtiqueta(String texto) throws IOException;
}
