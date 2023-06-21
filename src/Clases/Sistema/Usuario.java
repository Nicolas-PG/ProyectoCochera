package Clases.Sistema;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase abstracta con los datos de un usuario de la que heredan
 * administrador y empleado.
 */
public abstract class Usuario implements Serializable {

    private String nombreUsuario;
    private String contrasena;
    private int idUsuario;
    private boolean estado;

    public Usuario(String nombreUsuario, String contrasena, int idUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.idUsuario = idUsuario;
        this.estado = true;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean verificarContrasena(String contrasena){
        return this.contrasena.equals(contrasena);
    }

    @Override
    public String toString() {
        return "Usuario="+ nombreUsuario + "\n" +
                "contrasena="+ contrasena + "\n" +
                "idUsuario=" + idUsuario +"\n" +
                "estado=" + estado +"\n" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario || Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        //return Objects.hash(nombreUsuario, contrasena, idUsuario, estado);
        return 1;
    }
}
