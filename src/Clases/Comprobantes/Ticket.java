package Clases.Comprobantes;

import Clases.Cochera.SERVICIOS;
import Clases.Sistema.JsonUtiles;
import Clases.Vehiculos.Vehiculo;
import Interfaces.ICalcularCosto;
import Interfaces.IToJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
/**
 * Clase que representa un ticket de estacionamiento. Hereda de la clase "Comprobante" y
 * implementa la interfaz "ICalcularCosto".
 *
 *
 */
public class Ticket extends Comprobante implements ICalcularCosto{

    private double costoTotal;
    private double tarifaVehiculo;

    private LocalDateTime horarioSalida;

    public Ticket() {
    }

    public Ticket(int nroComprobante, Vehiculo vehiculo, LocalDateTime horarioYFecha, SERVICIOS servicio, double tarifaVehiculo) {
        super(nroComprobante, vehiculo, horarioYFecha, servicio);
        this.tarifaVehiculo = tarifaVehiculo;
        this.costoTotal = calcularTotal();

    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public double getTarifaVehiculo() {
        return tarifaVehiculo;
    }

    public void setTarifaVehiculo(double tarifaVehiculo) {
        this.tarifaVehiculo = tarifaVehiculo;
    }

    public LocalDateTime getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(LocalDateTime horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    @Override
    public double calcularTotal() {

        return calcularTarifa() + calcularAdicional();
    }

    @Override
    public double calcularAdicional() {

        return getServicioAdicional().getCosto() ;
    }

    @Override
    public double calcularTarifa() {
        double resultado=0;

        horarioSalida = LocalDateTime.now();

        Duration tiempoACobrar = Duration.between(getHorarioYFechaEntrada(),horarioSalida);

        long dias =  tiempoACobrar.toDays();
        long hora =  tiempoACobrar.toHours();
        long minutos =  tiempoACobrar.toMinutes();

        resultado = ((dias * 24) * tarifaVehiculo) + (hora * tarifaVehiculo)  + ((tarifaVehiculo/60)*minutos);



        return resultado;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "costoTotal=" + costoTotal +
                ", tarifaVehiculo=" + tarifaVehiculo +
                ", horarioSalida=" + horarioSalida +
                '}';
    }


    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = super.toJson();

        jsonObject.put("costoTotal",costoTotal);
        jsonObject.put("tarifaVehiculo", tarifaVehiculo);
        jsonObject.put("horarioSalida",horarioSalida);

        return jsonObject;
    }

    public String tarifaDiferentesMoneda(double costoTiket){
        String aux="";

        JSONObject tarifa= null;
        try {
            tarifa = new JSONObject(JsonUtiles.getDataMoneda());

            JSONObject dolar=tarifa.optJSONObject("blue");
            double dolarBlue=dolar.getDouble("value_sell");

            JSONObject euro=tarifa.optJSONObject("blue_euro");
            double euroBlue=euro.getDouble("value_sell");

            double costoDolar=costoTiket/dolarBlue;
            double costoEuro=costoTiket/euroBlue;
            aux= "Euro = €"+String.format("%.2f",costoEuro)+ "   " + "Dolar es US$= "+String.format("%.2f",costoDolar);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }




        return aux;
    }
}
