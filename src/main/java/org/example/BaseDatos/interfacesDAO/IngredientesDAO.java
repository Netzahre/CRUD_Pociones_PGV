package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Ingredientes;

import java.util.List;

public interface IngredientesDAO {
    void insertar(Ingredientes ingrediente);
    void actualizar(Ingredientes ingrediente);
    void eliminar(int idIngrediente);
    Ingredientes buscarPorID(int idIngrediente);
    List<Ingredientes> listaTodos();

}
