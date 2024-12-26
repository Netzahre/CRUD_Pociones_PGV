package org.example.cliente_pocionescrud;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class controladorVerDatos {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}