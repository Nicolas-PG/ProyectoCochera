package Excepciones;

public class UsuarioYaRegistrado extends Exception{

    private String nombreUsuario;
    public UsuarioYaRegistrado(String nombreUsuario) {
        super("El usuario" + nombreUsuario +" ya se cuentra registrado");
        this.nombreUsuario = nombreUsuario;
    }
}
