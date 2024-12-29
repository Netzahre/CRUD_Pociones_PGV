package org.example.cliente_pocionescrud;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.Map;

/**
 * Controlador para la vista de detalles de una poción.
 */
public class controladorDatosPocion {
    private Pociones pocionSeleccionada;
    AccederServidor servidor;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelEfecto;
    @FXML
    private Label labelEscuela;
    @FXML
    private Label labelTamanio;
    @FXML
    private Label labelPrecio;
    @FXML
    private TableView<Map.Entry<Ingredientes, Integer>> tablaIngredientes;
    @FXML
    private TableColumn<Map.Entry<Ingredientes, Integer>, String> columnaNombre;
    @FXML
    private TableColumn<Map.Entry<Ingredientes, Integer>, Integer> columnaCantidad;

    /**
     * Establece la poción seleccionada y muestra sus detalles.
     * @param pocion Poción seleccionada.
     */
    public void setPocion(Pociones pocion) {
            this.pocionSeleccionada = pocion;
            try {
                inicializarColumnas();
                mostrarDetallesPocion();
            } catch (IOException | ClassNotFoundException e) {
                mostrarMensaje("Error", "No se pudieron cargar los detalles de la poción.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
    }

    /**
     * Inicializa las columnas de la tabla de ingredientes.
     */
    private void inicializarColumnas() {
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNombreIngrediente()));
        columnaCantidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));
    }

    /**
     * Muestra los detalles de la poción seleccionada.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void mostrarDetallesPocion() throws IOException, ClassNotFoundException {
        labelNombre.setText(pocionSeleccionada.getNombrePocion());
        labelEfecto.setText(pocionSeleccionada.getEfectoPocion());
        labelEscuela.setText(pocionSeleccionada.getEscuela().toString());
        labelTamanio.setText(pocionSeleccionada.getTamanio().toString());
        labelPrecio.setText(String.valueOf(pocionSeleccionada.getPrecio()));

        servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        if (servidor != null) {
            Map<Ingredientes, Integer> ingredientes = servidor.obtenerIngredientesPocion(pocionSeleccionada.getIdPocion());
            tablaIngredientes.getItems().clear();

            for (Map.Entry<Ingredientes, Integer> entry : ingredientes.entrySet()) {
                tablaIngredientes.getItems().add(entry);
            }
        } else {
            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Abre la ventana para modificar la poción seleccionada.
     * @param event Evento de clic en el botón.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    protected void abrirModificar(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modificarPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        controladorModificar controlador = fxmlLoader.getController();
        controlador.setPocion(pocionSeleccionada);
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Elimina la poción seleccionada.
     * @param event Evento de clic en el botón.
     * @throws IOException
     */
    @FXML
    protected void borrarPocion(ActionEvent event) throws IOException {
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmación de Borrado");
        alertaConfirmacion.setHeaderText("¿Estás seguro de que deseas borrar esta poción?");
        alertaConfirmacion.setContentText("Esta acción no se puede deshacer.");

        if (alertaConfirmacion.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
            if (servidor == null) {
                mostrarMensaje("Error", "No hay conexión con el servidor.", Alert.AlertType.ERROR);
                return;
            }

            servidor.eliminarPocion(pocionSeleccionada.getIdPocion());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerPociones.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);
        }
    }

    /**
     * Muestra un mensaje en una ventana emergente.
     * @param titulo Título de la ventana.
     * @param mensaje Mensaje a mostrar.
     * @param tipo Tipo de mensaje.
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Regresa a la ventana de ver poción.
     * @throws IOException
     */
    @FXML
    private void salir() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerPociones.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = tablaIngredientes.getScene();
        escenaActual.setRoot(nuevaVista);
    }
}