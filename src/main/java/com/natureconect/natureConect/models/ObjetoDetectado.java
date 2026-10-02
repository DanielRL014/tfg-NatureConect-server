package com.natureconect.natureConect.models;

import java.util.List;

/**
 * clase para los objetos detectados
 */
public class ObjetoDetectado {
    public List<Integer> bbox; // [x1, y1, x2, y2]
    public float confianza_deteccion;
    public String clase;
    public float confianza_clasificacion;
}
