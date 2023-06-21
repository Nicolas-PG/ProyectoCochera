package Excepciones;

public class EspacioInsuficiente extends Exception {

    public EspacioInsuficiente() {
        super("No hay suficiente espacio en la cochera para ingresar el vehiculo");
    }
}


