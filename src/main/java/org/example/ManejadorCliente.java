package org.example;

import org.example.BaseDatos.ConsultasFijas;
import org.example.BaseDatos.implementacionesCRUD.IngredientesCRUD;
import org.example.BaseDatos.implementacionesCRUD.PocionesCRUD;
import org.example.BaseDatos.implementacionesCRUD.UsuarioCRUD;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.example.objetos.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que maneja la conexión con el cliente
 */
public class ManejadorCliente implements Runnable {
    private final Socket socket;
    private List<Ingredientes> listaIngredientes;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition noHayLectores = lock.newCondition();
    private static final Condition noHayEscritores = lock.newCondition();
    private static boolean hayEscritor = false;
    private static int numLectores = 0;

    /**
     * Constructor de la clase
     *
     * @param socket Socket por el que se conecta el cliente
     */
    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    /**
     * Método que se ejecuta al iniciar el hilo
     */
    @Override
    public void run() {
        try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {
            PocionesCRUD crudPoc = new PocionesCRUD();
            IngredientesCRUD crudIng = new IngredientesCRUD();
            UsuarioCRUD crudUs = new UsuarioCRUD();
            ConsultasFijas consultasFijas = new ConsultasFijas();

            // Bucle infinito para recibir comandos del cliente
            while (true) {
                String comando = (String) entrada.readObject();

                switch (comando) {
                    case "OBTENER_POCIONES":
                        obtenerPociones(salida, crudPoc);
                        break;

                    case "CREAR_POCION":
                        crearPocion(entrada, crudPoc);
                        break;

                    case "ELIMINAR_POCION":
                        eliminarPocion(entrada, crudPoc);
                        break;

                    case "OBTENER_INGREDIENTES":
                        obtenerIngredientes(salida, crudIng);
                        break;

                    case "OBTENER_INGREDIENTES_POCION":
                        obtenerIngredientesPocion(entrada, salida, crudPoc);
                        break;

                    case "MODIFICAR_POCION":
                        modificarPocion(entrada, crudPoc);
                        break;

                    case "FILTRAR_POCIONES":
                        filtrarPociones(entrada, salida, crudPoc);
                        break;

                    case "FILTRAR_INGREDIENTES":
                        filtrarIngredientes(entrada, salida, crudIng);
                        break;

                    case "INGREDIENTES_MAS_DE_TRES_POCIONES":
                        ingredientesMasDeTresPociones(salida, consultasFijas);
                        break;

                    case "CANTIDAD_INGREDIENTES_ESCUELAS":
                        cantidadIngredientesEscuelas(salida, consultasFijas);
                        break;

                    case "TOP_3_POCIONES_CARAS":
                        top3PocionesCaras(entrada, salida, consultasFijas);
                        break;

                    case "CANTIDAD_TIPO_INGREDIENTE":
                        cantidadTipoIngredientes(salida, consultasFijas);
                        break;

                    case "TOP_10_POCIONES_INGREDIENTES":
                        top10PocionesIngredientes(salida, consultasFijas);
                        break;

                    case "INICIAR_SESION":
                        iniciarSesion(entrada, salida, crudUs);
                        break;

                    case "CREAR_USUARIO":
                        crearUsuario(entrada, salida, crudUs);
                        break;

                    default:
                        salida.writeObject("Comando no reconocido");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cliente desconectado");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Gestion de usuario
    private void iniciarSesion(ObjectInputStream entrada, ObjectOutputStream salida, UsuarioCRUD crudUs) throws IOException, ClassNotFoundException {
        String user = (String) entrada.readObject();
        byte[] passCifrada = (byte[]) entrada.readObject();
        Usuario usuario = crudUs.buscarPorNombre(user);
        if (usuario == null) {
            salida.writeObject("ERROR");
            salida.flush();
            return;
        }
        for (int i = 0; i < passCifrada.length; i++) {
            if (passCifrada[i] != usuario.getContrasena()[i]) {
                salida.writeObject("ERROR");
                salida.flush();
                return;
            }
        }
        System.out.println(usuario.getEsAdmin());
        if (usuario.getEsAdmin()) {
            salida.writeObject("ADMIN");
            salida.flush();
        } else {
            salida.writeObject("USUARIO");
            salida.flush();

        }
    }


    private void crearUsuario(ObjectInputStream entrada, ObjectOutputStream salida, UsuarioCRUD crudUs) throws IOException, ClassNotFoundException {
        String user = (String) entrada.readObject();
        byte[] passCifrada = (byte[]) entrada.readObject();
        boolean esAdmin = (boolean) entrada.readObject();
        Boolean existe = crudUs.insertar(user, passCifrada, esAdmin);
        System.out.println(existe);
        salida.writeObject(existe);
        salida.flush();
    }


    //Pociones

    /**
     * Método para obtener las pociones, accediendo a la sección crítica con exclusión mutua
     *
     * @param salida  Flujo de salida para enviar la lista de pociones
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException Error al enviar la lista de pociones
     */
    private void obtenerPociones(ObjectOutputStream salida, PocionesCRUD crudPoc) throws IOException {
        entrarALeer();
        List<Pociones> listaPociones = crudPoc.listaTodos();
        salida.writeObject(listaPociones);
        salirDeLeer();
    }

    /**
     * Método para crear una poción, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir la nueva poción
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException            Error al enviar la confirmación
     * @throws ClassNotFoundException Error al recibir la nueva poción
     */
    private void crearPocion(ObjectInputStream entrada, PocionesCRUD crudPoc) throws IOException, ClassNotFoundException {
        entrarAEscribir();
        Pociones nuevaPocion = (Pociones) entrada.readObject();
        Map<Ingredientes, Integer> ingredientes = (Map<Ingredientes, Integer>) entrada.readObject();
        crudPoc.insertar(nuevaPocion, ingredientes);
        salirDeEscribir();
    }

    /**
     * Método para eliminar una poción, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir el ID de la poción a eliminar
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException            Error al recibir el ID de la poción
     * @throws ClassNotFoundException Error al recibir el ID de la poción
     */
    private void eliminarPocion(ObjectInputStream entrada, PocionesCRUD crudPoc) throws IOException, ClassNotFoundException {
        entrarAEscribir();
        int idEliminar = Integer.parseInt((String) entrada.readObject());
        crudPoc.eliminar(idEliminar);
        salirDeEscribir();
    }

    /**
     * Método para modificar una poción, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir el ID de la poción a modificar
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException            Error al recibir el ID de la poción
     * @throws ClassNotFoundException Error al recibir la nueva poción
     */
    private void modificarPocion(ObjectInputStream entrada, PocionesCRUD crudPoc) throws IOException, ClassNotFoundException {
        entrarAEscribir();
        int idModificar = Integer.parseInt((String) entrada.readObject());
        Pociones pocionModificada = (Pociones) entrada.readObject();
        Map<Ingredientes, Integer> ingredientesModificados = (Map<Ingredientes, Integer>) entrada.readObject();
        crudPoc.actualizar(idModificar, pocionModificada, ingredientesModificados);
        salirDeEscribir();
    }

    /**
     * Método para obtener los ingredientes de una poción, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir el ID de la poción
     * @param salida  Flujo de salida para enviar la lista de ingredientes
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException            Error al recibir el ID de la poción
     * @throws ClassNotFoundException Error al recibir el ID de la poción
     */
    private void obtenerIngredientesPocion(ObjectInputStream entrada, ObjectOutputStream salida, PocionesCRUD crudPoc) throws IOException, ClassNotFoundException {
        entrarALeer();
        Map<Ingredientes, Integer> ingredientesPocion = crudPoc.obtenerIngredientesPocion(Integer.parseInt((String) entrada.readObject()));
        salida.writeObject(ingredientesPocion);
        salirDeLeer();
    }

    /**
     * Método para filtrar pociones, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir los filtros
     * @param salida  Flujo de salida para enviar la lista de pociones filtradas
     * @param crudPoc Objeto para acceder a la base de datos de pociones
     * @throws IOException            Error al recibir los filtros
     * @throws ClassNotFoundException Error al recibir los filtros
     */
    private void filtrarPociones(ObjectInputStream entrada, ObjectOutputStream salida, PocionesCRUD crudPoc) throws IOException, ClassNotFoundException {
        Map<String, Object> filtros = (Map<String, Object>) entrada.readObject();
        List<Pociones> pocionesFiltradas = crudPoc.obtenerPocionesFiltradas(filtros);
        salida.writeObject(pocionesFiltradas);
    }

    // Ingredientes

    /**
     * Método para obtener los ingredientes, accediendo a la sección crítica con exclusión mutua
     *
     * @param salida  Flujo de salida para enviar la lista de ingredientes
     * @param crudIng Objeto para acceder a la base de datos de ingredientes
     * @throws IOException Error al enviar la lista de ingredientes
     */
    private void obtenerIngredientes(ObjectOutputStream salida, IngredientesCRUD crudIng) throws IOException {
        entrarALeer();
        listaIngredientes = crudIng.listaTodos();
        salida.writeObject(listaIngredientes);
        salirDeLeer();
    }

    /**
     * Método para filtrar ingredientes, accediendo a la sección crítica con exclusión mutua
     *
     * @param entrada Flujo de entrada para recibir los filtros
     * @param salida  Flujo de salida para enviar la lista de ingredientes filtrados
     * @param crudIng Objeto para acceder a la base de datos de ingredientes
     * @throws IOException            Error al recibir los filtros
     * @throws ClassNotFoundException Error al recibir los filtros
     */
    private void filtrarIngredientes(ObjectInputStream entrada, ObjectOutputStream salida, IngredientesCRUD crudIng) throws IOException, ClassNotFoundException {
        Map<String, Object> filtros = (Map<String, Object>) entrada.readObject();
        List<Ingredientes> ingredientesFiltrados = crudIng.obtenerIngredientesFiltrados(filtros);
        salida.writeObject(ingredientesFiltrados);
    }

    //Consultas fijas con seccion critica

    /**
     * Método para realizar la consulta de ingredientes que se encuentran en más de tres pociones
     *
     * @param salida         Flujo de salida para enviar la lista de ingredientes
     * @param consultasFijas Objeto para acceder a las consultas fijas
     * @throws IOException Error al enviar la lista de ingredientes
     */
    private void ingredientesMasDeTresPociones(ObjectOutputStream salida, ConsultasFijas consultasFijas) throws IOException {
        entrarALeer();
        List<Object[]> ingredientesMasDeTresPociones = consultasFijas.ingredientesEnMasDeTresPociones();
        salida.writeObject(ingredientesMasDeTresPociones);
        salirDeLeer();
    }

    /**
     * Método para realizar la consulta de la cantidad de ingredientes promedio por pocion en las escuelas
     *
     * @param salida         Flujo de salida para enviar la lista de ingredientes
     * @param consultasFijas Objeto para acceder a las consultas fijas
     * @throws IOException Error al enviar la lista de ingredientes
     */
    private void cantidadIngredientesEscuelas(ObjectOutputStream salida, ConsultasFijas consultasFijas) throws IOException {
        entrarALeer();
        List<Object[]> cantidadIngredientesEscuelas = consultasFijas.PromedioDeIngredientesPorPocion();
        salida.writeObject(cantidadIngredientesEscuelas);
        salirDeLeer();
    }

    /**
     * Método para realizar la consulta de las 3 pociones más caras de un tamaño específico
     *
     * @param entrada        Flujo de entrada para recibir el tamaño de la poción
     * @param salida         Flujo de salida para enviar la lista de pociones
     * @param consultasFijas Objeto para acceder a las consultas fijas
     * @throws IOException            Error al recibir el tamaño de la poción
     * @throws ClassNotFoundException Error al recibir el tamaño de la poción
     */
    private void top3PocionesCaras(ObjectInputStream entrada, ObjectOutputStream salida, ConsultasFijas consultasFijas) throws IOException, ClassNotFoundException {
        entrarALeer();
        String tamanio = ((String) entrada.readObject()).toUpperCase().trim();
        List<Pociones> top3PocionesCaras = consultasFijas.pocionesPorTamanio(Pociones.Tamanio.valueOf(tamanio));
        salida.writeObject(top3PocionesCaras);
        salirDeLeer();
    }

    /**
     * Método para realizar la consulta de la cantidad de ingredientes por tipo
     *
     * @param salida         Flujo de salida para enviar la lista de ingredientes
     * @param consultasFijas Objeto para acceder a las consultas fijas
     * @throws IOException Error al enviar la lista de ingredientes
     */
    private void cantidadTipoIngredientes(ObjectOutputStream salida, ConsultasFijas consultasFijas) throws IOException {
        entrarALeer();
        List<Object[]> cantidadTipoIngredientes = consultasFijas.totalTiposIngredientes();
        salida.writeObject(cantidadTipoIngredientes);
        salirDeLeer();
    }

    /**
     * Método para realizar la consulta de las 10 pociones con más ingredientes
     *
     * @param salida         Flujo de salida para enviar la lista de pociones
     * @param consultasFijas Objeto para acceder a las consultas fijas
     * @throws IOException Error al enviar la lista de pociones
     */
    private void top10PocionesIngredientes(ObjectOutputStream salida, ConsultasFijas consultasFijas) throws IOException {
        entrarALeer();
        List<Object[]> top10PocionesIngredientes = consultasFijas.pocionesConMasIngredientes();
        salida.writeObject(top10PocionesIngredientes);
        salirDeLeer();
    }

    /**
     * Método para entrar a la sección crítica de lectura
     */
    private void entrarALeer() {
        lock.lock();
        try {

            while (hayEscritor) {
                noHayLectores.await();
            }

            numLectores++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            lock.unlock();
        }
    }

    /**
     * Método para salir de la sección crítica de lectura
     */
    private void salirDeLeer() {
        lock.lock();
        try {
            numLectores--;
            if (numLectores == 0) {
                noHayEscritores.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Método para entrar a la sección crítica de escritura
     */
    private void entrarAEscribir() {
        lock.lock();
        try {
            while (numLectores > 0) {
                noHayEscritores.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Método para salir de la sección crítica de escritura
     */
    private void salirDeEscribir() {
        lock.lock();
        hayEscritor = false;
        noHayLectores.signal();
        lock.unlock();
    }
}

