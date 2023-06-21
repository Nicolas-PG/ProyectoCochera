package Clases.Vehiculos;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Clase hija que hereda los atributos de vehiculo y setea el tamaño diferente.
 */
public class Camioneta extends Vehiculo {

    public Camioneta(String patente, String modelo, String marca) {
        super(patente, modelo, marca, 2.0);
    }

    @Override
    public Vehiculo jsonToJavaVehiculo(JSONObject jsonObject) throws JSONException {
        Camioneta camioneta = new Camioneta(jsonObject.getString("patente"), jsonObject.getString("modelo"), jsonObject.getString("marca") );

        return camioneta;
    }
}
