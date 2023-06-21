package Clases.Sistema;

import Clases.Cochera.Estacionamiento;
import Clases.Comprobantes.ComprobanteEstacionamiento;
import Clases.Comprobantes.Ticket;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * La clase ManejoArchivos proporciona métodos para trabajar con archivos  y realizar operaciones relacionadas.
 */
public class ManejoArchivos {



    /**
     * Graba el comprobante de estacionamiento en un archivo.
     *
     * @param archivo               El nombre del archivo en el que se guarda el comprobante.
     * @param comprobanteEstacionamiento El comprobante de estacionamiento a guardar.
     */
    public static void grabarComprobante(String archivo, ComprobanteEstacionamiento comprobanteEstacionamiento){

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(comprobanteEstacionamiento);

            objectOutputStream.close();
        }catch (IOException e){
            System.out.println("Error al guardar el comprobante "+e.getMessage());
        }
    }


    /**
     * Lee los comprobantes de estacionamiento desde un archivo.
     *
     * @param archivo El nombre del archivo desde el que se leen los comprobantes.
     * @return Un HashMap que contiene los comprobantes de estacionamiento leídos del archivo.
     */
    public static HashMap<String,ComprobanteEstacionamiento> leerComprobantes(String archivo){

        HashMap<String,ComprobanteEstacionamiento> comprobantes = new HashMap<>();

        try (ObjectInputStream objInSt = new ObjectInputStream(new FileInputStream(archivo))){

            while (true){
                try {

                    ComprobanteEstacionamiento comprobante = (ComprobanteEstacionamiento) objInSt.readObject();
                    comprobantes.put(comprobante.getVehiculo().getPatente(), comprobante);

                }catch (EOFException e){
                    break;
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado: "+archivo); // hacer excepcion personalizada

        }catch (IOException | ClassNotFoundException e){

            e.printStackTrace();

            System.out.println("Error al cargar el comprobante desde el archivo");
        }

        return comprobantes;
    }


    /**
     * Graba el ticket en un archivo.
     *
     * @param archivo El nombre del archivo en el que se guarda el ticket.
     * @param ticket  El ticket a guardar.
     */
    public static void grabarTicket(String archivo, Ticket ticket){

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(ticket);

            objectOutputStream.close();
        }catch (IOException e){
            System.out.println("Error al guardar el ticket "+e.getMessage());
        }
    }

    /**
     * Lee los tickets desde un archivo.
     *
     * @param archivo El nombre del archivo desde el que se leen los tickets.
     * @return Una lista de tickets leídos del archivo.
     */
    public static List<Ticket> leerTickets(String archivo){

        List<Ticket> tickets = new ArrayList<>();
        try (ObjectInputStream objInStr = new ObjectInputStream(new FileInputStream(archivo))){

            while (true){
                try {
                    Ticket ticket = (Ticket) objInStr.readObject();
                    tickets.add(ticket);

                }catch (EOFException e){
                    break;
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado: "+archivo);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Error al cargar el ticket desde el archivo");
        }

        return tickets;
    }


    /**
     * Lee objetos Usuario desde un archivo y los guarda en un HashSet.
     *
     * @param archivo El nombre o la ruta del archivo desde el cual se leerán los usuarios.
     * @return Un HashSet que contiene los usuarios leídos desde el archivo.
     */

    public static HashSet<Usuario> leerUsuarios(String archivo){

        HashSet<Usuario> usuarios = new HashSet<>();
        try (ObjectInputStream objInStr = new ObjectInputStream(new FileInputStream(archivo))){

            while (true){
                try {
                    Usuario usuario = (Usuario) objInStr.readObject();
                    usuarios.add(usuario);

                }catch (EOFException e){
                    break;
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado: "+archivo);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Error al cargar el usario desde el archivo");
        }

        return usuarios;
    }

    /**
     * Guarda un objeto Usuario en un archivo.
     * Lee todos los usuarios existentes del archivo, agrega el nuevo usuario y guarda
     * toda la coleccion actualzida.
     *
     * @param usuario El objeto Usuario que se va a guardar en el archivo.
     * @param archivo El nombre o la ruta del archivo en el cual se guardará el usuario.
     */

    public static void guardarUsuario(Usuario usuario, String archivo){

        HashSet<Usuario> usuarios = leerUsuarios(archivo);
        usuarios.add(usuario);

        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);

            for (Usuario aux : usuarios){

                objetoSalida.writeObject(aux);
            }

            objetoSalida.close();
            archivoSalida.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }



    /**
     * Guarda un conjunto de objetos Usuario en un archivo.
     *
     * @param usuarioHashSet El conjunto de objetos Usuario que se va a guardar en el archivo.
     * @param archivo El nombre o la ruta del archivo en el cual se guardará el conjunto de usuarios.
     */


    public static void guardarUsuariosSet(HashSet<Usuario> usuarioHashSet, String archivo){


        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);

            for (Usuario aux : usuarioHashSet){

                objetoSalida.writeObject(aux);
            }

            objetoSalida.close();
            archivoSalida.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }




}
