package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.IngredientesDAO;
import org.example.objetos.Ingredientes;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa la interfaz IngredientesDAO y se encarga de realizar las operaciones CRUD sobre la tabla Ingredientes
 */
public class IngredientesCRUD implements IngredientesDAO {

    /**
     * Método que inserta un ingrediente en la tabla Ingredientes. No implementado.
     * @param ingrediente Objeto de tipo Ingredientes que se desea insertar
     */
    @Override
    public void insertar(Ingredientes ingrediente) {

    }

    /**
     * Método que actualiza un ingrediente en la tabla Ingredientes. No implementado.
     * @param ingrediente Objeto de tipo Ingredientes que se desea actualizar
     */
    @Override
    public void actualizar(Ingredientes ingrediente) {

    }

    /**
     * Método que elimina un ingrediente de la tabla Ingredientes. No implementado.
     * @param idIngrediente ID del ingrediente que se desea eliminar
     */
    @Override
    public void eliminar(int idIngrediente) {

    }

    /**
     * Método que busca un ingrediente en la tabla Ingredientes. No implementado.
     * @param idIngrediente ID del ingrediente que se desea eliminar
     */
    @Override
    public Ingredientes buscarPorID(int idIngrediente) {
        return null;
    }

    /**
     * Método que lista todos los ingredientes de la tabla Ingredientes
     * @return Lista de objetos de tipo Ingredientes
     */
    @Override
    public List<Ingredientes> listaTodos() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Ingredientes> ingredientes = new ArrayList<>();
        System.out.println("Obteniendo lista de ingredientes");
        try{
            ingredientes = sesion.createQuery("from Ingredientes").list();
        } catch (Exception e){
            System.out.println("Error al obtener la lista de ingredientes" + e.getMessage());
        } finally {
            sesion.close();
        }

        return ingredientes;
    }

    public List<Ingredientes> obtenerIngredientesFiltrados(Map<String, Object> filtros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder consulta = new StringBuilder("FROM Ingredientes i WHERE 1=1");

            if (filtros.containsKey("nombre")) {
                consulta.append(" AND i.nombreIngrediente LIKE :nombre");
            }

            if (filtros.containsKey("tipo")) {
                String tipo = filtros.get("tipo").toString().toUpperCase();
                if (!"TODOS".equals(tipo)) {
                    consulta.append(" AND i.tipoIngrediente LIKE :tipo");
                }
            }

            Query<Ingredientes> query = session.createQuery(consulta.toString(), Ingredientes.class);

            if (filtros.containsKey("nombre")) {
                query.setParameter("nombre", "%" + filtros.get("nombre") + "%");
            }

            if (filtros.containsKey("tipo")) {
                String tipo = filtros.get("tipo").toString().toUpperCase();
                if (!"TODOS".equals(tipo)) {
                    try {
                        Ingredientes.TiposIngrediente tipoEnum = Ingredientes.TiposIngrediente.valueOf(tipo);
                        query.setParameter("tipo", tipoEnum);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo no válido: " + filtros.get("tipo"));
                    }
                }
            }

            return query.list();
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de ingredientes filtrada: " + e.getMessage());
            return null;
        }
    }
}
