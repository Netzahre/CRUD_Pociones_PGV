package org.example;
import org.example.BaseDatos.ConsultasFijas;
import org.example.BaseDatos.implementacionesCRUD.IngredientesCRUD;
import org.example.BaseDatos.implementacionesCRUD.PocionesCRUD;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import java.io .*;
import java.net.Socket;
import java.util .*;

public class ManejadorCliente implements Runnable {
    private Socket socket;
    private List<Pociones> listaPociones;
    private List<Ingredientes> listaIngredientes;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream())) {
            PocionesCRUD crudPoc = new PocionesCRUD();
            IngredientesCRUD crudIng = new IngredientesCRUD();
            ConsultasFijas consultasFijas = new ConsultasFijas();

            while (true) {
                String comando = (String) entrada.readObject();

                switch (comando) {
                    case "OBTENER_POCIONES":
                        listaPociones = crudPoc.listaTodos();
                        salida.writeObject(listaPociones);
                        break;

                    case "CREAR_POCION":
                        Pociones nuevaPocion = (Pociones) entrada.readObject();
                        Map<Ingredientes, Integer> ingredientes = (Map<Ingredientes, Integer>) entrada.readObject();
                        crudPoc.insertar(nuevaPocion, ingredientes);
                        salida.writeObject("Poción creada exitosamente");
                        break;

                    case "ELIMINAR_POCION":
                        int idEliminar = Integer.parseInt((String) entrada.readObject());
                        crudPoc.eliminar(idEliminar);
                        salida.writeObject("Poción eliminada exitosamente");
                        break;

                    case "OBTENER_INGREDIENTES":
                        listaIngredientes = crudIng.listaTodos();
                        salida.writeObject(listaIngredientes);
                        break;

                    case "MODIFICAR_POCION":
                        int idModificar = Integer.parseInt((String) entrada.readObject());
                        Pociones pocionModificada = (Pociones) entrada.readObject();
                        Map<Ingredientes, Integer> ingredientesModificados = (Map<Ingredientes, Integer>) entrada.readObject();
                        crudPoc.actualizar(idModificar, pocionModificada, ingredientesModificados);
                        salida.writeObject("Poción modificada exitosamente");
                        break;

                    case "FILTRAR_POCIONES":
                        Map<String, Object> filtros = (Map<String, Object>) entrada.readObject();
                        List<Pociones> pocionesFiltradas = crudPoc.obtenerPocionesFiltradas(filtros);
                        salida.writeObject(pocionesFiltradas);
                        break;

                    case "INGREDIENTES_MAS_DE_TRES_POCIONES":
                        List<Object[]> ingredientesMasDeTresPociones = consultasFijas.ingredientesEnMasDeTresPociones();
                        salida.writeObject(ingredientesMasDeTresPociones);
                        break;

                    case "CANTIDAD_INGREDIENTES_ESCUELAS":
                        List<Object[]> cantidadIngredientesEscuelas = consultasFijas.PromedioDeIngredientesPorPocion();
                        salida.writeObject(cantidadIngredientesEscuelas);
                        break;

                    case "TOP_3_POCIONES_CARAS":
                        //Recibir los datos
                        String tamanio = (String) ((String) entrada.readObject()).toUpperCase().trim();
                        List<Pociones> top3PocionesCaras = consultasFijas.pocionesPorTamanio(Pociones.Tamanio.valueOf(tamanio));
                        salida.writeObject(top3PocionesCaras);
                        break;

                    case "CANTIDAD_TIPO_INGREDIENTE":
                        List<Object[]> cantidadTipoIngredientes = consultasFijas.totalTiposIngredientes();
                        salida.writeObject(cantidadTipoIngredientes);
                        break;

                    case "TOP_10_POCIONES_INGREDIENTES":
                        List<Object[]> top10PocionesIngredientes = consultasFijas.pocionesConMasIngredientes();
                        salida.writeObject(top10PocionesIngredientes);
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
}

