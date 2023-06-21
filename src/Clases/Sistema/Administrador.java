package Clases.Sistema;

import Interfaces.IComportamientoColeccion;

import java.io.Serializable;
/**
 * Esta clase representa un Administrador, que es un tipo de Usuario.
 * Un Administrador tiene un nombre de usuario, una contraseña y un ID de usuario.
 *
 */
public class Administrador extends Usuario implements Serializable {

    public Administrador(String nombreUsuario, String contrasena, int idUsuario) {
        super(nombreUsuario, contrasena, idUsuario);
    }



}
