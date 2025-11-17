package com.example.lawranmed;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RadiologyRequestFormController {

    @FXML
    private Label titleLabel;
    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private DatePicker requestDatePicker;

    private Stage dialogStage;
    private RadiologyRequest request;
    private boolean saveClicked = false;

    @FXML
    private void initialize() {
        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
            }
            @Override
            public Patient fromString(String string) { return null; }
        });

        doctorComboBox.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doctor) {
                return doctor != null ? doctor.getFirstName() + " " + doctor.getLastName() : "";
            }
            @Override
            public Doctor fromString(String string) { return null; }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRequest(RadiologyRequest request, ObservableList<Patient> patientList, ObservableList<Doctor> doctorList) {
        this.request = request;
        patientComboBox.setItems(patientList);
        doctorComboBox.setItems(doctorList);

        titleLabel.setText("New " + request.getScanType() + " Request");
        if (request != null) {
            patientComboBox.setValue(request.getPatient());
            doctorComboBox.setValue(request.getRequestingDoctor());
            requestDatePicker.setValue(request.getRequestDate());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            request.setPatient(patientComboBox.getValue());
            request.setRequestingDoctor(doctorComboBox.getValue());
            request.setRequestDate(requestDatePicker.getValue());
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
