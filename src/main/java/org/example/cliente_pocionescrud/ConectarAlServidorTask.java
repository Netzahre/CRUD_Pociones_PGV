package org.example.cliente_pocionescrud;

import javafx.concurrent.Task;

import java.io.IOException;

/**
 * Clase que se encarga de intentar conectar al servidor.
 * Extiende de Task<Boolean> para poder ejecutar la conexión en un hilo separado.
 * Si no, bloquearía la interfaz gráfica.
 * Creeme, no quieres que la interfaz gráfica se bloquee.
 */
public class ConectarAlServidorTask extends Task<Boolean> {
    private final String host;
    private final int puerto;

    /**
     * Constructor.
     * @param host Host del servidor.
     * @param puerto Puerto del servidor.
     */
    public ConectarAlServidorTask(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Intenta conectar al servidor.
     * @return true si la conexión fue exitosa, false en caso contrario.

     */
    @Override
    protected Boolean call() {
        AccederServidor accederServidor = null;
        try {
            System.out.println("Intentando conectar al servidor...");
            accederServidor = AccederServidor.getInstance(host, puerto);
            System.out.println("Conexión exitosa.");

            System.out.println("Conexión establecida correctamente.");
            return true;
        } catch (IOException e) {
            updateMessage("Error al intentar conectar con el servidor.");
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
            return false;
        }
    }
}
