package Clases.Vehiculos;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Clase hija que hereda los atributos de vehiculo y setea el tamaño diferente.
 */
public class Moto extends Vehiculo {

    public Moto(String patente, String modelo, String marca) {
        super(patente, modelo, marca, 0.5);
    }



    @Override
    public Vehiculo jsonToJavaVehiculo(JSONObject jsonObject) throws JSONException {
        Moto moto = new Moto(jsonObject.getString("patente"), jsonObject.getString("modelo"), jsonObject.getString("marca") );

        return moto;
    }
}
