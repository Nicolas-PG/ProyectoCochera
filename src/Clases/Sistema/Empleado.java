package Clases.Sistema;

import java.io.Serializable;


/**
 * La clase Empleado representa a un empleado en el sistema.
 * Hereda de la clase Usuario y es serializable.
 */
public class Empleado extends Usuario implements Serializable{

    public Empleado(String nombreUsuario, String contrasena, int idUsuario) {
        super(nombreUsuario, contrasena, idUsuario);
    }
}
