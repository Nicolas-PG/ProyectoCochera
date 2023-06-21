package Clases.Sistema;

import Clases.Cochera.Estacionamiento;
import Clases.Cochera.SERVICIOS;
import Clases.Comprobantes.ComprobanteEstacionamiento;
import Clases.Vehiculos.Auto;
import Clases.Vehiculos.Camioneta;
import Clases.Vehiculos.Moto;
import Excepciones.EspacioInsuficiente;
import Excepciones.PatenteNoExiste;

import javax.sound.midi.Soundbank;
import java.net.Socket;
import java.util.Scanner;
/**
 * La clase MenuEmpleado representa el menú y las acciones disponibles para los empleados que usenel  sistema.
 */
public class MenuEmpleado {

    /**
     * Muestra el menú de opciones y permite al empleado realizar acciones en el sistema.
     *
     * @param estacionamiento El objeto Estacionamiento que contiene los datos del estacionamiento.
     */
public static void menuEpleado(Estacionamiento estacionamiento)  {

    int tipo;
    int servicio;
    SERVICIOS aux = null;

    Scanner scanner=new Scanner(System.in);

    System.out.println("Ingrese el numero de la opcion que quiera realizar:"+"\n");
    System.out.println("\n"+"1)Ingresar Vehiculo"+"\n"+
            "2)Salida de Vehiculo y Ticket"+"\n"+
            "3)Listado de Vehiculos estacionados"+"\n"+
            "4)Espacio disponibles"+"\n"+
            "5)Ver caja"+"\n"+
            "6) Salir");

    int opcion=scanner.nextInt();
    ComprobanteEstacionamiento comprobanteAux = null;

    switch (opcion){

        case 1:

            System.out.println("Que tipo de Vehiculo esta por ingresar ?:" +"\n"+
                    "1)Moto.               "+"\n"+
                     "2)Auto."+"\n"+
                      "3)Camioneta.");
                do {

                    tipo=scanner.nextInt();
                    if(tipo>3||tipo<1){
                    System.out.println("Ingrese un opcion valida para el tipo de Vehiculo");}
                }while (tipo>3||tipo<1);

              scanner.nextLine();
            System.out.println("Ingresa la marca.");
            String marca=scanner.nextLine();

            scanner.nextLine();
            System.out.println("Ingresa la modelo.");
            String modelo=scanner.nextLine();


            System.out.println("Ingrese la patente ");
            String patente=scanner.nextLine();

            System.out.println("Ingrese si quiere Servicios adicionales:"+"\n"+
                    "1)Cambio de aceite."+"\n"+
                    "2)Aspirado."+"\n"+
                    "3)Lavado."+"\n"+
                    "4)Completo."+"\n"+
                     "5)Ningun servicio adicional"+"\n");


                do {

                    servicio = scanner.nextInt();
                    switch (servicio){

                        case 1:
                            aux=SERVICIOS.CAMBIOACEITE;
                            break;
                        case 2:
                            aux=SERVICIOS.ASPIRADO;
                            break;
                        case 3:
                            aux=SERVICIOS.LAVADO;
                            break;
                        case 4:
                            aux=SERVICIOS.COMPLETO;
                            break;
                        case 5:
                            aux=SERVICIOS.NINGUNO;
                            break;
                        default:
                            System.out.println("Ingrese un opcion valida");
                            break;
                    }


                }while (servicio>5||servicio<1);

            
                try{
                switch (tipo){
                    case 1:
                        Moto moto=new Moto(patente,modelo,marca);
                           comprobanteAux= estacionamiento.ingresarVehiculo(moto,aux);

                        break;
                    case 2:
                        Auto auto =new Auto (patente,modelo,marca);
                        comprobanteAux=estacionamiento.ingresarVehiculo(auto,aux);
                        break;
                    case 3:
                        Camioneta camioneta=new Camioneta(patente,modelo,marca);
                        comprobanteAux=estacionamiento.ingresarVehiculo(camioneta,aux);
                        break;
                    default:
                        break;

                    

                }}catch (EspacioInsuficiente e){
                    System.out.println(e.getMessage());
                }

            System.out.println(comprobanteAux.toString());
                break;
        case 2:

            boolean patenteValida=true;

            do {
                System.out.println("Ingrese la patente del Vehiculo");
                scanner.nextLine();
                String patenteAux = scanner.nextLine();

                patenteValida = estacionamiento.patenteValida(patenteAux);

                try {
                    System.out.println(estacionamiento.salidaVehiculo(patenteAux));
                } catch (PatenteNoExiste e) {
                    System.out.println(e.getMessage());
                }
            } while (patenteValida==false);
            break;

        case 3:


            System.out.println("La lista de vehiculos estacionados es : "+"\n");

            System.out.println(estacionamiento.listarComprobantes());
           break;
        case 4:

            System.out.println("\n"+"La cantidad de espacios disponibles en este momento en el estacionamiento es de :"+estacionamiento.getCapacidad());


        break;

        case 5:
            System.out.println(estacionamiento.getCaja());

            break;


        case 6:
            break;





    }




}



}
