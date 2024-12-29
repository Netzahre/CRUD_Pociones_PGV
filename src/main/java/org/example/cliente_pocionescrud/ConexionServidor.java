package org.example.cliente_pocionescrud;

import java.io.IOException;

public class ConexionServidor {

    private static AccederServidor accederServidor;

    // Obtiene la instancia de AccederServidor, creando una nueva si no existe
    public static AccederServidor getAccederServidor(String host, int puerto) throws IOException {
        if (accederServidor == null) {
            accederServidor = AccederServidor.getInstance(host, puerto);
        }
        return accederServidor;
    }

    // Cierra la conexión cuando se termine de usar
    public static void cerrarConexion() throws IOException {
        if (accederServidor != null) {
            accederServidor.cerrarConexion();
            accederServidor = null; // Opcional: limpiar la instancia
        }
    }
}
