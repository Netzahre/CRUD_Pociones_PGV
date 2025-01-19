package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Controlador para la vista de creación de poción.
 */
public class ControladorLogin {
    @FXML
    private TextField campoUser;
    @FXML
    private TextField campoPass;


    @FXML
    private void iniciarSesion() throws IOException, NoSuchAlgorithmException {
        String user = campoUser.getText();
        String pass = campoPass.getText();
        MessageDigest cifrado = MessageDigest.getInstance("MD5");
        cifrado.reset();
        cifrado.update(pass.getBytes());
        byte[] passCifrada = cifrado.digest();
        if (user.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Por favor, llene todos los campos.");
            return;
        }

        AccederServidor servidor = ConexionServidor.getAccederServidor("localhost", 9069);
        if (servidor == null) {
            mostrarMensaje("No se pudo conectar al servidor.");
            return;
        }
        String mensaje = servidor.iniciarSesion(user, passCifrada);

        if (mensaje.equals("USUARIO")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = campoUser.getScene();
            ConexionServidor.setEsUsuario(true);
            escenaActual.setRoot(nuevaVista);
        } else if (mensaje.equals("ADMIN")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = campoUser.getScene();
            ConexionServidor.setEsUsuario(false);
            escenaActual.setRoot(nuevaVista);
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos.");
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
     * Método para mostrar un mensaje de error en pantalla.
     * @param mensaje Contenido del mensaje.
     */
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
