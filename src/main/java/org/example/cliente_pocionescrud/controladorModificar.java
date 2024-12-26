package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

public class controladorModificar {
    @FXML
    protected void confirmarModificar(ActionEvent event) throws IOException {
        //TODO implementar la logica de server y creacion de pociones
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        mostrarMensaje("Confirmar modificacion", "¿Desea confiramr la modificacion de la pocion?", Alert.AlertType.CONFIRMATION);

        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    @FXML
    protected void atras(ActionEvent event) throws IOException {
        //TODO implementar la logica de server
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        mostrarMensaje("Confirmar cancelar", "¿Desea cancelar la creacion de la pocion?", Alert.AlertType.CONFIRMATION);
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