package org.example.BaseDatos;

import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ConsultasFijas {


    //Ingredientes que se usan en más de 3 pociones diferentes
    public List<Pociones> ingredientesEnMasDeTresPociones() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.nombre, COUNT(DISTINCT R.id_pocion) AS pociones_usadas FROM ingredientes I JOIN recetas R ON I.id = R.id_ingrediente GROUP BY I.nombre HAVING pociones_usadas > 3;\n";

        Query query1 = sesion.createQuery(query);
        query1.setParameter("escuela", escuela);
        query1.setMaxResults(10);
        List<Pociones> pociones = query1.list();
        sesion.close();
        return pociones;
    }

    //Cantidad total de tipo de ingrediente utilizado
    public List<Ingredientes> totalTiposIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.tipoIngrediente, COALESCE(SUM(R.cantidad), 0) AS total_usado FROM Ingredientes I LEFT JOIN Recetas R ON I.idIngrediente = R.idIngrediente GROUP BY I.tipoIngrediente ORDER BY total_usado DESC";
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
        query1.setMaxResults(10);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }

    //top 10 pociones con mayor cantidad de ingredientes
    public List<Pociones> pocionesConMasIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.nombrePocion, COUNT(R.idIngrediente) AS total_ingredientes FROM Pociones P JOIN Recetas R ON P.idPocion = R.idPocion GROUP BY P.nombrePocion ORDER BY total_ingredientes DESC";
        Query query1 = sesion.createQuery(query);
        query1.setMaxResults(10);
        List<Pociones> lista = query1.list();
        sesion.close();
        return lista;
    }

}
