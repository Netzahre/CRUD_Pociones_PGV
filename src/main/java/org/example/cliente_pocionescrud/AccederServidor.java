package org.example.cliente_pocionescrud;

import org.example.objetos.Pociones;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class AccederServidor {
    private Socket socket;
    private ObjectOutputStream  salidaDatos;
    private ObjectInputStream entradaDatos;

    // Establecer conexion con el server
    public AccederServidor(String host, int puerto) throws IOException {
        // Conexión al servidor en el puerto especificado
        socket = new Socket(host, puerto);
        salidaDatos = new ObjectOutputStream(socket.getOutputStream());
        entradaDatos = new ObjectInputStream(socket.getInputStream());
    }

    // Método para enviar mensajes al servidor
    public void enviarMensaje(String mensaje) throws IOException {
        salidaDatos.writeObject(mensaje);
    }

    // Método para recibir mensajes del servidor
    public String recibirMensaje() throws IOException {
        return entradaDatos.readLine();
    }

    // Método para recibir un objeto del servidor
    public Object recibirObjeto() throws IOException, ClassNotFoundException {
        return entradaDatos.readObject();
    }

    // Método para obtener la lista de pociones
    public List<Pociones> obtenerPociones() throws IOException, ClassNotFoundException {
        enviarMensaje("OBTENER_POCIONES"); // Solicitar la lista de pociones
        return (List<Pociones>) recibirObjeto(); // Recibir y devolver la lista de pociones
    }


    // Cerrar la conexión
    public void cerrarConexion() throws IOException {
        salidaDatos.close();
        entradaDatos.close();
        socket.close();
    }

    public boolean esServidorLleno() throws IOException {
        String respuesta = recibirMensaje();
        return respuesta != null && respuesta.contains("LLENO");
    }

}
