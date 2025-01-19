package org.example.cliente_pocionescrud;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la vista de creación de poción.
 */
public class controladorCrear {
    private AccederServidor servidor;
    @FXML
    private TextField NombrePocionTextField;
    @FXML
    private TextField EfectoPocionTextField;
    @FXML
    private ComboBox<Pociones.Escuela> escuelaComboBox;
    @FXML
    private ComboBox<Pociones.Tamanio> tamanoComboBox;
    @FXML
    private TextField precioPocion;
    @FXML
    private TableView<Ingredientes> tableViewIngredientes;
    @FXML
    private TableColumn<Ingredientes, String> columnaIngrediente;
    @FXML
    private TableColumn<Ingredientes, Integer> columnaCantidad;

    private final Map<Ingredientes, Integer> ingredientesMap = new HashMap<>();

    /**
     * Método que se ejecuta al inicializar la vista.
     */
    public void initialize() {
        iniciarColumnas();
        escuelaComboBox.getItems().setAll(Pociones.Escuela.values());
        tamanoComboBox.getItems().setAll(Pociones.Tamanio.values());
        escuelaComboBox.getSelectionModel().selectFirst();
        tamanoComboBox.getSelectionModel().selectFirst();
        try {
            rellenarTabla();
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron cargar los ingredientes desde el servidor.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para inicializar las columnas de la tabla.
     */
    private void iniciarColumnas() {
        columnaIngrediente.setCellValueFactory(new PropertyValueFactory<>("nombreIngrediente"));
        columnaCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(ingredientesMap.get(data.getValue())).asObject());
        columnaCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnaIngrediente.setEditable(false);
        columnaCantidad.setEditable(true);

        // Actualizar el mapa de ingredientes cuando se edita la cantidad. Increible.
        columnaCantidad.setOnEditCommit(event -> {
            Ingredientes ingrediente = event.getRowValue();
            String newCantidadStr = event.getNewValue().toString();

            try {
                int newCantidad = Integer.parseInt(newCantidadStr);
                if (newCantidad >= 0) {
                    ingredientesMap.put(ingrediente, newCantidad); // Actualizar la cantidad en el mapa
                } else {
                    mostrarMensaje("Error", "La cantidad debe ser un número positivo.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                // Mostrar mensaje de error si no se puede convertir a número y refrescar la tabla para que se muestre el valor anterior
                mostrarMensaje("Error", "Por favor, ingrese un número válido para la cantidad.", Alert.AlertType.ERROR);
                tableViewIngredientes.refresh();
            }
        });
    }

    /**
     * Método para rellenar la tabla con los ingredientes.
     */
    private void rellenarTabla() throws IOException, ClassNotFoundException {
        try {
            servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Ingredientes> ingredientes = servidor.obtenerIngredientes();
        if (ingredientes == null || ingredientes.isEmpty()) {
            mostrarMensaje("Error", "No se encontraron ingredientes en la base de datos.", Alert.AlertType.ERROR);
            return;
        }

        for (Ingredientes ingrediente : ingredientes) {
            ingredientesMap.put(ingrediente, 0); // Asignar cantidad 0 por defecto
        }

        tableViewIngredientes.getItems().setAll(ingredientesMap.keySet());
    }

    /**
     * Método para verificar que el precio de la poción sea un número mayor a 0.
     * @return true si el precio es válido, false en caso contrario.
     */
    private boolean verificarPrecio() {
        try {
            double precio = Double.parseDouble(precioPocion.getText());
            return precio > 0;
        } catch (Exception e) {
            mostrarMensaje("Error", "El precio debe ser un número y mayor a 0.", Alert.AlertType.ERROR);
            return false;
        }
    }

    /**
     * Método para confirmar la creación de la poción.
     * @param event Evento de clic en el botón.
     */
    @FXML
    protected void confirmarCrear(ActionEvent event) {
        try {
            String nombre = NombrePocionTextField.getText();
            String efecto = EfectoPocionTextField.getText();
            String escuela = escuelaComboBox.getSelectionModel().getSelectedItem().toString();
            String tamano = tamanoComboBox.getSelectionModel().getSelectedItem().toString();
            if (!verificarPrecio()) {
                return;
            }
            if (nombre.isEmpty() || efecto.isEmpty() || escuela.isEmpty() || tamano.isEmpty()) {
                mostrarMensaje("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            double precio = Double.parseDouble(precioPocion.getText());
            Pociones nuevaPocion = new Pociones(nombre, efecto, precio, Pociones.Escuela.valueOf(escuela), Pociones.Tamanio.valueOf(tamano));

            Map<Ingredientes, Integer> ingredientesSeleccionados = new HashMap<>();
            for (Ingredientes ingrediente : ingredientesMap.keySet()) {
                Integer cantidad = ingredientesMap.get(ingrediente);
                if (cantidad > 0) {
                    ingredientesSeleccionados.put(ingrediente, cantidad);
                }
            }
            servidor = ConexionServidor.getAccederServidor("localhost", 9069);
            servidor.crearPocion(nuevaPocion, ingredientesSeleccionados);
            mostrarMensaje("Creación Exitosa", "La poción se ha creado correctamente.", Alert.AlertType.INFORMATION);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerPociones.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);

        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo crear la poción. Verifica los datos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para cancelar la creación de la poción.
     * @param event Evento de clic en el botón.
     * @throws IOException Excepción de entrada/salida.
     */
    @FXML
    protected void atras(ActionEvent event) throws IOException {
        mostrarMensaje("Confirmar cancelar", "¿Desea cancelar la creación de la poción?", Alert.AlertType.CONFIRMATION);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerPociones.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método para mostrar un mensaje en pantalla.
     * @param titulo Título del mensaje.
     * @param mensaje Contenido del mensaje.
     * @param tipo Tipo de mensaje.
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
