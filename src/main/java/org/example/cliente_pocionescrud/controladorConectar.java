package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class controladorConectar {
    @FXML
    private AccederServidor accederServidor;


    @FXML
    protected void Conectar(ActionEvent event) {
        try {
//            accederServidor = new AccederServidor("localhost", 69);
//            if (accederServidor.esServidorLleno()) {
//                mostrarMensaje("Servidor lleno", "Actualmente el servidor está ocupado. Por favor, intente más tarde.", Alert.AlertType.WARNING);
//                accederServidor.cerrarConexion();
//                return;
//            }
            // Cambiar a la nueva pantalla si la conexión fue exitosa
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("acceso.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            // Cambiar el contenido de la escena actual
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);

            mostrarMensaje("Conexion exitosa", "Usted se ha conectado con exito al servidor", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            mostrarMensaje("Conexion fallida", "Ha ocurrido un error al intentar acceder al servidor", Alert.AlertType.ERROR);
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