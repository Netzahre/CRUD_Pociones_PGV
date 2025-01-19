package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.objetos.Ingredientes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Clase controladorVerIngredientes
 * Esta clase es el controlador de la vista VerIngredientes.fxml
 * Se encarga de mostrar los ingredientes almacenados en el servidor
 *
 */
public class controladorVerIngredientes {
    AccederServidor servidor;
    @FXML
    private TextField nombreFiltro;
    @FXML
    private ComboBox<String> tipoFiltro;
    @FXML
    private TableView<Ingredientes> tablaIngredientes;
    @FXML
    private TableColumn<Ingredientes, Integer> columnaId;
    @FXML
    private TableColumn<Ingredientes, String> columnaNombre;
    @FXML
    private TableColumn<Ingredientes, String> columnaTipo;


    /**
     * Método initialize
     * Este método se ejecuta al cargar la vista VerIngredientes.fxml
     * Se encarga de inicializar los elementos de la interfaz gráfica
     * y de rellenar la tabla con los ingredientes almacenados en el servidor.
     *
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        tipoFiltro.getItems().addAll("VEGETAL", "MINERAL", "ORGANICO", "MAGICO", "TODOS");
        tipoFiltro.setValue("TODOS");
        try {
            rellenarTabla();
        } catch (IOException | ClassNotFoundException e) {
            mostrarMensaje("No se pudo obtener la lista de pociones del servidor.");
        }
    }

    /**
     * Método inicializarColumnas
     * Este método se encarga de inicializar las columnas de la tabla
     * de ingredientes.
     *
     */
    private void inicializarColumnas() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idIngrediente"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreIngrediente"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoIngrediente"));
    }

    /**
     * Método rellenarTabla
     * Este método se encarga de rellenar la tabla de ingredientes
     * con los ingredientes almacenados en el servidor.
     *
     * @throws IOException Si ocurre un error de entrada/salida
     * @throws ClassNotFoundException Si ocurre un error al cargar una clase
     */
    private void rellenarTabla() throws IOException, ClassNotFoundException {
        servidor = ConexionServidor.getAccederServidor("localhost", 9069);

        if (servidor == null) {
            mostrarMensaje("No hay conexión con el servidor.");
            return;
        }
        List<Ingredientes> listaIngredientes = servidor.obtenerIngredientes();

        tablaIngredientes.getItems().clear(); // Limpiar cualquier dato previo
        tablaIngredientes.getItems().addAll(listaIngredientes); // Agregar las nuevas pociones
    }

    /**
     * Método mostrarMensaje
     * Este método se encarga de mostrar un mensaje en una ventana emergente.
     *
     * @param mensaje Mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Método volverAtras
     * Este método se encarga de volver a la vista de acceso.
     *
     * @param event Evento de clic
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @FXML
    protected void volverAtras(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método filtrarIngredientes
     * Este método se encarga de filtrar los ingredientes de acuerdo a los
     * parámetros ingresados por el usuario.
     *
     * @throws IOException Si ocurre un error de entrada/salida
     * @throws ClassNotFoundException Si ocurre un error al cargar una clase
     */
    @FXML
    private void filtrarIngredientes() throws IOException, ClassNotFoundException {

        String nombre = nombreFiltro.getText().trim();
        String tipo = tipoFiltro.getValue();

        if (nombre.isEmpty()) {
            nombre = "%"; // Valor por defecto
        }

        Map<String, Object> filtros = Map.of(
                "nombre", nombre,
                "tipo", tipo
        );
        servidor.enviarFiltrosIngredientes(filtros);

        List<Ingredientes> ingredientesFiltrados = servidor.obtenerIngredientesFiltrados();

        tablaIngredientes.getItems().clear();
        tablaIngredientes.getItems().addAll(ingredientesFiltrados);
    }
}
