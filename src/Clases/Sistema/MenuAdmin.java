package Clases.Sistema;

import Clases.Cochera.Estacionamiento;
import Clases.Cochera.SERVICIOS;
import Excepciones.ContrasenaInsegura;
import Excepciones.EspacioInsuficiente;
import Excepciones.PatenteNoExiste;
import Excepciones.UsuarioYaRegistrado;
import org.json.JSONException;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * La clase MenuAdmin representa el menú y las acciones disponibles para el administrador del sistema.
 * Una de las opciones permite llamar a las acciones del menú de empleados.
 */
public class MenuAdmin {




    /**
     * Muestra el menú de opciones y permite al administrador realizar acciones en el sistema.
     *
     * @param estacionamiento El objeto Estacionamiento que contiene los datos del estacionamiento.
     */
    public static void menuAdmin(Estacionamiento estacionamiento)  {

        SistemaEmpleados registroUsuarios=new SistemaEmpleados();
        registroUsuarios.cargarEmpleadosArchivo();


        Scanner scanner=new Scanner(System.in);

        System.out.println("Ingrese el numero de la opcion que quiera realizar:"+"\n");
        System.out.println("\n"+"1)Registrar nuevo usuario."+"\n"+
                            "2)Eliminar usuario"+"\n"+
                            "3)Cambiar estado de usuario"+"\n"+
                            "4)Menu Empleado"+"\n"+
                            "5)Ver recaudacion"+"\n"+
                            "6)Vaciar caja"+"\n");



        int id;
        int estado;
        String salida="entrar";



        while (salida.equals("entrar")){
            int opcion=scanner.nextInt();



        switch (opcion) {

            case 1:
                scanner.nextLine();
                System.out.println("Ingrese el usuario: ");
                String nombre = scanner.nextLine();
                scanner.nextLine();
                System.out.println("Ingrese la contrasena: ");
                String contrasena = scanner.nextLine();


                try {

                    RegistroYLogin.registroUsuario(nombre, contrasena);
                    registroUsuarios.cargarEmpleadosArchivo();


                } catch (UsuarioYaRegistrado e) {
                    System.out.println(e.getMessage());;
                } catch (ContrasenaInsegura e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                System.out.println("Lista de usuarios registrados");
                System.out.println(registroUsuarios.listar());

                System.out.println("Ingrese el ID del usuario que quiere eliminar");
                scanner.nextLine();

                id = scanner.nextInt();

                registroUsuarios.eliminar(registroUsuarios.buscar(id));
                break;

            case 3:
                System.out.println("Lista de usuarios registrados");

                System.out.println(registroUsuarios.listar());
                System.out.println("Ingrese el ID del usuario que quiere modificar su estado");
                scanner.nextLine();
                id = scanner.nextInt();

                System.out.println("Para activar usuario ingrese 1, para bloquear ingrese 0");
                scanner.nextLine();
                estado = scanner.nextInt();

                if (estado == 1) {
                    registroUsuarios.cambiarEstado(id, true);
                } else {
                    registroUsuarios.cambiarEstado(id, false);
                }
                break;


            case 4:
                MenuEmpleado.menuEpleado(estacionamiento);


                break;
            case 5:

                System.out.println("La recaudacion sin retirar es de :"+estacionamiento.getCaja());
                break;


            case 6:


                System.out.println("\n"+"Estas retirando de la caja $"+estacionamiento.getCaja()+"\n");

                estacionamiento.cerrarCaja();

                break;




        }

        registroUsuarios.guardarModificaciones();

        salida=scanner.nextLine();

        }

    }
}
