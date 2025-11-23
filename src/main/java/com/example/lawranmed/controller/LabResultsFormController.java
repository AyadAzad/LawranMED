package com.example.lawranmed.controller;

import com.example.lawranmed.model.LabTest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LabResultsFormController {

    @FXML
    private Label patientAndTestLabel;
    @FXML
    private TextArea resultsArea;

    private Stage dialogStage;
    private LabTest labTest;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setLabTest(LabTest labTest) {
        this.labTest = labTest;

        if (labTest != null) {
            patientAndTestLabel.setText("Patient: " + labTest.getPatientName() + " - Test: " + labTest.getTestType());
            resultsArea.setText(labTest.getResults());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        labTest.setResults(resultsArea.getText());
        labTest.setStatus("Completed");
        saveClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
