package org.example.cliente_pocionescrud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

/**
 * Controlador de la ventana de acceso.
 * Se encarga de gestionar las acciones de los botones de la ventana de acceso.
 */
public class controladorGestionUsuario {
    private AccederServidor servidor;

    /**
     * Muestra la ventana de informacion de pociones.
     */


    /**
     * Muestra la ventana de informacion de ingredientes.
     */
    @FXML
    protected void iniciarSesion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Muestra la ventana de consultas fijas
     */
    @FXML
    protected void registrarUser(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registro.fxml"));
        Parent nuevaVista = fxmlLoader.load();
        Scene escenaActual = ((Node) event.getSource()).getScene();
        escenaActual.setRoot(nuevaVista);
    }

    /**
     * Intenta cortar la conexion al servidor y cierra la aplicacion
     */
    @FXML
    protected void salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar desconexión");
        alert.setHeaderText("¿Está seguro de que desea desconectar?");
        alert.setContentText("Si se desconecta, perderá la conexión con el servidor.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    servidor = ConexionServidor.getAccederServidor("localhost", 9069);
                    servidor.cerrarConexion();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        });
    }
}