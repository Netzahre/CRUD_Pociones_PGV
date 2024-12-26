package org.example.cliente_pocionescrud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AccederServidor {
    private Socket socket;
    private PrintWriter salidaDatos;
    private BufferedReader entradaDatos;

    // Establecer conexion con el server
    public AccederServidor(String host, int puerto) throws IOException {
        // Conexión al servidor en el puerto especificado
        socket = new Socket(host, puerto);
        salidaDatos = new PrintWriter(socket.getOutputStream(), true);
        entradaDatos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Método para enviar mensajes al servidor
    public void enviarMensaje(String mensaje) {
        salidaDatos.println(mensaje);
    }

    // Método para recibir mensajes del servidor
    public String recibirMensaje() throws IOException {
        return entradaDatos.readLine();
    }

    // Cerrar la conexión
    public void cerrarConexion() throws IOException {
        salidaDatos.close();
        entradaDatos.close();
        socket.close();
    }

    public boolean esServidorLleno() throws IOException {
        String respuesta = recibirMensaje();
        return respuesta != null && respuesta.contains("Esperando conexión");
    }

}
