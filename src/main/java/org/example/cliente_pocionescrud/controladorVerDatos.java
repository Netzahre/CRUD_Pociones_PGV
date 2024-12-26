package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class controladorVerDatos {
    @FXML
    protected void crearPocion(ActionEvent event) throws IOException {
        //TODO implementar la logica de server
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crearPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    @FXML
    protected void AbrirPocion(ActionEvent event) throws IOException {
        //TODO implementar la logica de server y de abrir al hcer click en 1 elemnto.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    @FXML
    protected void volverAtras(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

}
