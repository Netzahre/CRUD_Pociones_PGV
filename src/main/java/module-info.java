module org.example.cliente_pocionescrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires modelo.comun;

    opens org.example.cliente_pocionescrud to javafx.fxml;
    exports org.example.cliente_pocionescrud;
}