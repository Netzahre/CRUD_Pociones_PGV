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
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class controladorCrear {
    @FXML
    private TextField NombrePocionTextField;
    @FXML
    private TextField EfectoPocionTextField;
    @FXML
    private ListView escuelaListView;
    @FXML
    private ListView tamanoListView;
    @FXML
    private TextField precioPocion;
    @FXML
    private TableView<Ingredientes> tableViewIngredientes;
    @FXML
    private TableColumn<Ingredientes, String> columnaIngrediente;
    @FXML
    private TableColumn<Ingredientes, Integer> columnaCantidad;

    private Map<Ingredientes, Integer> ingredientesMap = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            // Configurar columnas de la tabla
            columnaIngrediente.setCellValueFactory(new PropertyValueFactory<>("nombreIngrediente"));
            columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

            // Poblar la tabla con ingredientes desde el servidor
            AccederServidor servidor = controladorConectar.getAccederServidor();
            List<Ingredientes> ingredientes = servidor.obtenerIngredientes();

            // Llenar la tabla con ingredientes inicializados con cantidad 0
            for (Ingredientes ingrediente : ingredientes) {
                ingredientesMap.put(ingrediente, 0); // Asignar cantidad 0
            }

            // Cargar la lista en la tabla
            tableViewIngredientes.getItems().setAll(ingredientesMap.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudieron cargar los ingredientes.", Alert.AlertType.ERROR);
        }
    }

    private boolean verificarPrecio() {
        try {
            double precio = Double.parseDouble(precioPocion.getText());
            return precio > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    protected void confirmarCrear(ActionEvent event) {
        try {
            String nombre = NombrePocionTextField.getText();
            String efecto = EfectoPocionTextField.getText();
            String escuela = escuelaListView.getSelectionModel().getSelectedItem().toString();
            String tamano = tamanoListView.getSelectionModel().getSelectedItem().toString();
            if (!verificarPrecio()) {
                mostrarMensaje("Error", "El precio debe ser un número y mayor a 0.", Alert.AlertType.ERROR);
                return;
            }

            double precio = Double.parseDouble(precioPocion.getText());

            // Crear una instancia de Pociones
            Pociones nuevaPocion = new Pociones(nombre, efecto, precio, Pociones.Escuela.valueOf(escuela), Pociones.Tamanio.valueOf(tamano));

            // Filtrar los ingredientes que tienen cantidad mayor a 0
            Map<Ingredientes, Integer> ingredientesSeleccionados = new HashMap<>();
            for (Ingredientes ingrediente : ingredientesMap.keySet()) {
                Integer cantidad = ingredientesMap.get(ingrediente);
                if (cantidad > 0) {
                    ingredientesSeleccionados.put(ingrediente, cantidad);
                }
            }

            // Enviar la nueva poción al servidor
            AccederServidor servidor = controladorConectar.getAccederServidor();
            servidor.crearPocion(nuevaPocion, ingredientesSeleccionados);

            // Mostrar mensaje de éxito
            mostrarMensaje("Creación Exitosa", "La poción se ha creado correctamente.", Alert.AlertType.INFORMATION);

            // Cambiar vista
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("verDatos.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudo crear la poción. Verifica los datos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void atras(ActionEvent event) throws IOException {
        // Confirmar si se quiere cancelar
        mostrarMensaje("Confirmar cancelar", "¿Desea cancelar la creación de la poción?", Alert.AlertType.CONFIRMATION);

        // Volver a la vista anterior
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("verDatos.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
