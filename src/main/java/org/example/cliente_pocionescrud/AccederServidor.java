package org.example.cliente_pocionescrud;

import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // Método para modificar una poción (enviar la poción y su mapa de ingredientes)
    public void enviarPocionModificada(int idPocion, Pociones pocionModificada, Map<Ingredientes, Integer> ingredientesModificados) throws IOException {
        // Enviar tipo de operación al servidor
        enviarMensaje("MODIFICAR_POCION");
        // Enviar el ID de la poción que se va a modificar
        enviarMensaje(String.valueOf(idPocion));
        // Enviar la poción modificada
        enviarObjeto(pocionModificada);
        // Enviar el mapa de ingredientes y cantidades modificados
        enviarObjeto(ingredientesModificados);
    }

    // Método auxiliar para enviar un objeto al servidor
    private void enviarObjeto(Object objeto) throws IOException {
        salidaDatos.writeObject(objeto);
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

    // Crear nueva poción
    public void crearPocion(Pociones nuevaPocion, Map<Ingredientes, Integer> ingredientes) throws IOException {
        enviarMensaje("CREAR_POCION");
        enviarObjeto(nuevaPocion);
        enviarObjeto(ingredientes);
    }

    // Método para obtener la lista de ingredientes
    public List<Ingredientes> obtenerIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("OBTENER_INGREDIENTES"); // Solicitar los ingredientes
        return (List<Ingredientes>) recibirObjeto(); // Recibir y devolver la lista de ingredientes
    }

    // Eliminar una poción por ID
    public void eliminarPocion(int idPocion) throws IOException {
        enviarMensaje("ELIMINAR_POCION");
        enviarMensaje(String.valueOf(idPocion));
    }

    // Método para obtener los ingredientes de una poción
    public Map<Ingredientes, Integer> obtenerIngredientesPocion(int idPocion) throws IOException, ClassNotFoundException {
        enviarMensaje("OBTENER_INGREDIENTES");
        enviarMensaje(String.valueOf(idPocion));
        return (Map<Ingredientes, Integer>) recibirObjeto();
    }

    // Método para enviar los ingredientes de una poción
    public void enviarParametrosDeFiltrado(Map<String, Object> filtros) throws IOException {
        enviarMensaje("FILTRAR_POCIONES"); // Indicar la operación al servidor
        enviarObjeto(filtros); // Enviar el mapa de filtros
    }

    // **Nuevo** Método para recibir una lista filtrada de pociones
    public List<Pociones> obtenerPocionesFiltradas() throws IOException, ClassNotFoundException {
        return (List<Pociones>) recibirObjeto();
    }

    //Consultas fijas
    // Obtener ingredientes en más de tres pociones
    public List<Object[]> obtenerIngredientesEnMasDeTresPociones() throws IOException, ClassNotFoundException {
        enviarMensaje("INGREDIENTES_MAS_DE_TRES_POCIONES");
        return (List<Object[]>) recibirObjeto();
    }

    // Obtener cantidad de ingredientes por escuela
    public List<Object[]> obtenerCantidadIngredientesEscuelas() throws IOException, ClassNotFoundException {
        enviarMensaje("CANTIDAD_INGREDIENTES_ESCUELAS");
        return (List<Object[]>) recibirObjeto();
    }

    // Obtener top 3 pociones caras
    public List<Object[]> obtenerTop3PocionesCaras() throws IOException, ClassNotFoundException {
        enviarMensaje("TOP_3_POCIONES_CARAS");
        return (List<Object[]>) recibirObjeto();
    }
    // Obtener cantidad de veces que aparece cada tipo ingrediente
    public List<Object[]> obtenerCantidadVecesIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("CANTIDAD_TIPO_INGREDIENTE");
        return (List<Object[]>) recibirObjeto();
    }
    // Obtener pociones con mas ingredientes
    public List<Object[]> TOP10PocionesMuchosIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("TOP_10_POCIONES_INGREDIENTES");
        return (List<Object[]>) recibirObjeto();
    }

    // Cerrar la conexión
    public void cerrarConexion() throws IOException {
        if (socket == null) {
            return;
        }
        salidaDatos.close();
        entradaDatos.close();
        socket.close();
    }

    public boolean esServidorLleno() throws IOException {
        String respuesta = recibirMensaje();
        return respuesta != null && respuesta.contains("LLENO");
    }

}
