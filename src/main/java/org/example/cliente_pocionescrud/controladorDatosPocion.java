package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class controladorDatosPocion {
    @FXML
    protected void abrirModificar(ActionEvent event) throws IOException {
        //TODO implementar la logica de server de pociones
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modificarPocion.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    @FXML
    protected void borrarPocion(ActionEvent event) throws IOException {
        //TODO implementar la logica de server y borrado de pociones.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("verDatos.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        // Cambiar el contenido de la escena actual
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }
}