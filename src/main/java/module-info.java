module com.example.lawranmed {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires atlantafx.base;

    opens com.example.lawranmed to javafx.fxml;
    exports com.example.lawranmed;
    exports com.example.lawranmed.model;
    opens com.example.lawranmed.model to javafx.fxml;
    exports com.example.lawranmed.controller;
    opens com.example.lawranmed.controller to javafx.fxml;
}