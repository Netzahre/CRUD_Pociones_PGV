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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la vista de modificación de una poción.
 */
public class controladorModificar {
    private Pociones pocionSeleccionada;
    AccederServidor servidor;

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
    private ComboBox escuelaPocion;
    @FXML
    private ComboBox tamanioPocion;
    @FXML
    private TextField precioPocion;

    private Map<Ingredientes, Integer> ingredientesMap = new HashMap<>();

    /**
     * Establecer la poción a modificar.
     *
     * @param pocion Poción a modificar.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void setPocion(Pociones pocion) throws IOException, ClassNotFoundException {
        this.pocionSeleccionada = pocion;
        iniciarElementos();
        rellenarTabla();
        cargarDetallesPocion();
    }

    /**
     * Rellenar la tabla de ingredientes con los ingredientes de la base de datos.
     * Me niego a calcular la cantidad de ingredientes de la receta original. Es un cacao. Ponlos desde cero.
     * @throws IOException
     * @throws ClassNotFoundException
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

        ingredientesMap.clear();
        for (Ingredientes ingrediente : ingredientes) {
            ingredientesMap.put(ingrediente, 0);
        }

        List<Map.Entry<Ingredientes, Integer>> listaIngredientes = new ArrayList<>(ingredientesMap.entrySet());
        tableViewIngredientes.getItems().setAll(listaIngredientes);
    }

    /**
     * Inicializar los elementos de la vista.
     */
    @FXML
    private void iniciarElementos() {
        escuelaPocion.getItems().setAll(Pociones.Escuela.values());
        tamanioPocion.getItems().setAll(Pociones.Tamanio.values());
        ColumnaIngredientes.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getKey().getNombreIngrediente()));
        ColumnaIngredientes.setEditable(false);

        ColumnaCantidad.setCellValueFactory(entry -> new SimpleIntegerProperty(entry.getValue().getValue()).asObject());
        ColumnaCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ColumnaCantidad.setEditable(true);

        ColumnaCantidad.setOnEditCommit(event -> {
            Map.Entry<Ingredientes, Integer> entry = event.getRowValue();
            Integer newCantidad = event.getNewValue();

            ingredientesMap.put(entry.getKey(), newCantidad);
        });
    }

    /**
     * Cargar los detalles de la poción en los campos de texto.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void cargarDetallesPocion() throws IOException, ClassNotFoundException {
        nombrePocion.setText(pocionSeleccionada.getNombrePocion());
        efectoPocion.setText(pocionSeleccionada.getEfectoPocion());
        escuelaPocion.setValue(pocionSeleccionada.getEscuela());
        tamanioPocion.setValue(pocionSeleccionada.getTamanio());
        precioPocion.setText(String.valueOf(pocionSeleccionada.getPrecio()));
    }

    /**
     * Verificar que el precio de la poción sea un número positivo.
     * @return true si el precio es válido, false en caso contrario.
     */
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

    /**
     * Confirmar la modificación de la poción.
     * @param event Evento de clic en el botón.
     * @throws IOException
     */
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
                        String escuela = escuelaPocion.getSelectionModel().getSelectedItem().toString();
                        String tamano = tamanioPocion.getSelectionModel().getSelectedItem().toString();


                        Pociones nuevaPocion = new Pociones(
                                nombrePocion.getText(),
                                efectoPocion.getText(),
                                Double.parseDouble(precioPocion.getText()),
                                Pociones.Escuela.valueOf(escuela),
                                Pociones.Tamanio.valueOf(tamano)
                        );

                        Map<Ingredientes, Integer> ingredientesModificados = new HashMap<>();
                        for (Ingredientes ingrediente : ingredientesMap.keySet()) {
                            Integer cantidad = ingredientesMap.get(ingrediente);
                            if (cantidad > 0) {
                                ingredientesModificados.put(ingrediente, cantidad);
                            }
                        }

                        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
                        if (servidor != null) {
                          servidor.enviarPocionModificada(pocionSeleccionada.getIdPocion(), nuevaPocion, ingredientesModificados);

                        } else {
                            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
                        }
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerPociones.fxml"));
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

    /**
     * Cancelar la modificación de la poción.
     * @param event Evento de clic en el botón.
     * @throws IOException
     */
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
                    controladorDatosPocion controlador = fxmlLoader.getController();
                    controlador.setPocion(pocionSeleccionada);
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

    /**
     * Mostrar un mensaje en pantalla.
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