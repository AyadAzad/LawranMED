package com.example.lawranmed.controller;

import com.example.lawranmed.model.LabTest;
import com.example.lawranmed.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class LabTestFormController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private TextField testTypeField;
    @FXML
    private DatePicker testDatePicker;
    @FXML
    private ComboBox<String> statusComboBox;

    private Stage dialogStage;
    private LabTest labTest;
    private boolean saveClicked = false;

    @FXML
    private void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList("Pending", "Completed", "Cancelled"));

        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setLabTest(LabTest labTest, ObservableList<Patient> patientList) {
        this.labTest = labTest;
        patientComboBox.setItems(patientList);

        if (labTest != null) {
            patientComboBox.setValue(labTest.getPatient());
            testTypeField.setText(labTest.getTestType());
            testDatePicker.setValue(labTest.getTestDate());
            statusComboBox.setValue(labTest.getStatus());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            labTest.setPatient(patientComboBox.getValue());
            labTest.setTestType(testTypeField.getText());
            labTest.setTestDate(testDatePicker.getValue());
            labTest.setStatus(statusComboBox.getValue());

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // Add validation logic here
        return true;
    }
}
