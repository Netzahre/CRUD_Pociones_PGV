package org.example;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase para manejar las conexiones de los clientes
 */
public class Servidor {
    private static final int PUERTO = 9069;
    private static final int MAXIMO_CONEXIONES = 3;
    private ServerSocket serverSocket;
    private ExecutorService colaEjecutor;

    public static void main(String[] args) throws IOException {
        //Cosa maligna
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }

    /**
     * Metodo para manejar las conexiones de los clientes
     * @throws IOException Si ocurre un error de entrada/salida
     */
    public void iniciar() throws IOException {
        serverSocket = new ServerSocket(PUERTO);
        colaEjecutor = Executors.newFixedThreadPool(MAXIMO_CONEXIONES);

        System.out.println("Servidor iniciado y esperando conexiones...");
        while (true) {
            try {
                // Aceptar una nueva conexión
                Socket socket = serverSocket.accept();
                System.out.println("Se intenta conectar el cliente "+ socket.getInetAddress());
                // Asignar la conexión a un hilo en el pool
                Thread hilo = new Thread(new ManejadorCliente(socket));
                colaEjecutor.submit(hilo);

            } catch (IOException e) {
                System.out.println("Error al aceptar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para manejar las conexiones de los clientes
     * @param socket Socket de la conexión
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void serverLleno(Socket socket) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {
            salida.writeObject("LLENO");
        }
    }

    /**
     * Metodo para manejar las conexiones de los clientes
     * @throws IOException Si ocurre un error de entrada/salida
     */
    //Aunque no lo este usando lo dejo por buena practica.
    public void cerrar() throws IOException {
        serverSocket.close();
        colaEjecutor.shutdown();
    }
}