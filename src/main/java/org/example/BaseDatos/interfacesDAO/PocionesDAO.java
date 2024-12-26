package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Pociones;

import java.util.List;

public interface PocionesDAO {
    void insertar(Pociones pocion);
    void actualizar(Pociones pocion);
    void eliminar(int idPocion);
    Pociones buscarPorID(int idPocion);
    List<Pociones> listaTodos();
}
