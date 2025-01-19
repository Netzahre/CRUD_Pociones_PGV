package org.example.cliente_pocionescrud;

import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Clase para acceder al servidor de la aplicación
 */
public class AccederServidor {
    private static AccederServidor instance;
    //private final Socket socket;
    private static SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private final SSLSocket socket;
    private final ObjectOutputStream salidaDatos;
    private final ObjectInputStream entradaDatos;

    /**
     * Constructor privado para la clase AccederServidor
     * Evita que se puedan crear instancias de la clase desde fuera
     *
     * @param host   Dirección IP del servidor
     * @param puerto Puerto del servidor
     * @throws IOException Si hay un error al crear el socket
     */
    private AccederServidor(String host, int puerto) throws IOException {
//        socket = new Socket(host, puerto);

        socket = (SSLSocket) factory.createSocket(host, puerto);
        salidaDatos = new ObjectOutputStream(socket.getOutputStream());
        entradaDatos = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Método para obtener una instancia de la clase AccederServidor
     * Utiliza el patrón Singleton para asegurar que solo se crea una instancia
     *
     * @param host   Dirección IP del servidor
     * @param puerto Puerto del servidor
     * @return Instancia de la clase AccederServidor
     * @throws IOException Si hay un error al crear la instancia
     */
    public static synchronized AccederServidor getInstance(String host, int puerto) throws IOException {
        if (instance == null) {
            instance = new AccederServidor(host, puerto);
        }
        return instance;
    }

    /**
     * Método para enviar un mensaje al servidor
     *
     * @param mensaje Mensaje a enviar
     * @throws IOException Si hay un error al enviar el mensaje
     */
    public void enviarMensaje(String mensaje) throws IOException {
        salidaDatos.writeObject(mensaje);
    }

    /**
     * Método para enviar una poción modificada al servidor
     *
     * @param idPocion                ID de la poción a modificar
     * @param pocionModificada        Poción modificada
     * @param ingredientesModificados Mapa con los ingredientes modificados
     * @throws IOException Si hay un error al enviar los datos
     */
    public void enviarPocionModificada(int idPocion, Pociones pocionModificada, Map<Ingredientes, Integer> ingredientesModificados) throws IOException {
        enviarMensaje("MODIFICAR_POCION");
        enviarMensaje(String.valueOf(idPocion));
        enviarObjeto(pocionModificada);
        enviarObjeto(ingredientesModificados);
    }

    /**
     * Método para enviar un objeto al servidor
     *
     * @param objeto Objeto a enviar
     * @throws IOException Si hay un error al enviar el objeto
     */
    private void enviarObjeto(Object objeto) throws IOException {
        salidaDatos.writeObject(objeto);
    }

    /**
     * Método para recibir un mensaje del servidor
     *
     * @return Mensaje recibido
     * @throws IOException Si hay un error al recibir el mensaje
     */
    public String recibirMensaje() throws IOException {
        return entradaDatos.readLine();
    }

    /**
     * Método para recibir un objeto del servidor
     *
     * @return Objeto recibido
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public Object recibirObjeto() throws IOException, ClassNotFoundException {
        try {
            return entradaDatos.readObject();
        } catch (IOException e) {
            System.err.println("Error al recibir objeto desde el servidor: " + e.getMessage());
            throw e; // Lanza la excepción para que el llamador pueda manejarla
        } catch (ClassNotFoundException e) {
            System.err.println("Clase desconocida al deserializar objeto: " + e.getMessage());
            throw e; // Lanza la excepción para depurar problemas de serialización
        }
    }

    /**
     * Método para obtener la lista de pociones del servidor
     *
     * @return Lista de pociones
     */
    public List<Pociones> obtenerPociones() {
        try {
            enviarMensaje("OBTENER_POCIONES");
            return (List<Pociones>) recibirObjeto();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al obtener la lista de pociones: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método para crear una nueva poción en el servidor
     *
     * @param nuevaPocion  Poción a crear
     * @param ingredientes Ingredientes de la poción
     * @throws IOException Si hay un error al enviar los datos
     */
    public void crearPocion(Pociones nuevaPocion, Map<Ingredientes, Integer> ingredientes) throws IOException {
        enviarMensaje("CREAR_POCION");
        enviarObjeto(nuevaPocion);
        enviarObjeto(ingredientes);
    }

    /**
     * Método para obtener la lista de ingredientes del servidor
     *
     * @return Lista de ingredientes
     */
    public List<Ingredientes> obtenerIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("OBTENER_INGREDIENTES");
        return (List<Ingredientes>) recibirObjeto();
    }

    /**
     * Método para crear un nuevo ingrediente en el servidor
     *
     * @param idPocion ID de la poción a la que pertenece el ingrediente
     * @throws IOException Si hay un error al enviar los datos
     */
    public void eliminarPocion(int idPocion) throws IOException {
        enviarMensaje("ELIMINAR_POCION");
        enviarMensaje(String.valueOf(idPocion));
    }

    /**
     * Metodo para obtener los ingredientes de una poción
     *
     * @param idPocion ID de la poción
     * @return Mapa con los ingredientes de la poción
     * @throws IOException            Si hay un error al enviar los datos
     * @throws ClassNotFoundException Si hay un error al recibir el objeto
     */
    public Map<Ingredientes, Integer> obtenerIngredientesPocion(int idPocion) throws IOException, ClassNotFoundException {
        enviarMensaje("OBTENER_INGREDIENTES_POCION");
        enviarMensaje(String.valueOf(idPocion));
        return (Map<Ingredientes, Integer>) recibirObjeto();
    }

    /**
     * Método para enviar los filtros de búsqueda de pociones
     *
     * @param filtros Mapa con los filtros de búsqueda
     * @throws IOException Si hay un error al enviar los datos
     */
    public void enviarFiltrosPociones(Map<String, Object> filtros) throws IOException {
        enviarMensaje("FILTRAR_POCIONES");
        enviarObjeto(filtros);
    }

    /**
     * Método para enviar los filtros de búsqueda de ingredientes
     *
     * @param filtros Mapa con los filtros de búsqueda
     * @throws IOException Si hay un error al enviar los datos
     */
    public void enviarFiltrosIngredientes(Map<String, Object> filtros) throws IOException {
        enviarMensaje("FILTRAR_INGREDIENTES");
        enviarObjeto(filtros);
    }

    /**
     * Método para obtener la lista de ingredientes filtrados
     *
     * @return Lista de ingredientes
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Ingredientes> obtenerIngredientesFiltrados() throws IOException, ClassNotFoundException {
        return (List<Ingredientes>) recibirObjeto();
    }

    /**
     * Método para obtener la lista de pociones filtradas
     *
     * @return Lista de pociones
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Pociones> obtenerPocionesFiltradas() throws IOException, ClassNotFoundException {
        return (List<Pociones>) recibirObjeto();
    }

    //Consultas fijas

    /**
     * Método para obtener ingredientes que aparecen en más de tres pociones
     *
     * @return Lista con los ingredientes que aparecen en más de tres pociones
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Object[]> obtenerIngredientesEnMasDeTresPociones() throws IOException, ClassNotFoundException {
        enviarMensaje("INGREDIENTES_MAS_DE_TRES_POCIONES");
        return (List<Object[]>) recibirObjeto();
    }

    /**
     * Método para obtener la cantidad de ingredientes por escuela
     *
     * @return Lista con la cantidad de ingredientes por escuela
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Object[]> obtenerCantidadIngredientesEscuelas() throws IOException, ClassNotFoundException {
        enviarMensaje("CANTIDAD_INGREDIENTES_ESCUELAS");
        return (List<Object[]>) recibirObjeto();
    }

    /**
     * Metodo para obtener las pociones más caras filtrando por tamaño
     *
     * @param tamanio Tamaño de las pociones
     * @return Lista con las pociones más caras
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Pociones> obtenerTop3PocionesCaras(String tamanio) throws IOException, ClassNotFoundException {
        enviarMensaje("TOP_3_POCIONES_CARAS");
        enviarMensaje(tamanio);
        return (List<Pociones>) recibirObjeto();
    }

    /**
     * Método para obtener la cantidad usada de cada ingrediente en las pociones
     *
     * @return Lista con la cantidad usada de cada ingrediente en las pociones
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Object[]> obtenerCantidadVecesIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("CANTIDAD_TIPO_INGREDIENTE");
        return (List<Object[]>) recibirObjeto();
    }

    /**
     * Método para obtener las pociones con más ingredientes
     *
     * @return Lista con las pociones con más ingredientes
     * @throws IOException            Si hay un error al recibir el objeto
     * @throws ClassNotFoundException Si hay un error al deserializar el objeto
     */
    public List<Object[]> TOP10PocionesMuchosIngredientes() throws IOException, ClassNotFoundException {
        enviarMensaje("TOP_10_POCIONES_INGREDIENTES");
        return (List<Object[]>) recibirObjeto();
    }

    public String iniciarSesion(String user, byte[] pass)  {
        try {
            enviarMensaje("INICIAR_SESION");
            enviarObjeto(user);
            enviarObjeto(pass);
            return (String) recibirObjeto();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Metodo para crear un usuario en el servidor
     *
     * @param user Nombre de usuario
     * @param pass Contraseña
     * @return Si el usuario fue creado correctamente
     * @throws IOException Si hay un error al enviar los datos
     */
    public boolean crearUsuario(String user, byte[] pass, Boolean admin) throws IOException, ClassNotFoundException {
        enviarMensaje("CREAR_USUARIO");
        enviarObjeto(user);
        enviarObjeto(pass);
        enviarObjeto(admin);
        return (Boolean) recibirObjeto();
    }

    /**
     * Metodo para cerrar la conexión con el servidor
     *
     * @throws IOException Si hay un error al cerrar la conexión
     */
    public void cerrarConexion() throws IOException {
        salidaDatos.close();
        entradaDatos.close();
        socket.close();
    }
}
