package org.example.cliente_pocionescrud;

import java.io.IOException;

/**
 * Clase que se encarga de gestionar la conexión con el servidor.
 * Se encarga de crear una instancia de AccederServidor y de cerrar la conexión.
 */
public class ConexionServidor {

    private static AccederServidor accederServidor;
    private static boolean esUsuario = true;
    /**
     * Obtiene una instancia de AccederServidor.
     * Si ya existe una instancia, la devuelve.
     * Si no existe, crea una nueva instancia.
     * @param host Host del servidor
     * @param puerto Puerto del servidor
     * @return Instancia de AccederServidor
     * @throws IOException Si ocurre un error al crear la conexión
     */
    public static AccederServidor getAccederServidor(String host, int puerto) throws IOException {
        if (accederServidor == null) {
            accederServidor = AccederServidor.getInstance(host, puerto);
        }
        return accederServidor;
    }

    /**
     * Cierra la conexión con el servidor.
     * @throws IOException Si ocurre un error al cerrar la conexión
     * No se usa en el proyecto, pero se deja por si se necesita en un futuro.
     */
    public static void cerrarConexion() throws IOException {
        if (accederServidor != null) {
            accederServidor.cerrarConexion();
            accederServidor = null;
        }
    }

    /**
     * Getter de esUsuario
     * @return esUsuario
     */
    public static boolean isEsUsuario() {
        return esUsuario;
    }

    /**
     * Setter de esUsuario
     * @param esUsuario Valor a asignar a esUsuario
     */
    public static void setEsUsuario(boolean esUsuario) {
        ConexionServidor.esUsuario = esUsuario;
    }
}
