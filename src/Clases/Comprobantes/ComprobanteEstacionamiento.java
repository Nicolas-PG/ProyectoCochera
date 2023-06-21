package Clases.Comprobantes;

import Clases.Cochera.SERVICIOS;
import Clases.Vehiculos.Vehiculo;

import java.time.LocalDateTime;
/**
 * Esta clase representa un Comprobante de Estacionamiento, que hereda de la clase Comprobante.
 * Los objetos creados por esta clase nos va a permitir gestionar los Vehiculos dentro del Estacionamiento
 *
 */
public class ComprobanteEstacionamiento extends Comprobante {

    public ComprobanteEstacionamiento() {
    }

    public ComprobanteEstacionamiento(int nroComprobante, Vehiculo vehiculo, LocalDateTime horarioYFecha, SERVICIOS servicio) {
        super(nroComprobante, vehiculo, horarioYFecha,servicio);
    }


}
