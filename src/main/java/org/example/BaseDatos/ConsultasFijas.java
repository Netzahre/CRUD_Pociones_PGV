package org.example.BaseDatos;

import org.example.objetos.Pociones;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase que contiene las consultas fijas que se realizarán en la base de datos
 */
public class ConsultasFijas {


    /**
     * Consulta que devuelve los 10 ingredientes que se utilizan en más de tres pociones
     * @return Lista de objetos con el nombre del ingrediente y el número de pociones en las que se utiliza
     */
    public List<Object[]> ingredientesEnMasDeTresPociones() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {

            String query = "SELECT I.nombreIngrediente, COUNT(DISTINCT R.pocion.idPocion) " +
                    "FROM Ingredientes I " +
                    "JOIN Recetas R ON I.idIngrediente = R.ingrediente.idIngrediente " +
                    "GROUP BY I.nombreIngrediente " +
                    "HAVING COUNT(DISTINCT R.pocion.idPocion) > 3";

            Query query1 = sesion.createQuery(query);
            query1.setMaxResults(10);

            return (List<Object[]>) query1.list();
        } catch (Exception e) {
            System.out.println("Error en la consulta: " + e.getMessage());
            return null;
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }


    /**
     * Consulta que devuelve el total de veces que se usa cada tipo de ingrediente
     * @return Lista de objetos con el nombre del ingrediente y el número de veces que se usa
     */
    public List<Object[]> totalTiposIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.tipoIngrediente, COALESCE(SUM(R.cantidad), 0) AS total_usado FROM Ingredientes I LEFT JOIN Recetas R ON I.idIngrediente = R.ingrediente.idIngrediente GROUP BY I.tipoIngrediente ORDER BY total_usado DESC";
        Query query1 = sesion.createQuery(query);
        List<Object[]> lista = query1.list();
        sesion.close();
        return lista;
    }

    /**
     * Consulta que devuelve las 3 pociones más caras y permite filtrar por tamaño
     * @return Lista de objetos con las pociones
     */
    public List<Pociones> pocionesPorTamanio(Pociones.Tamanio tamanio){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "FROM Pociones P WHERE P.tamanio = :tamanio ORDER BY P.precio DESC";
        Query query1 = sesion.createQuery(query);
        query1.setParameter("tamanio", tamanio);
        query1.setMaxResults(3);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }

    /**
     * Consulta que devuelve las 10 pociones que tienen más ingredientes
     * @return Lista de objetos con el nombre de la pocion y el número de ingredientes
     */
    public List<Object[]> pocionesConMasIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.nombrePocion, COUNT(R.ingrediente.idIngrediente) AS total_ingredientes FROM Pociones P JOIN Recetas R ON P.idPocion = R.pocion.idPocion GROUP BY P.nombrePocion ORDER BY total_ingredientes DESC";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Object[]> lista = query1.list();
        sesion.close();
        return lista;
    }

    /**
     * Consulta que devuelve el promedio de ingredientes por pocion agrupado por escuela
     * @return Lista de objetos con la escuela y el promedio de ingredientes
     */
    public List<Object[]> PromedioDeIngredientesPorPocion(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.escuela, COUNT(R.ingrediente) AS promedio_ingredientes FROM Pociones P JOIN Recetas R ON P.idPocion = R.pocion.idPocion GROUP BY P.escuela";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Object[]> lista = query1.list();
        sesion.close();
        return lista;
    }
}
