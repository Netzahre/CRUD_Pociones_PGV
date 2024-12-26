package org.example.objetos;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ConsultasFijas {


    //Top 10 pociones de una escuela específica
    public List<Pociones> topDiezEscuela(Pociones.Escuela escuela) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT P.nombrePocion, sum(LV.cantidad) as total_vendidas from Pociones P inner join LineaVentas LV on p.idPocion = lv.idPocion WHERE P.escuela = :escuela group by p.nombrePocion order by total_vendidas desc";
        Query query1 = sesion.createQuery(query);
        query1.setParameter("escuela", escuela);
        query1.setMaxResults(10);
        List<Pociones> pociones = query1.list();
        sesion.close();
        return pociones;
    }

    //Cantidad total de un tipo de ingrediente utilizado
    public List<Ingredientes> totalTiposIngredientes(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String query = "SELECT I.tipoIngrediente, COALESCE(SUM(R.cantidad), 0) AS total_usado FROM Ingredientes I LEFT JOIN Recetas R ON I.idIngrediente = R.idIngrediente GROUP BY I.tipoIngrediente ORDER BY total_usado DESC";
        Query query1 = sesion.createQuery(query);
        List<Ingredientes> lista = query1.list();
        sesion.close();
        return lista;
    }
}
