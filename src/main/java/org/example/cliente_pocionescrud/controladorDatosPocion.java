package org.example.cliente_pocionescrud;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controladorDatosPocion {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}