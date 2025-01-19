package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Clase controladorVerPociones
 * Esta clase es el controlador de la vista VerPociones.fxml
 * Se encarga de mostrar las pociones disponibles en el servidor
 * y de permitir al usuario filtrarlas y ver sus detalles.
 *
 */
public class controladorVerPociones {
    AccederServidor servidor;
    @FXML
    private TextField nombreFiltro;
    @FXML
    private ComboBox<String> escuelaFiltro;
    @FXML
    private ComboBox<String> tamanioFiltro;
    @FXML
    private TableView<Pociones> tablaPociones;
    @FXML
    private TableColumn<Pociones, Integer> columnaId;

    @FXML
    private TableColumn<Pociones, String> columnaNombre;

    @FXML
    private TableColumn<Pociones, String> columnaEfecto;

    @FXML
    private TableColumn<Pociones, String> columnaEscuela;

    @FXML
    private TableColumn<Pociones, String> columnaTamanio;

    @FXML
    private TableColumn<Pociones, Double> columnaPrecio;

    @FXML
    private Button botonCrearPocion;
    /**
     * Método initialize
     * Este método se ejecuta al cargar la vista VerPociones.fxml
     * y se encarga de inicializar los elementos de la interfaz gráfica.
     *
     */
    @FXML
    public void initialize() {
        inicializarColumnas();
        escuelaFiltro.getItems().addAll(
                "CONJURACION", "EVOCACION", "ILUSION", "NIGROMANCIA", "TRANSMUTACION", "ABJURACION", "ENCANTAMIENTO", "DIVINACION", "UNIVERSAL", "TODAS"
        );
        escuelaFiltro.setValue("TODAS");
        tamanioFiltro.getItems().addAll("PEQUEÑO", "MEDIANO", "GRANDE", "TODOS");
        tamanioFiltro.setValue("TODOS");
        try {
            rellenarTabla();
        } catch (IOException | ClassNotFoundException e) {
            mostrarMensaje("No se pudo obtener la lista de pociones del servidor.");
        }
        if (ConexionServidor.isEsUsuario()) {
            botonCrearPocion.setVisible(false);
        }
    }

    /**
     * Método inicializarColumnas
     * Este método se encarga de inicializar las columnas de la tabla
     * de pociones.
     *
     */
    private void inicializarColumnas() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idPocion"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePocion"));
        columnaEfecto.setCellValueFactory(new PropertyValueFactory<>("efectoPocion"));
        columnaEscuela.setCellValueFactory(new PropertyValueFactory<>("escuela"));
        columnaTamanio.setCellValueFactory(new PropertyValueFactory<>("tamanio"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tablaPociones.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                try {
                    abrirPocion(newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Método rellenarTabla
     * Este método se encarga de obtener la lista de pociones
     * del servidor y de rellenar la tabla con ellas.
     *
     * @throws IOException Si no se puede obtener la lista de pociones
     * @throws ClassNotFoundException Si no se puede obtener la lista de pociones
     */
    private void rellenarTabla() throws IOException, ClassNotFoundException {
        servidor = ConexionServidor.getAccederServidor("localhost", 9069);

        if (servidor == null) {
            mostrarMensaje("No hay conexión con el servidor.");
            return;
        }
        List<Pociones> listaPociones = servidor.obtenerPociones();

        tablaPociones.getItems().clear(); // Limpiar cualquier dato previo
        tablaPociones.getItems().addAll(listaPociones); // Agregar las nuevas pociones
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
     * Método crearPocion
     * Este método se encarga de cambiar a la vista CrearPocion.fxml
     * para permitir al usuario crear una nueva poción.
     *
     * @param event Evento de click
     */
    @FXML
    protected void crearPocion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crearPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método abrirPocion
     * Este método se encarga de cambiar a la vista DatosPocion.fxml
     * para permitir al usuario ver los detalles de una poción.
     *
     * @param pocionSeleccionada Poción seleccionada
     */
    @FXML
    protected void abrirPocion(Pociones pocionSeleccionada) throws IOException {
        if (pocionSeleccionada == null) {
            mostrarMensaje("No se seleccionó una poción válida.");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();

        controladorDatosPocion controlador = fxmlLoader.getController();

        controlador.setPocion(pocionSeleccionada);

        Scene escenaActual = tablaPociones.getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método volverAtras
     * Este método se encarga de volver a la vista Acceso.fxml
     *
     * @param event Evento de click
     */
    @FXML
    protected void volverAtras(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método filtrarPociones
     * Este método se encarga de filtrar las pociones en la tabla
     * según los parámetros ingresados por el usuario.
     *
     * @throws IOException Si no se puede obtener la lista de pociones
     * @throws ClassNotFoundException Si no se puede obtener la lista de pociones
     */
    @FXML
    private void filtrarPociones() throws IOException, ClassNotFoundException {

        String nombre = nombreFiltro.getText().trim();
        String escuelas = escuelaFiltro.getValue();
        String tamanio = tamanioFiltro.getValue();

        if (nombre.isEmpty()) {
            nombre = "%"; // Valor por defecto
        }

        Map<String, Object> filtros = Map.of(
                "nombre", nombre,
                "escuela", escuelas,
                "tamanio", tamanio
        );
        servidor.enviarFiltrosPociones(filtros);

        List<Pociones> pocionesFiltradas = servidor.obtenerPocionesFiltradas();

        tablaPociones.getItems().clear();
        tablaPociones.getItems().addAll(pocionesFiltradas);
    }
}
