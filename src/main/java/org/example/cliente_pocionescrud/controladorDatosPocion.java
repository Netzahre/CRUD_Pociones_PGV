package org.example.cliente_pocionescrud;

import eu.hansolo.tilesfx.addons.Switch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class controladorDatosPocion {
    private Pociones pocionSeleccionada;

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

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        inicializarColumnas();
        mostrarDetallesPocion();
    }

    public void setPocion(Pociones pocion) {
        this.pocionSeleccionada = pocion;
    }

    private void inicializarColumnas() {
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNombreIngrediente()));
        columnaCantidad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));
    }


    public void mostrarDetallesPocion() throws IOException, ClassNotFoundException {
        labelNombre.setText(pocionSeleccionada.getNombrePocion());
        labelEfecto.setText(pocionSeleccionada.getEfectoPocion());

        switch (pocionSeleccionada.getEscuela()) {
            case Pociones.Escuela.ABJURACION -> labelEscuela.setText("Abjuracion");
            case Pociones.Escuela.CONJURACION -> labelEscuela.setText("Conjuracion");
            case Pociones.Escuela.DIVINACION -> labelEscuela.setText("Divinacion");
            case Pociones.Escuela.ENCANTAMIENTO -> labelEscuela.setText("Encantamiento");
            case Pociones.Escuela.EVOCACION -> labelEscuela.setText("Evocacion");
            case Pociones.Escuela.ILUSION -> labelEscuela.setText("Ilusion");
            case Pociones.Escuela.NIGROMANCIA -> labelEscuela.setText("Nigromancia");
            case Pociones.Escuela.TRANSMUTACION -> labelEscuela.setText("Transmutacion");
            case Pociones.Escuela.UNIVERSAL -> labelEscuela.setText("Universal");
        }

        switch (pocionSeleccionada.getTamanio()) {
            case Pociones.Tamanio.GRANDE -> labelTamanio.setText("Grande");
            case Pociones.Tamanio.MEDIANO -> labelTamanio.setText("Mediano");
            case Pociones.Tamanio.PEQUEÑO -> labelTamanio.setText("Pequeño");
        }

        labelPrecio.setText(String.valueOf(pocionSeleccionada.getPrecio()));


        // Solicitar los ingredientes del servidor
        AccederServidor servidor = controladorConectar.getAccederServidor();
        if (servidor != null) {
            Map<Ingredientes, Integer> ingredientes = servidor.obtenerIngredientesPocion(pocionSeleccionada.getIdPocion());

            // Limpiar la tabla antes de agregar los nuevos datos
            tablaIngredientes.getItems().clear();

            // Iterar sobre el mapa de ingredientes y cantidades y agregarlos a la tabla
            for (Map.Entry<Ingredientes, Integer> entry : ingredientes.entrySet()) {
                tablaIngredientes.getItems().add(entry);
            }
        } else {
            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    protected void abrirModificar(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modificarPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        controladorModificar controlador = fxmlLoader.getController();
        controlador.setPocion(pocionSeleccionada);
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    @FXML
    protected void borrarPocion(ActionEvent event) throws IOException {
        // Pedir confirmación antes de eliminar
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmación de Borrado");
        alertaConfirmacion.setHeaderText("¿Estás seguro de que deseas borrar esta poción?");
        alertaConfirmacion.setContentText("Esta acción no se puede deshacer.");

        // Si el usuario confirma
        if (alertaConfirmacion.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            AccederServidor servidor = controladorConectar.getAccederServidor();
            if (servidor == null) {
                mostrarMensaje("Error", "No hay conexión con el servidor.", Alert.AlertType.ERROR);
                return;
            }

            servidor.eliminarPocion(pocionSeleccionada.getIdPocion());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("verDatos.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}