package Clases.Cochera;

import java.io.Serializable;


/**
 * Enumeración que representa diferentes servicios con sus respectivos costos.
 * Implementa la interfaz Serializable.
 */

public enum SERVICIOS implements Serializable {

    CAMBIOACEITE("Cambio aceite",5000),
    ASPIRADO("Aspirado",500),
    LAVADO("Lavado",1500),
    COMPLETO("Completo",6500),
    NINGUNO("Sin servicios", 0);

    private String info;
    private double costo;

    SERVICIOS(String info, int costo) {
        this.info = info;
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }

    public String getInfo() {
        return info;
    }
}
