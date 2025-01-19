package org.example.cliente_pocionescrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase InicioAPP
 * Esta clase es la encargada de iniciar la aplicación.
 * Se encarga de cargar la vista Conectar.fxml al iniciar la aplicación.
 *
 */
public class InicioAPP extends Application {
    private static final String KEYSTORE = "src/main/resources/truststore.jks";
    private static final String PASSWORD = "admin";
    /**
     * Método start
     * Este método se encarga de cargar la vista Conectar.fxml
     * al iniciar la aplicación.
     *
     * @param stage Escenario principal de la aplicación.
     * @throws IOException Excepción lanzada si no se puede cargar la vista.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InicioAPP.class.getResource("Conectar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PGV - Pociones grandes y variadas");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método main
     * Este método es el punto de entrada de la aplicación.
     * Se encarga de iniciar la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", KEYSTORE);
        System.setProperty("javax.net.ssl.trustStorePassword", PASSWORD);
        launch();
    }
}