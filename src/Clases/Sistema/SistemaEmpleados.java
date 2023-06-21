package Clases.Sistema;

import Interfaces.IComportamientoColeccion;

import java.util.HashSet;

/**
 * Esta clase implementa la interfaz IComportamientoColeccion y le pasa
 * un tipo de dato Empleado para poder sobreescribir los metodos  y
 * permitirle al admin poder aregar, eliminar y cambiar el estado a
 * un empleado en el sistema.
 * @see Interfaces.IComportamientoColeccion
 */
public class SistemaEmpleados implements IComportamientoColeccion<Empleado> {

    private HashSet<Usuario> empleados;

    public SistemaEmpleados() {
        this.empleados = new HashSet<>();
    }




    @Override
    public boolean agregar(Empleado elemento) {

        return empleados.add(elemento);
    }

    @Override
    public String listar() {
        String concatenar="";

        for (Usuario aux : empleados){

            concatenar += aux.toString()+"\n";
        }

        return concatenar;
    }

    @Override
    public boolean eliminar(Empleado elemento) {
        return empleados.remove(elemento);
    }

    @Override
    public Empleado buscar(int id) {

        for (Usuario aux : empleados){
            if (aux.getIdUsuario() == id){

                return (Empleado) aux;
            }
        }

        return null;
    }

    @Override
    public int cantElementos() {
        return empleados.size();
    }

    public void cambiarEstado(int id, boolean estado){

        for (Usuario aux : empleados){

            if (aux.getIdUsuario() == id){

                aux.setEstado(estado);
            }

        }
    }

    public  void cargarEmpleadosArchivo(){
        empleados = ManejoArchivos.leerUsuarios("usuarios.dat");

    }

    public  void guardarModificaciones(){

        ManejoArchivos.guardarUsuariosSet(empleados,"usuarios.dat");
    }
}
