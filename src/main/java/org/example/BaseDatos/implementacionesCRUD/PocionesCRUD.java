package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.PocionesDAO;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.example.objetos.Recetas;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
            System.out.println("Obteniendo lista de pociones");
            pociones = sesion.createQuery("from Pociones").list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            sesion.close();
        }

        return pociones;
    }

    public List<Pociones> obtenerPocionesFiltradas(Map<String, Object> filtros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder consulta = new StringBuilder("FROM Pociones p WHERE 1=1");

            // Filtros básicos
            if (filtros.containsKey("nombre")) {
                consulta.append(" AND p.nombrePocion LIKE :nombre");
            }

            // Comprobamos si la escuela es "TODAS" para no aplicar el filtro de escuela
            if (filtros.containsKey("escuela")) {
                String escuela = filtros.get("escuela").toString().toUpperCase();
                if (!"TODAS".equals(escuela)) {
                    consulta.append(" AND p.escuela LIKE :escuela");
                }
            }

            Query<Pociones> query = session.createQuery(consulta.toString(), Pociones.class);

            if (filtros.containsKey("nombre")) {
                query.setParameter("nombre", "%" + filtros.get("nombre") + "%");
            }

            // Solo se aplica el filtro de escuela si no es "TODAS"
            if (filtros.containsKey("escuela")) {
                String escuela = filtros.get("escuela").toString().toUpperCase();
                if (!"TODAS".equals(escuela)) {
                    try {
                        Pociones.Escuela escuelaEnum = Pociones.Escuela.valueOf(escuela);
                        query.setParameter("escuela", escuelaEnum);
                    } catch (IllegalArgumentException e) {
                        // Si la cadena no es un valor válido de la enumeración, manejar la excepción
                        System.out.println("Escuela no válida: " + filtros.get("escuela"));
                    }
                }
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
