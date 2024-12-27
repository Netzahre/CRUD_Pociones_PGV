package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.IngredientesDAO;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class IngredientesCRUD implements IngredientesDAO {

    @Override
    public void insertar(Ingredientes ingrediente) {

    }

    @Override
    public void actualizar(Ingredientes ingrediente) {

    }

    @Override
    public void eliminar(int idIngrediente) {

    }

    @Override
    public Ingredientes buscarPorID(int idIngrediente) {
        return null;
    }

    @Override
    public List<Ingredientes> listaTodos() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Ingredientes> ingredientes = new ArrayList<>();
        try{
            ingredientes = sesion.createQuery("from Ingredientes").list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sesion.close();
        }

        return ingredientes;
    }
}
