package Interfaces;

public interface IComportamientoColeccion<T> {
    boolean agregar(T elemento);
    String listar();
    boolean eliminar(T elemento);
    T buscar(int id);
    int cantElementos();
}
