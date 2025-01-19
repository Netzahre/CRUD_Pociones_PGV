package org.example.BaseDatos;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase que se encarga de gestionar la sesión de Hibernate.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static final Configuration config = new Configuration().configure();

    /**
     * Método que devuelve la sesión de Hibernate.
     *
     * @return La sesión de Hibernate.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = config.buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Método que cierra la sesión de Hibernate.
     * Como se cierra la aplicacion directamente no se necesita. Pero se deja por si se necesita en un futuro.
     */
    public static void cerrarSesion() {
        sessionFactory.close();
    }
}
