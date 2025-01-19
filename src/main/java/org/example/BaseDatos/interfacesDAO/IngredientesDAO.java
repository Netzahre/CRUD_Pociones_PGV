package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Ingredientes;

import java.util.List;

/**
 * Interfaz que define los métodos que se pueden realizar sobre la tabla Ingredientes de la base de datos.
 */
public interface IngredientesDAO {
    void insertar(Ingredientes ingrediente);
    void actualizar(Ingredientes ingrediente);
    void eliminar(int idIngrediente);
    Ingredientes buscarPorID(int idIngrediente);
    List<Ingredientes> listaTodos();

}
