package com.example.lawranmed;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RadiologyReportFormController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label patientAndScanLabel;
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
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        request.setReport(reportArea.getText());
        request.setStatus("Completed");
        saveClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
