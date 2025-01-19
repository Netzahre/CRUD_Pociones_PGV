package org.example.BaseDatos.interfacesDAO;

import org.example.objetos.Usuario;

public interface UsuarioDAO {
    boolean insertar(String nombreUsuario, byte[] contrasena, boolean esAdmin);
    Usuario buscarPorID(int idUsuario);
    Usuario buscarPorNombre(String nombreUsuario);
}
