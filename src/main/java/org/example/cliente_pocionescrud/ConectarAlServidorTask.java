package org.example.cliente_pocionescrud;

import javafx.concurrent.Task;
import java.io.IOException;

public class ConectarAlServidorTask extends Task<Boolean> {
    private final String host;
    private final int puerto;

    public ConectarAlServidorTask(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }
    @Override
    protected Boolean call() throws Exception {
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
