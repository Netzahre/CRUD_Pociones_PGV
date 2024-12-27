package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.PocionesDAO;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.example.objetos.Recetas;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PocionesCRUD implements PocionesDAO {

    @Override
    public void insertar(Pociones pocion, Map<Ingredientes, Integer> ingredientes) {
        //Se inicia la sesion
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        try {
            //empezamos la transaccion
            sesion.beginTransaction();
            //Hacemos el insert
            sesion.persist(pocion);
            sesion.flush(); // Asegura que se genere el ID

            // Opcional: Actualizamos la poción para asegurarnos de que es "managed"
            sesion.refresh(pocion);

            // Guarda cada receta asociada
            for (Map.Entry<Ingredientes, Integer> entry : ingredientes.entrySet()) {
                Ingredientes ingrediente = entry.getKey();
                Integer cantidad = entry.getValue();
                if (ingrediente.getIdIngrediente() == 0) {
                    sesion.persist(ingrediente); // Solo si es una nueva entidad
                } else {
                    ingrediente = sesion.merge(ingrediente); // Asociar la entidad al contexto si ya existe
                }
                // Creamos la entidad Recetas
                Recetas receta = new Recetas(pocion, ingrediente, cantidad);

                // Persistimos la receta en la base de datos
                sesion.persist(receta);
            }


            //Hacemos commit a la transaccion
            sesion.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            //Si da error, se hace rollback
            sesion.getTransaction().rollback();

        } finally {
            //Finalmente se cierra la sesion para evitar que se qu
            sesion.close();
        }
    }

    @Override
    public void actualizar(Pociones pocion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try{
            sesion.beginTransaction();
            sesion.merge(pocion);
            sesion.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            sesion.getTransaction().rollback();
        } finally {
            sesion.close();
        }
    }

    @Override
    public void eliminar(int idPocion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        try{
            sesion.beginTransaction();
            sesion.remove(idPocion);
            sesion.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            sesion.getTransaction().rollback();
        } finally {
            sesion.close();
        }

    }

    @Override
    public Pociones buscarPorID(int idPocion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Pociones pocion = null;
        try{
            pocion = sesion.get(Pociones.class,idPocion);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sesion.close();
        }
        return pocion;
    }

    @Override
    public List<Pociones> listaTodos() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Pociones> pociones = new ArrayList<>();
        try{
            pociones = sesion.createQuery("from Pociones").list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sesion.close();
        }

        return pociones;
    }


}
