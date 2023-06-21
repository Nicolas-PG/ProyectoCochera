package Clases.Sistema;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * La clase JsonUtiles proporciona métodos para trabajar con archivos JSON y realizar operaciones relacionadas.
 */
public class JsonUtiles {

    /**
     * Graba un JSONArray en un archivo JSON.
     *
     * @param array El JSONArray a grabar.
     * @param archivo El nombre del archivo sin extensión.
     */
    public static void grabar(JSONArray array, String archivo) {
        try {
            FileWriter file = new FileWriter(archivo+".json");
            file.write(array.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Graba un JSONObject en un archivo JSON.
     *
     * @param jsonObject El JSONObject a grabar.
     * @param archivo El nombre del archivo sin extensión.
     */
    public static void grabar(JSONObject jsonObject, String archivo) {
        try {
            FileWriter file = new FileWriter(archivo+".json");
            file.write(jsonObject.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Lee el contenido de un archivo JSON y lo devuelve como una cadena de texto.
     *
     * @param archivo El nombre del archivo sin extensión.
     * @return El contenido del archivo JSON como una cadena de texto.
     */

    public static String leer(String archivo)
    {
        String contenido = "";
        try
        {
            contenido = new String(Files.readAllBytes(Paths.get(archivo+".json")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contenido;
    }

    /**
     * Obtiene los datos de la API pública de Bluelytics para las cotizaciones de monedas.
     *
     * @return Los datos de la API como una cadena de texto JSON.
     */
    public static String getDataMoneda(){
        try{
            URL url = new URL("https://api.bluelytics.com.ar/v2/latest");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if(responseCode != 200){
                throw new RuntimeException("Error: "+responseCode);
            }
            else{
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext())
                {
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                return stringBuilder.toString();
            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
        return "";
    }
}

