package org.example.cliente_pocionescrud;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * Controlador de la vista de conexión.
 */
public class controladorConectar {
    private final int PUERTO = 9069;
    private final String HOST = "localhost";

    /**
     * Metodo que se ejecuta cuando se presiona el botón "Conectar".
     */
    @FXML
    protected void Conectar(ActionEvent event) throws IOException {
        try {
            // Crear una tarea para conectarse al servidor
            ConectarAlServidorTask tareaConexion = new ConectarAlServidorTask(HOST, PUERTO);
            tareaConexion.setOnSucceeded(_ -> {

                // Verificar si la conexión fue exitosa
                if (tareaConexion.getValue()) {
                    System.out.println("Conexión exitosa");
                    // Cambiar a la vista de acceso si la conexión fue exitosa. Esto se hace en el hilo de la interfaz gráfica.
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestionUsuario.fxml"));
                            Parent nuevaVista = fxmlLoader.load();
                            Scene escenaActual = ((Node) event.getSource()).getScene();
                            escenaActual.setRoot(nuevaVista);
                            mostrarMensaje("Conexión exitosa", "Usted se ha conectado con éxito al servidor.", Alert.AlertType.INFORMATION);
                        } catch (IOException ex) {
                            mostrarMensaje("Error", "No se pudo cargar la vista siguiente.", Alert.AlertType.ERROR);
                        }
                    });
                } else {
                    mostrarMensaje("Servidor lleno", tareaConexion.getMessage(), Alert.AlertType.WARNING);
                }
            });

            // Manejar errores en la conexión al servidor
            tareaConexion.setOnFailed(e -> {
                System.out.println("setOnFailed ejecutado");
                mostrarMensaje("Error", tareaConexion.getMessage(), Alert.AlertType.ERROR);
            });

            // Ejecutar el Task en un hilo de fondo para no bloquear la interfaz. Bendito Google.
            new Thread(tareaConexion).start();
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Metodo que se ejecuta cuando se presiona el botón "Salir". Cierra la aplicación.
     */
    @FXML
    protected void salir() {
        System.exit(0);
    }

    /**
     * Muestra un mensaje en una ventana emergente.
     * @param titulo El título de la ventana.
     * @param mensaje El mensaje a mostrar.
     * @param tipo El tipo de mensaje.
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
