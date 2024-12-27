package org.example.BaseDatos;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Configuration config = new Configuration().configure();

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = config.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void cerrarSesion() {
        sessionFactory.close();
    }
}
