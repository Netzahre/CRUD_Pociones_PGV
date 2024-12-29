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

public class controladorConectar {
    private static AccederServidor accederServidor;

    public static AccederServidor getAccederServidor() {
        return accederServidor;
    }

    @FXML
    protected void Conectar(ActionEvent event) throws IOException {
        try {
            // Ejecutar la tarea de conexión
            ConectarAlServidorTask tareaConexion = new ConectarAlServidorTask("localhost", 9069);
            // Establecer el comportamiento cuando la tarea se ejecute correctamente
            tareaConexion.setOnSucceeded(_ -> {
                System.out.println("setOnSucceeded ejecutado");
                if (tareaConexion.getValue()) {
                    System.out.println("Conexión exitosa, cambiando la vista.");
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("acceso.fxml"));
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


            // Establecer el comportamiento si la tarea falla
            tareaConexion.setOnFailed(e -> {
                System.out.println("setOnFailed ejecutado");
                mostrarMensaje("Error", tareaConexion.getMessage(), Alert.AlertType.ERROR);
            });

            // Ejecutar el Task en un hilo de fondo para no bloquear la interfaz
            new Thread(tareaConexion).start();
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void salir() {
        System.exit(0);
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
