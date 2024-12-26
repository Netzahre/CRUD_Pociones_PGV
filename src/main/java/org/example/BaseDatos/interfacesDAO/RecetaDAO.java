package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Recetas;

import java.util.List;

//Realmente necesitamos?
public interface RecetaDAO {
    void insertar(Recetas receta);
    void actualizar(int idPocion);
    void eliminar(int idPocion);
    Recetas buscarPorID(int idPocion);
    List<Recetas> listaTodos();
}