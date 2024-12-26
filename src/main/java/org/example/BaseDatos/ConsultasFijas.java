package org.example.BaseDatos;

import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ConsultasFijas {


    //Ingredientes que se usan en más de 3 pociones diferentes
    public List<Ingredientes> ingredientesEnMasDeTresPociones() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.nombreIngrediente, COUNT(DISTINCT R.pocion.idPocion) AS pociones_usadas FROM Ingredientes I JOIN Recetas R ON I.idIngrediente = R.ingrediente.idIngrediente GROUP BY I.nombreIngrediente HAVING pociones_usadas > 3";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Ingredientes> lista = query1.list();
        sesion.close();
        return lista;
    }

    //Cantidad total de tipo de ingrediente utilizado
    public List<Ingredientes> totalTiposIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.tipoIngrediente, COALESCE(SUM(R.cantidad), 0) AS total_usado FROM Ingredientes I LEFT JOIN Recetas R ON I.idIngrediente = R.ingrediente.idIngrediente GROUP BY I.tipoIngrediente ORDER BY total_usado DESC";
        Query query1 = sesion.createQuery(query);
        List<Ingredientes> lista = query1.list();
        sesion.close();
        return lista;
    }

    //Pociones mas caras por tamaño
    public List<Pociones> pocionesPorTamanio(Pociones.Tamanio tamanio){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.tamanio, MAX(P.precio) AS precio_maximo FROM Pociones P WHERE P.tamanio = :tamanio GROUP BY P.tamanio ORDER BY precio_maximo DESC";
        Query query1 = sesion.createQuery(query);
        query1.setParameter("tamanio", tamanio);
        query1.setMaxResults(3);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }

    //top 10 pociones con mayor cantidad de ingredientes
    public List<Pociones> pocionesConMasIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.nombrePocion, COUNT(R.ingrediente.idIngrediente) AS total_ingredientes FROM Pociones P JOIN Recetas R ON P.idPocion = R.pocion.idPocion GROUP BY P.nombrePocion ORDER BY total_ingredientes DESC";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }

    //Pociones de cada escuela con su promedio de ingredientes utilizados:
    public List<Pociones> PromedioDeIngredientesPorPocion(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.escuela, AVG(COUNT(R.ingrediente.idIngrediente)) AS promedio_ingredientes FROM Pociones P JOIN Recetas R ON P.idPocion = R.pocion.idPocion GROUP BY P.escuela";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }


}
