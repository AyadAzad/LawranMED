package com.example.lawranmed.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdministrationController {

    @FXML
    private TabPane adminTabPane;

    @FXML
    public void initialize() {
        loadTabContent("Staff Management", "staff-view.fxml");
        loadTabContent("Departments", "departments-view.fxml");
    }

    private void loadTabContent(String tabText, String fxmlFile) {
        adminTabPane.getTabs().stream()
            .filter(tab -> tab.getText().equals(tabText))
            .findFirst()
            .ifPresent(tab -> {
                try {
                    // Corrected resource path to be absolute from classpath root
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lawranmed/" + fxmlFile));
                    AnchorPane content = loader.load();
                    tab.setContent(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
