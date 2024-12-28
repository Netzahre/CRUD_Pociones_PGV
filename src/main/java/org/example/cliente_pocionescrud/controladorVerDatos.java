    package org.example.cliente_pocionescrud;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import org.example.objetos.Pociones;

    import java.io.IOException;
    import java.util.List;
    import java.util.Map;

    public class controladorVerDatos {
        @FXML
        private TextField nombreFiltro;
        @FXML
        private ListView<String> escuelaFiltro;
        @FXML
        private CheckBox mineralFiltro;
        @FXML
        private CheckBox vegetalFiltro;
        @FXML
        private CheckBox organicoFiltro;
        @FXML
        private CheckBox magicoFiltro;
        @FXML
        private CheckBox pequenoFiltro;
        @FXML
        private CheckBox medianoFiltro;
        @FXML
        private CheckBox grandeFiltro;
        @FXML
        private TableView<Pociones> tablaPociones;

        @FXML
        private TableColumn<Pociones, Integer> columnaId;

        @FXML
        private TableColumn<Pociones, String> columnaNombre;

        @FXML
        private TableColumn<Pociones, String> columnaEfecto;

        @FXML
        private TableColumn<Pociones, String> columnaEscuela;

        @FXML
        private TableColumn<Pociones, String> columnaTamanio;

        @FXML
        private TableColumn<Pociones, Double> columnaPrecio;

        @FXML
        public void initialize() {
            inicializarColumnas();
            escuelaFiltro.getItems().addAll(
                    "Abjuracion", "Conjuracion", "Divinacion",
                    "Encantamiento", "Evocacion", "Ilusion",
                    "Nigromancia", "Transmutacion", "Universal"
            );
            try {
                rellenarTabla();
            } catch (IOException | ClassNotFoundException e) {
                mostrarMensaje("Error", "No se pudo obtener la lista de pociones del servidor.", Alert.AlertType.ERROR);
            }
        }

        private void inicializarColumnas() {
            columnaId.setCellValueFactory(new PropertyValueFactory<>("idPocion"));
            columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePocion"));
            columnaEfecto.setCellValueFactory(new PropertyValueFactory<>("efectoPocion"));
            columnaEscuela.setCellValueFactory(new PropertyValueFactory<>("escuela"));
            columnaTamanio.setCellValueFactory(new PropertyValueFactory<>("tamanio"));
            columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

            // Agregar un listener para la selección de una fila en la tabla
            tablaPociones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        abrirPocion(newValue); // Pasamos la poción seleccionada
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        private void rellenarTabla() throws IOException, ClassNotFoundException {
            // Obtener la conexión al servidor
            AccederServidor servidor = controladorConectar.getAccederServidor();
            if (servidor == null) {
                mostrarMensaje("Error", "No hay conexión con el servidor.", Alert.AlertType.ERROR);
                return;
            }

            // Solicitar la lista de pociones al servidor
            List<Pociones> listaPociones = servidor.obtenerPociones();

            // Establecer directamente la lista de pociones en la tabla
            tablaPociones.getItems().clear(); // Limpiar cualquier dato previo
            tablaPociones.getItems().addAll(listaPociones); // Agregar las nuevas pociones
        }

        private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
            Alert alerta = new Alert(tipo);
            alerta.setTitle(titulo);
            alerta.setHeaderText(null);
            alerta.setContentText(mensaje);
            alerta.showAndWait();
        }

        @FXML
        protected void crearPocion(ActionEvent event) throws IOException {
            //TODO implementar la logica de server
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crearPocion.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);
        }

        @FXML
        protected void abrirPocion(Pociones pocionSeleccionada) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("datosPocion.fxml"));
            Parent nuevaVista = fxmlLoader.load();

            controladorDatosPocion controlador = fxmlLoader.getController();
            controlador.setPocion(pocionSeleccionada);

            Scene escenaActual = tablaPociones.getScene();
            escenaActual.setRoot(nuevaVista);
        }

        @FXML
        protected void volverAtras(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Acceso.fxml"));
            Parent nuevaVista = fxmlLoader.load();
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(nuevaVista);
        }
        @FXML
        private void filtrarPociones(ActionEvent event) throws IOException, ClassNotFoundException {
            AccederServidor servidor = controladorConectar.getAccederServidor();
            if (servidor == null) {
                mostrarMensaje("Error", "No hay conexión con el servidor.", Alert.AlertType.ERROR);
                return;
            }

            // Construir los parámetros de filtrado
            String nombre = nombreFiltro.getText().trim();
            List<String> escuelas = escuelaFiltro.getSelectionModel().getSelectedItems();
            boolean mineral = mineralFiltro.isSelected();
            boolean vegetal = vegetalFiltro.isSelected();
            boolean organico = organicoFiltro.isSelected();
            boolean magico = magicoFiltro.isSelected();
            boolean pequeno = pequenoFiltro.isSelected();
            boolean mediano = medianoFiltro.isSelected();
            boolean grande = grandeFiltro.isSelected();

            // Enviar mensaje al servidor
            servidor.enviarMensaje("FILTRAR_POCIONES");

            // Enviar parámetros como un mapa o JSON (dependiendo de cómo manejas la comunicación)
            Map<String, Object> filtros = Map.of(
                    "nombre", nombre,
                    "escuelas", escuelas,
                    "mineral", mineral,
                    "vegetal", vegetal,
                    "organico", organico,
                    "magico", magico,
                    "pequeno", pequeno,
                    "mediano", mediano,
                    "grande", grande
            );
            servidor.enviarParametrosDeFiltrado(filtros);

            // Recibir la lista filtrada
            List<Pociones> pocionesFiltradas = servidor.obtenerPocionesFiltradas();

            // Actualizar la tabla
            tablaPociones.getItems().clear();
            tablaPociones.getItems().addAll(pocionesFiltradas);
        }
    }
