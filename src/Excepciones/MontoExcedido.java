package Excepciones;

public class MontoExcedido extends Exception{

    private double montoARetirar;

    public MontoExcedido(double montoARetirar) {
        super("Tiene $"  + montoARetirar + " en caja." + " Por favor vacie la caja." );
        this.montoARetirar = montoARetirar;
    }
}
