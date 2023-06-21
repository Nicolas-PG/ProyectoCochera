package Clases.Comprobantes;

import Clases.Cochera.SERVICIOS;
import Clases.Vehiculos.Vehiculo;
import Interfaces.IToJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



/**
 * Esta clase abstracta representa Comprobantes para el sistema de estacionamiento de vehículos.
 *
 */
public abstract class Comprobante implements Serializable, IToJson {

    private int nroComprobante;
    private Vehiculo vehiculo;
    private LocalDateTime horarioYFechaEntrada;

    private SERVICIOS servicioAdicional;


    public Comprobante() {
    }

    public Comprobante(int nroComprobante, Vehiculo vehiculo, LocalDateTime horarioYFecha, SERVICIOS servicio) {
        this.nroComprobante = nroComprobante;
        this.vehiculo = vehiculo;
        this.horarioYFechaEntrada = horarioYFecha;
        this.servicioAdicional = servicio;
    }

    public int getNroComprobante() {
        return nroComprobante;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setNroComprobante(int nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getHorarioYFechaEntrada() {
        return horarioYFechaEntrada;
    }

    public void setHorarioYFechaEntrada(LocalDateTime horarioYFechaEntrada) {
        this.horarioYFechaEntrada = horarioYFechaEntrada;
    }

    public SERVICIOS getServicioAdicional() {
        return servicioAdicional;
    }

    public void setServicioAdicional(SERVICIOS servicioAdicional) {
        this.servicioAdicional = servicioAdicional;
    }


    @Override
    public String toString() {
        return "Comprobante N°=" + nroComprobante +"\n"+
                "Info Vehiculo:" +"\n"+ vehiculo +"\n"+
                "Horario y FechaEntrada=" + horarioYFechaEntrada +"\n"+
                "ServicioAdicional=" + servicioAdicional+"\n" ;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonServicio = new JSONObject();

        jsonObject.put("nroComprobante",nroComprobante);
        jsonObject.put("vehiculo",vehiculo.toJson());
        jsonObject.put("horarioYFechaEntrada",horarioYFechaEntrada);
        jsonServicio.put("info",servicioAdicional.getInfo());
        jsonServicio.put("costo",servicioAdicional.getCosto());
        jsonObject.put("servicio",jsonServicio);


        return jsonObject;

    }

    public LocalDateTime stringAFecha(String fecha){

        LocalDateTime fechaAux = null;

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
            fechaAux = LocalDateTime.parse(fecha, formato);

        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto");
        }

        return fechaAux;
    }
}
