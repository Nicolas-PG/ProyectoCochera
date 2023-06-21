package Clases.Vehiculos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase hija que hereda los atributos de vehiculo y setea el tamaño diferente.
 */
public class Auto extends Vehiculo {

    public Auto(String patente, String modelo, String marca) {
        super(patente, modelo, marca, 1.0);
    }

    @Override
    public Vehiculo jsonToJavaVehiculo(JSONObject jsonObject) throws JSONException {
        Auto auto = new Auto(jsonObject.getString("patente"), jsonObject.getString("modelo"), jsonObject.getString("marca") );

        return auto;
    }
}
