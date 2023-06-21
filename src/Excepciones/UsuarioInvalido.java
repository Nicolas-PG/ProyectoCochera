package Excepciones;

public class UsuarioInvalido extends Exception{

    public UsuarioInvalido() {
        super("Usuario o contrasena invalida, intente nuevamente");
    }
}
