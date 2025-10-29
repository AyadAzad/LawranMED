package com.example.lawranmed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HelloController {

    @FXML
    private StackPane mainContent;

    @FXML
    private void showDashboard() {
        loadPage("dashboard-view");
    }

    @FXML
    private void showPatients() {
        loadPage("patients-view");
    }

    @FXML
    private void showDoctors() {
        loadPage("doctors-view");
    }

    @FXML
    private void showAppointments() {
        loadPage("appointments-view");
    }

    @FXML
    private void showBilling() {
        loadPage("billing-view");
    }

    @FXML
    private void showPharmacy() {
        loadPage("pharmacy-view");
    }

    @FXML
    private void showLaboratory() {
        loadPage("laboratory-view");
    }

    @FXML
    private void showRadiology() {
        loadPage("radiology-view");
    }

    @FXML
    private void showAdministration() {
        loadPage("administration-view");
    }

    private void loadPage(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName + ".fxml"));
            Parent page = loader.load();
            mainContent.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
            mainContent.getChildren().setAll(new Label("Error loading page: " + fxmlName));
        }
    }
}
