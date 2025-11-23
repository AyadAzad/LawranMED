package com.example.lawranmed;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class RadiologyReportFormController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label patientAndScanLabel;
    @FXML
    private Label detailedTestsLabel; // New FXML field
    @FXML
    private TextArea reportArea;

    private Stage dialogStage;
    private RadiologyRequest request;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRequest(RadiologyRequest request) {
        this.request = request;

        if (request != null) {
            titleLabel.setText(request.getScanType() + " Report");
            patientAndScanLabel.setText("Patient: " + request.getPatientName() + " - Scan: " + request.getScanType());
            reportArea.setText(request.getReport());

            // Display detailed tests if available
            if (request.getDetailedTests() != null && !request.getDetailedTests().isEmpty()) {
                detailedTestsLabel.setText("Tests: " + request.getDetailedTests().stream().collect(Collectors.joining(", ")));
            } else {
                detailedTestsLabel.setText("Tests: N/A");
            }
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        request.setReport(reportArea.getText());
        request.setStatus("Completed"); // Mark as completed when report is saved
        saveClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
