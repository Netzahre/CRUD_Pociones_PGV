package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.PocionesDAO;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.example.objetos.Recetas;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa la interfaz PocionesDAO y se encarga de realizar las operaciones CRUD sobre la tabla Pociones
 */
public class PocionesCRUD implements PocionesDAO {

    /**
     * Método que inserta una nueva poción en la base de datos
     *
     * @param pocion       Poción a insertar
     * @param ingredientes Mapa que contiene los ingredientes y sus cantidades
     */
    @Override
    public void insertar(Pociones pocion, Map<Ingredientes, Integer> ingredientes) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        try {
            sesion.beginTransaction();
            sesion.persist(pocion);
            sesion.flush(); // Forzar la sincronización con la base de datos
            sesion.refresh(pocion);

            // Recorrer el mapa de ingredientes y cantidades para crear las recetas
            for (Map.Entry<Ingredientes, Integer> entry : ingredientes.entrySet()) {
                Ingredientes ingrediente = entry.getKey();
                Integer cantidad = entry.getValue();
                if (ingrediente.getIdIngrediente() == 0) {
                    sesion.persist(ingrediente); // Persistir el ingrediente si no existe. De cara al futuro.
                } else {
                    ingrediente = sesion.merge(ingrediente);
                }
                Recetas receta = new Recetas(pocion, ingrediente, cantidad);
                sesion.persist(receta);
            }
            sesion.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error al insertar la poción: " + e.getMessage());
            sesion.getTransaction().rollback();

        } finally {
            sesion.close();
        }
    }

    /**
     * Método que actualiza una poción en la base de datos
     *
     * @param idPocion    ID de la poción a actualizar
     * @param pocion      Poción con los nuevos datos
     * @param ingredientes Mapa que contiene los ingredientes y sus cantidades
     */
    @Override
    public void actualizar(int idPocion, Pociones pocion, Map<Ingredientes, Integer> ingredientes) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Pociones pocionNueva = sesion.get(Pociones.class, idPocion);

            if (pocionNueva == null) {
                throw new RuntimeException("Poción no encontrada");
            }

            pocionNueva.setNombrePocion(pocion.getNombrePocion());
            pocionNueva.setEfectoPocion(pocion.getEfectoPocion());
            pocionNueva.setPrecio(pocion.getPrecio());
            pocionNueva.setEscuela(pocion.getEscuela());
            pocionNueva.setTamanio(pocion.getTamanio());

            // Eliminar las recetas antiguas de la poción
            List<Recetas> recetasAntiguas = new ArrayList<>(pocionNueva.getReceta());
            for (Recetas receta : recetasAntiguas) {
                //esto desvincula la receta de la poción
                receta.setPocion(null);
                //borrar la receta
                sesion.remove(receta);
            }
            pocionNueva.getReceta().clear();

            // Recorrer el mapa de ingredientes y cantidades para crear las recetas
            for (Map.Entry<Ingredientes, Integer> entry : ingredientes.entrySet()) {
                Ingredientes ingrediente = entry.getKey();
                Integer cantidad = entry.getValue();
                ingrediente = sesion.merge(ingrediente);  // Evitar problemas con entidades desconectadas
                Recetas receta = new Recetas(pocionNueva, ingrediente, cantidad);
                sesion.persist(receta);
                pocionNueva.addReceta(receta);
            }

            sesion.merge(pocionNueva);
            sesion.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error al actualizar la poción: " + e.getMessage());
            sesion.getTransaction().rollback();

        } finally {
            sesion.close();
        }
    }

    /**
     * Método que elimina una poción de la base de datos
     *
     * @param idPocion ID de la poción a eliminar
     */
    @Override
    public void eliminar(int idPocion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            sesion.beginTransaction();
            Pociones borrarPocion = sesion.get(Pociones.class, idPocion);
            sesion.remove(borrarPocion);
            sesion.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar la poción: " + e.getMessage());
            sesion.getTransaction().rollback();
        } finally {
            sesion.close();
        }
    }

    /**
     * Método que busca una poción por su ID
     *
     * @param idPocion ID de la poción a buscar
     * @return Poción encontrada
     * No se ha usado en el proyecto pero se ha implementado para completar la interfaz (Y para futuras implementaciones)
     */
    @Override
    public Pociones buscarPorID(int idPocion) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Pociones pocion = null;
        try {
            pocion = sesion.get(Pociones.class, idPocion);
        } catch (Exception e) {
            System.out.println("Error al buscar la poción: " + e.getMessage());
        } finally {
            sesion.close();
        }
        return pocion;
    }

    /**
     * Método que devuelve una lista con todas las pócimas de la base de datos
     *
     * @return Lista de pócimas
     */
    @Override
    public List<Pociones> listaTodos() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Pociones> pociones = new ArrayList<>();
        try {
            System.out.println("Obteniendo lista de pociones");
            pociones = sesion.createQuery("from Pociones").list();
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de pociones: " + e.getMessage());
        } finally {
            sesion.close();
        }
        return pociones;
    }

    public Map<Ingredientes, Integer> obtenerIngredientesPocion(int idPocion) {
        try(Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Pociones pocion = sesion.get(Pociones.class, idPocion);
            Map<Ingredientes, Integer> ingredientes = new HashMap<>();

            for (Recetas receta : pocion.getReceta()) {
                Ingredientes ingrediente = receta.getIngrediente();
                Integer cantidad = receta.getCantidad();

                ingredientes.put(ingrediente, cantidad);
            }
            return ingredientes;
        } catch (Exception e) {
            System.out.println("Error al obtener los ingredientes de la poción: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método que devuelve una lista de pócimas filtradas por nombre y escuela
     *
     * @param filtros Mapa con los filtros a aplicar
     * @return Lista de pócimas filtradas
     */
    public List<Pociones> obtenerPocionesFiltradas(Map<String, Object> filtros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder consulta = new StringBuilder("FROM Pociones p WHERE 1=1");

            if (filtros.containsKey("nombre")) {
                consulta.append(" AND p.nombrePocion LIKE :nombre");
            }

            if (filtros.containsKey("escuela")) {
                String escuela = filtros.get("escuela").toString().toUpperCase();
                if (!"TODAS".equals(escuela)) {
                    consulta.append(" AND p.escuela LIKE :escuela");
                }
            }

            if (filtros.containsKey("tamanio")) {
                String tamanio = filtros.get("tamanio").toString().toUpperCase();
                if (!"TODOS".equals(tamanio)) {
                    consulta.append(" AND p.tamanio LIKE :tamanio");
                }
            }

            Query<Pociones> query = session.createQuery(consulta.toString(), Pociones.class);

            if (filtros.containsKey("nombre")) {
                query.setParameter("nombre", "%" + filtros.get("nombre") + "%");
            }

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

            if (filtros.containsKey("tamanio")) {
                String tamanio = filtros.get("tamanio").toString().toUpperCase();
                if (!"TODOS".equals(tamanio)) {
                    try {
                        Pociones.Tamanio tamanioEnum = Pociones.Tamanio.valueOf(tamanio);
                        query.setParameter("tamanio", tamanioEnum);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tamaño no válido: " + filtros.get("tamanio"));
                    }
                }
            }

            return query.list();
        } catch (Exception e) {
            System.out.println("Error al obtener las pócimas filtradas: " + e.getMessage());
            return null;
        }
    }

}
