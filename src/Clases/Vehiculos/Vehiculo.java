package Clases.Vehiculos;

import Interfaces.IToJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Clase abstracta con los datos de vehiculos de donde extienden
 * moto, auto y camioneta.
 */
public abstract class Vehiculo implements Serializable, IToJson {

    private String patente, modelo, marca;
    private double tamanio;



    public Vehiculo(String patente, String modelo, String marca, double tamanio) {
        this.patente = patente;
        this.modelo = modelo;
        this.marca = marca;
        this.tamanio = tamanio;
    }


    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    @Override
    public String toString() {
        return "patente=" + patente + "\n" +
                "modelo=" + modelo + "\n" +
                "marca=" + marca + "\n" +
                "tamanio=" + tamanio ;
    }

    /**
     * Metodo que pasa todos los datos del vehiculo a un objeto JSON.
     * @see IToJson
     * @return JSONObject con todos los datos del vehiculo.
     * @throws JSONException
     */
    @Override
    public JSONObject toJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("patente",patente);
        jsonObject.put("modelo",modelo);
        jsonObject.put("marca",marca);
        jsonObject.put("tamanio",tamanio);

        return jsonObject;
    }


    /**
     * Metodo abstracto que implementan las clases hijas y
     * pasa los datos de los vehiculos a JSON.
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public abstract Vehiculo jsonToJavaVehiculo(JSONObject jsonObject) throws JSONException;


}
