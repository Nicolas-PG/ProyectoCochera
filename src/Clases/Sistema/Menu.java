package Clases.Sistema;

import Clases.Cochera.Estacionamiento;
import Excepciones.UsuarioInvalido;
import org.json.JSONException;

import java.util.Scanner;

public class Menu {


    public static void menuInicial(){

        int opcion=0;
        Estacionamiento nuevo=new Estacionamiento(100,"Grandpa`s");
        Scanner scanner = new Scanner(System.in);
        String nombre ="";
        String contrasena="";
        String control="";
        try {
            nuevo.backupEstacionamiento("estacionamiento");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Ingrese su nombre de usuario: ");
        nombre = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Ingrese su contrasena: ");
        contrasena = scanner.nextLine();
        scanner.nextLine();


        try {
            if (RegistroYLogin.inicioSesion(nombre,contrasena)){

                if (RegistroYLogin.buscarId(nombre) == 0){

                    while (!control.equals("salida")){

                    MenuAdmin.menuAdmin(nuevo);

                        System.out.println("Si quiere salir del sistema ingrese (salida), si no presione enter para continuar");
                        control=scanner.nextLine();
                    }


                }else {

                    while (!control.equals("salida")){

                        MenuEmpleado.menuEpleado(nuevo);

                        System.out.println("Si quiere salir del sistema ingrese (salida), si no presione enter para continuar");
                        control=scanner.nextLine();
                    }



                }

            }
        } catch (UsuarioInvalido e) {
            System.out.println(e.getMessage());
            menuInicial();
        }

        try {
          nuevo.guardarJsonEstacionamiento(nuevo.toJson(),"estacionamiento");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
