package org.example;

import com.sun.jdi.PrimitiveValue;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
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
    private static final String KEYSTORE = "src/main/resources/keystorePociones.jks";
    private static final String PASSWORD = "admin";
    private SSLServerSocket serverSocket;
    private ExecutorService colaEjecutor;

    //Si no se quiere usar SSL
    //private ServerSocket serverSocket;
    //private ExecutorService colaEjecutor;

    public static void main(String[] args) throws IOException {
        //Cosa maligna
        System.setProperty("javax.net.ssl.keyStore", KEYSTORE);
        System.setProperty("javax.net.ssl.keyStorePassword", PASSWORD);

        Servidor servidor = new Servidor();
        servidor.iniciar();
    }

    /**
     * Metodo para manejar las conexiones de los clientes
     * @throws IOException Si ocurre un error de entrada/salida
     */
    public void iniciar() throws IOException {
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        serverSocket = (SSLServerSocket) factory.createServerSocket(PUERTO);
        colaEjecutor = Executors.newFixedThreadPool(MAXIMO_CONEXIONES);

        System.out.println("Servidor iniciado y esperando conexiones...");
        while (true) {
            try {
                SSLSocket socket = (SSLSocket) serverSocket.accept();
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
     * @throws IOException Si ocurre un error de entrada/salida
     */
    //Aunque no lo este usando lo dejo por buena practica.
    public void cerrar() throws IOException {
        serverSocket.close();
        colaEjecutor.shutdown();
    }
}

