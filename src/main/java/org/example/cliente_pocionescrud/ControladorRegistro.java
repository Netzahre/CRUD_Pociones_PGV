package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Controlador para la vista de creación de poción.
 */
public class ControladorRegistro {
    @FXML
    private TextField campoUser;
    @FXML
    private TextField campoPass;
    @FXML
    private CheckBox checkAdmin;

    @FXML
    private void crearUser() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        String user = campoUser.getText();
        String pass = campoPass.getText();
        Boolean admin = checkAdmin.isSelected();
        MessageDigest cifrado = MessageDigest.getInstance("MD5");
        cifrado.reset();
        cifrado.update(pass.getBytes());
        byte[] passCifrada = cifrado.digest();

        if (user.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Error", "Por favor, llene todos los campos.", Alert.AlertType.ERROR);
            return;
        }

        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        if (servidor == null) {
            mostrarMensaje("Error", "No se pudo conectar al servidor.", Alert.AlertType.ERROR);
            return;
        }
        Boolean resultado = servidor.crearUsuario(user, passCifrada, admin);
        System.out.println(resultado);
        if (resultado) {
            mostrarMensaje("Éxito", "Usuario creado con éxito.", Alert.AlertType.INFORMATION);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = campoUser.getScene();
            escenaActual.setRoot(nuevaVista);
        } else {
            mostrarMensaje("Error", "No se pudo crear el usuario.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void atras(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gestionUsuario.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Método para mostrar un mensaje en pantalla.
     *
     * @param titulo  Título del mensaje.
     * @param mensaje Contenido del mensaje.
     * @param tipo    Tipo de mensaje.
     */
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
