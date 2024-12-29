package org.example.cliente_pocionescrud;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class controladorModificar {
    private Pociones pocionSeleccionada;
    private List<Map.Entry<Ingredientes, Integer>> listaIngredientes = new ArrayList<>();

    @FXML
    private TableView<Map.Entry<Ingredientes, Integer>> tableViewIngredientes;
    @FXML
    private TableColumn<Map.Entry<Ingredientes, Integer>, String> ColumnaIngredientes;
    @FXML
    private TableColumn<Map.Entry<Ingredientes, Integer>, Integer> ColumnaCantidad;
    @FXML
    private TextField nombrePocion;
    @FXML
    private TextField efectoPocion;
    @FXML
    private ComboBox<Pociones.Escuela> escuelaPocion;
    @FXML
    private ComboBox<Pociones.Tamanio> tamanioPocion;
    @FXML
    private TextField precioPocion;

    private Map<Ingredientes, Integer> ingredientesMap = new HashMap<>();

    public void setPocion(Pociones pocion) throws IOException, ClassNotFoundException {
        this.pocionSeleccionada = pocion;
        cargarDetallesPocion();
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        ColumnaIngredientes.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getKey().getNombreIngrediente()));
        ColumnaIngredientes.setEditable(false);

        ColumnaCantidad.setCellValueFactory(entry -> new SimpleIntegerProperty(entry.getValue().getValue()).asObject());
        ColumnaCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ColumnaCantidad.setEditable(true);

        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        List<Ingredientes> ingredientes = servidor.obtenerIngredientes();

        for (Ingredientes ingrediente : ingredientes) {
            ingredientesMap.put(ingrediente, 0); // Asignar cantidad 0
        }

        // Inicializar ComboBox
        escuelaPocion.getItems().addAll(Pociones.Escuela.values());
        tamanioPocion.getItems().addAll(Pociones.Tamanio.values());

        // Validar que solo se ingresen números en la columna de cantidad
        ColumnaCantidad.setOnEditCommit(event -> {
            String newValue = event.getNewValue().toString();
            try {
                // Intentar convertir el nuevo valor en un número
                Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                // Si el valor no es un número, revertir el cambio
                mostrarMensaje("Error", "La cantidad debe ser un número entero", Alert.AlertType.ERROR);
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(event.getOldValue());
            }
        });
    }

    // Cargar detalles de la poción en los campos
    private void cargarDetallesPocion() throws IOException, ClassNotFoundException {
        nombrePocion.setText(pocionSeleccionada.getNombrePocion());
        efectoPocion.setText(pocionSeleccionada.getEfectoPocion());
        escuelaPocion.setValue(pocionSeleccionada.getEscuela());
        tamanioPocion.setValue(pocionSeleccionada.getTamanio());
        precioPocion.setText(String.valueOf(pocionSeleccionada.getPrecio()));

    }

    @FXML
    private boolean verificarPrecio() {
        try {
            double precio = Double.parseDouble(precioPocion.getText());
            if (precio < 0) {
                mostrarMensaje("Error", "El precio no puede ser negativo", Alert.AlertType.ERROR);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "El precio debe ser un número", Alert.AlertType.ERROR);
            return false;
        }
    }

    @FXML
    protected void confirmarModificar(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar modificacion");
        alert.setHeaderText("¿Desea confirmar la modificación de la poción?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                {
                    try {
                        if (!verificarPrecio()) {
                            return;
                        }
                        Pociones.Escuela escuela = escuelaPocion.getValue();
                        Pociones.Tamanio tamanio = tamanioPocion.getValue();

                        Pociones nuevaPocion = new Pociones(
                                nombrePocion.getText(),
                                efectoPocion.getText(),
                                Double.parseDouble(precioPocion.getText()),
                                escuela,
                                tamanio
                        );

                        Map<Ingredientes, Integer> ingredientesModificados = new HashMap<>();
                        for (Ingredientes ingrediente : ingredientesMap.keySet()) {
                            Integer cantidad = ingredientesMap.get(ingrediente);
                            if (cantidad > 0) {
                                ingredientesModificados.put(ingrediente, cantidad);
                            }
                        }

                        // Enviar al servidor
                        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
                        if (servidor != null) {
                          servidor.enviarPocionModificada(pocionSeleccionada.getIdPocion(), nuevaPocion, ingredientesModificados);

                        } else {
                            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
                        }
                        // Cambiar la vista después de la modificación
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
                        Parent nuevaVista = fxmlLoader.load();
                        Scene escenaActual = ((Node) event.getSource()).getScene();
                        escenaActual.setRoot(nuevaVista);
                    } catch (IOException e) {
                        mostrarMensaje("Error", "Ha ocurrido un error", Alert.AlertType.ERROR);
                    }
                }
            } else {
                // Nothign da nothing
            }
        });
    }

    @FXML
    protected void atras(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cancelación");
        alert.setHeaderText("¿Desea cancelar la modificación de la poción?");
        alert.setContentText("Si cancela, no se guardarán los cambios.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
                    Parent nuevaVista = fxmlLoader.load();
                    Scene escenaActual = ((Node) event.getSource()).getScene();
                    escenaActual.setRoot(nuevaVista);
                } catch (IOException e) {
                    mostrarMensaje("Error", "No se pudo cargar la vista de datos de la poción.", Alert.AlertType.ERROR);
                }
            } else {
                // Nothign da nothing
            }
        });
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}