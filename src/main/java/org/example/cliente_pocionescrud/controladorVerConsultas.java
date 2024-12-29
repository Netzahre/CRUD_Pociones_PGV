package org.example.cliente_pocionescrud;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class controladorVerConsultas {
    @FXML
    private TableView tablatop3PocionesCaras;
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

    // Este método inicializa las tablas y obtiene los datos
    public void initialize() {
        inicializarColumnas();
        //rellenar el listView
        filtroTamanio.getItems().addAll("Pequeño", "Mediano", "Grande");
        filtroTamanio.setValue("Pequeño");

        //Añadir un listener al combobox
        filtroTamanio.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rellenarTablas();
            } catch (IOException | ClassNotFoundException e) {
                mostrarMensaje("Error", "No se pudo obtener los datos del servidor.", Alert.AlertType.ERROR);
            }
        });
        try {
            rellenarTablas();
        } catch (IOException | ClassNotFoundException e) {
            mostrarMensaje("Error", "No se pudo obtener los datos del servidor.", Alert.AlertType.ERROR);
        }
    }

    // Inicializamos las columnas de todas las tablas
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

    // Método para rellenar las tablas con los datos obtenidos del servidor
    private void rellenarTablas() throws IOException, ClassNotFoundException {
        // Obtener la conexión al servidor
        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        if (servidor == null) {
            mostrarMensaje("Error", "No hay conexión con el servidor.", Alert.AlertType.ERROR);
            return;
        }

        // Solicitar y recibir los datos del servidor para cada consulta
        List<Object[]> ingredientesMas3Pociones = servidor.obtenerIngredientesEnMasDeTresPociones();
        List<Object[]> cantidadIngredientesEscuelas = servidor.obtenerCantidadIngredientesEscuelas();
        List<Pociones> top3PocionesCaras = servidor.obtenerTop3PocionesCaras(filtroTamanio.getValue());
        List<Object[]> cantidadVecesIngrediente = servidor.obtenerCantidadVecesIngredientes();
        List<Object[]> top10PocionesMuchosIngredientes = servidor.TOP10PocionesMuchosIngredientes();

        // Limpiar las tablas previas
        tablaingredientesmas3pociones.getItems().clear();
        tablacantidadIngredientesEscuelas.getItems().clear();
        tablatop3PocionesCaras.getItems().clear();
        tablacantidadVecesIngrediente.getItems().clear();
        tablatop10pocionesMuchosIngredientes.getItems().clear();

        // Agregar los nuevos datos a las tablas
        tablaingredientesmas3pociones.getItems().addAll(ingredientesMas3Pociones);
        tablacantidadIngredientesEscuelas.getItems().addAll(cantidadIngredientesEscuelas);
        tablatop3PocionesCaras.getItems().addAll(top3PocionesCaras);
        tablacantidadVecesIngrediente.getItems().addAll(cantidadVecesIngrediente);
        tablatop10pocionesMuchosIngredientes.getItems().addAll(top10PocionesMuchosIngredientes);
    }

    // Mostrar mensajes de error o información
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

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