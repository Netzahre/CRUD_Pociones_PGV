package org.example.BaseDatos.implementacionesCRUD;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.interfacesDAO.UsuarioDAO;
import org.example.objetos.Usuario;
import org.hibernate.Session;

public class UsuarioCRUD implements UsuarioDAO {
    public boolean insertar(String nombreUsuario, byte[] contrasena, boolean esAdmin) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuario usuarioNuevo = new Usuario(nombreUsuario, contrasena, esAdmin);
        try {
            sesion.beginTransaction();
            sesion.persist(usuarioNuevo);
            sesion.getTransaction().commit();
            return true;

        } catch (Exception e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
            sesion.getTransaction().rollback();
            return false;
        } finally {
            sesion.close();
        }
    }

    public Usuario buscarPorID(int idUsuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = null;
        try {
            usuario = sesion.get(Usuario.class, idUsuario);
        } catch (Exception e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        } finally {
            sesion.close();
        }
        return usuario;
    }

    public Usuario buscarPorNombre(String nombreUsuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = null;
        try {
            usuario = (Usuario) sesion.createQuery("from Usuario where nombreUsuario = :nombreUsuario")
                    .setParameter("nombreUsuario", nombreUsuario)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        } finally {
            sesion.close();
        }
        return usuario;
    }
}
