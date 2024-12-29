package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.util.List;
import java.util.Map;

public interface PocionesDAO {
    void insertar(Pociones pocion, Map<Ingredientes, Integer> ingredientes);
    void actualizar(int id,Pociones pocion, Map<Ingredientes, Integer> ingredientes);
    void eliminar(int idPocion);
    Pociones buscarPorID(int idPocion);
    List<Pociones> listaTodos();
}
