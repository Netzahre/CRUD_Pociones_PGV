package org.example.cliente_pocionescrud;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.List;
/**
 * Clase controladorVerConsultas
 * Esta clase es el controlador de la vista VerConsultas.fxml
 * Se encarga de mostrar las consultas realizadas al servidor
 * y de mostrar los resultados de las mismas en la interfaz gráfica.
 *
 * @version 1.0 2 Jun 2021
 */
public class controladorVerConsultas {
    AccederServidor servidor;
    @FXML
    private TableView<Pociones> tablatop3PocionesCaras;
    @FXML
    private TableColumn<Pociones, String> columnaTop3Tamanio;
    @FXML
    private TableColumn<Pociones, Double> columnaTop3Precio;

    @FXML
    private TableView<Object[]> tablaingredientesmas3pociones;
    @FXML
    private TableColumn<Object[], String> columnasMas3Ingredientes;
    @FXML
    private TableColumn<Object[], Long> columnasMas3Cantidad;

    @FXML
    private TableView<Object[]> tablacantidadIngredientesEscuelas;
    @FXML
    private TableColumn<Object[], String> columnaCantidadIngredientesEscuela;
    @FXML
    private TableColumn<Object[], Long> columnaCantidadIngredientesEscuelaCantidad;

    @FXML
    private TableView<Object[]> tablatop10pocionesMuchosIngredientes;
    @FXML
    private TableColumn<Object[], String> columnatop10MuchosIngredientesPocion;
    @FXML
    private TableColumn<Object[], Long> columnaTop10MuchosIngredientesCantidad;

    @FXML
    private TableView<Object[]> tablacantidadVecesIngrediente;
    @FXML
    private TableColumn<Object[], String> columnaCantidadVecesIngredienteNombre;
    @FXML
    private TableColumn<Object[], Long> columnaCantidadVecesIngredienteCantidad;

    @FXML
    private ComboBox<String> filtroTamanio;

    /**
     * Método initialize
     * Este método se ejecuta al cargar la vista VerConsultas.fxml
     * y se encarga de inicializar las columnas de las tablas
     * y de rellenar las tablas con los datos obtenidos del servidor.
     */
    public void initialize() {
        inicializarColumnas();
        filtroTamanio.getItems().addAll("Pequeño", "Mediano", "Grande");
        filtroTamanio.setValue("Pequeño");
        filtroTamanio.valueProperty().addListener((_, _, _) -> {
            try {
                rellenarTablas();
            } catch (IOException | ClassNotFoundException e) {
                mostrarMensaje("Algo fallo al rellenar las tablas con el nuevo filtro");
            }
        });
        try {
            rellenarTablas();
        } catch (IOException | ClassNotFoundException e) {
            mostrarMensaje("No se pudo obtener los datos del servidor.");
        }
    }

    /**
     * Método inicializarColumnas
     * Este método se encarga de inicializar las columnas de las tablas
     */
    private void inicializarColumnas() {
        // Ingredientes en más de 3 pociones
        columnasMas3Ingredientes.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue()[0]));
        columnasMas3Cantidad.setCellValueFactory(data -> new SimpleLongProperty((Long) data.getValue()[1]).asObject());

        // Cantidad de ingredientes por escuela
        columnaCantidadIngredientesEscuela.setCellValueFactory(data ->
                new SimpleStringProperty(((Pociones.Escuela) data.getValue()[0]).name()));
        columnaCantidadIngredientesEscuelaCantidad.setCellValueFactory(data -> new SimpleLongProperty((Long) data.getValue()[1]).asObject());

        // Top 3 pociones caras desde la List Pociones
        columnaTop3Tamanio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombrePocion()));
        columnaTop3Precio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrecio()).asObject());

        // Cantidad de veces que un ingrediente es utilizado
        columnaCantidadVecesIngredienteNombre.setCellValueFactory(data -> new SimpleStringProperty(((Ingredientes.TiposIngrediente) data.getValue()[0]).name()));
        columnaCantidadVecesIngredienteCantidad.setCellValueFactory(data -> new SimpleLongProperty((Long) data.getValue()[1]).asObject());

        // Top 10 pociones con más ingredientes
        columnatop10MuchosIngredientesPocion.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue()[0]));
        columnaTop10MuchosIngredientesCantidad.setCellValueFactory(data -> new SimpleLongProperty((Long) data.getValue()[1]).asObject());
    }

    /**
     * Método rellenarTablas
     * Este método se encarga de rellenar las tablas con los datos obtenidos del servidor
     * para cada consulta realizada.
     */
    private void rellenarTablas() throws IOException, ClassNotFoundException {
        servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        if (servidor == null) {
            mostrarMensaje("No hay conexión con el servidor.");
            return;
        }

        List<Object[]> ingredientesMas3Pociones = servidor.obtenerIngredientesEnMasDeTresPociones();
        List<Object[]> cantidadIngredientesEscuelas = servidor.obtenerCantidadIngredientesEscuelas();
        List<Pociones> top3PocionesCaras = servidor.obtenerTop3PocionesCaras(filtroTamanio.getValue());
        List<Object[]> cantidadVecesIngrediente = servidor.obtenerCantidadVecesIngredientes();
        List<Object[]> top10PocionesMuchosIngredientes = servidor.TOP10PocionesMuchosIngredientes();

        tablaingredientesmas3pociones.getItems().clear();
        tablacantidadIngredientesEscuelas.getItems().clear();
        tablatop3PocionesCaras.getItems().clear();
        tablacantidadVecesIngrediente.getItems().clear();
        tablatop10pocionesMuchosIngredientes.getItems().clear();

        tablaingredientesmas3pociones.getItems().addAll(ingredientesMas3Pociones);
        tablacantidadIngredientesEscuelas.getItems().addAll(cantidadIngredientesEscuelas);
        tablatop3PocionesCaras.getItems().addAll(top3PocionesCaras);
        tablacantidadVecesIngrediente.getItems().addAll(cantidadVecesIngrediente);
        tablatop10pocionesMuchosIngredientes.getItems().addAll(top10PocionesMuchosIngredientes);
    }

    /**
     * Método mostrarMensaje
     * Este método se encarga de mostrar un mensaje en la interfaz gráfica
     *
     * @param mensaje Contenido del mensaje
     */
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Método atras
     * Este método se encarga de volver a la vista Acceso.fxml
     *
     * @param event Evento de click
     */
    @FXML
    protected void atras(ActionEvent event) throws IOException {
        //TODO implementar la logica de server
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }
}