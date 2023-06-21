package Excepciones;

public class ContrasenaInsegura extends Exception{

    public ContrasenaInsegura() {
        super("La contrasena debe ser mayor a 5 digitos");
    }
}
