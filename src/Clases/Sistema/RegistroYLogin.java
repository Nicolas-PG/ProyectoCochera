package Clases.Sistema;

import Excepciones.ContrasenaInsegura;
import Excepciones.UsuarioInvalido;
import Excepciones.UsuarioYaRegistrado;

import java.util.HashSet;


/**
 * Esta clase maneja todos los metodos para registrar usuarios y
 * para poder iniciar sesion en el sistema.
 */
public class RegistroYLogin {

    private static final String ARCHIVO_USUARIOS = "usuarios.dat";





    /**
     * Esta funcion verifica si el usuario existe en el sistema y
     * retorna un boolean dependiendo si existe o no.
     * @see ManejoArchivos#leerUsuarios(String)  Esta funcion pasa los usuarios del archivo a una lista
     * @param usuario Usuario a verificar.
     * @return Dato boolean.
     */
    private static boolean verificarUsuarios(Usuario usuario){
        HashSet<Usuario> usuarios = ManejoArchivos.leerUsuarios(ARCHIVO_USUARIOS);                 //Esta funcion llama a la de pasar de arch a lista y verifica si el empleado existe

        for (Usuario aux : usuarios){
            if(usuario.getIdUsuario() == aux.getIdUsuario()){   // Verifico con ID
                return true;
            }

        }

        return false;

    }


    /**
     * Esta funcion verifica que el usuario no exista y lo registra
     * con los datos que recibe por parametro.
     * @see RegistroYLogin#generarID()
     * @see RegistroYLogin#verificarUsuarios(Usuario)
     * @see ManejoArchivos#guardarUsuario(Usuario, String)
     * @param nombreEmpleado Nombre del usuario a registrar.
     * @param contrasena Contraseña del usuario a registrar.
     * @throws UsuarioYaRegistrado Si el usuario que intenta registrar ya se encuentra registrado lanza una excepcion.
     * @throws ContrasenaInsegura Excepcion que se lanza si la contraseña es menor a 5 digitos.
     */


    public static void registroUsuario(String nombreEmpleado, String contrasena) throws UsuarioYaRegistrado, ContrasenaInsegura {

        Empleado empleado = new Empleado(nombreEmpleado,contrasena,generarID());
        System.out.println(empleado);
        if (!verificarUsuarios(empleado)){
            ManejoArchivos.guardarUsuario(empleado,ARCHIVO_USUARIOS);
        }else if (contrasena.length() <=5){
            throw new ContrasenaInsegura();
        }else {
            throw new UsuarioYaRegistrado(nombreEmpleado);
        }

    }


    /**
     * Esta funcio trae todos los usuarios para verificar que exista y si existe
     * y los datos son correctos permite iniciar sesion devolviendo un booleano.
     * @see ManejoArchivos#leerUsuarios(String)
     * @param nombreUsuario
     * @param contrasena
     * @return Dato boolean dependiendo si los datos son correctos o no.
     * @throws UsuarioInvalido Si los datos son erroneos o no existen arroja una excepcion.
     */

    public static boolean inicioSesion(String nombreUsuario, String contrasena) throws UsuarioInvalido {
        HashSet<Usuario> usuarios = ManejoArchivos.leerUsuarios(ARCHIVO_USUARIOS);
        boolean retorno = false;

        for (Usuario aux : usuarios){
            if (aux.getNombreUsuario().equals(nombreUsuario) && aux.verificarContrasena(contrasena)&&aux.isEstado()==true){  // verificarContrasena es un metodo del empleado que verifica que sean iguales, ya que no podemos usar un get  por seguirdad

                //Esta funcion llama a la de arch to lista y verifica que los datos sean correctos, si son correctos nos devuelve true.

                retorno = true;
            }


        }

        if (retorno == false){

            throw new UsuarioInvalido();
        }

        return retorno;

    }


    // buscar en la lista el ultimo empelado y ver la id para sumarle 1

    /**
     * Busca la ID mayor de todos los usuarios y le suma 1 para poder
     * generar ID's incrementalmente
     *
     * @return
     */

    private static int generarID(){

        HashSet<Usuario> usuarios = ManejoArchivos.leerUsuarios(ARCHIVO_USUARIOS);

        int mayor=1;

        if (!usuarios.isEmpty()){

            for (Usuario aux : usuarios){

                if(mayor < aux.getIdUsuario()){

                    mayor = aux.getIdUsuario();
                }
            }

            return mayor + 1;

        }else {

            return mayor;

        }

    }

    /**
     * Busca la ID de un usuario a partir de su nombre.
     *
     * @param nombre Nombre del usuario a buscar ID.
     * @return Retorna la ID del usuario.
     */

    public static int buscarId(String nombre){

        HashSet<Usuario> usuarios = ManejoArchivos.leerUsuarios(ARCHIVO_USUARIOS);

        int retorno=9999;

        for (Usuario aux : usuarios){
            if (aux.getNombreUsuario().equals(nombre)){
                retorno = aux.getIdUsuario();
            }
        }

        return retorno;
    }





}
