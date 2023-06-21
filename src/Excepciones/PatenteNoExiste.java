package Excepciones;


public class PatenteNoExiste extends Exception{

    private String patente;
    public PatenteNoExiste(String patente) {
        super("La patente ingresada: "+ patente + " no existe");
        this.patente = patente;
    }
}
