package com.example.lawranmed;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoctorFormController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField specializationField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker hireDatePicker;

    private Stage dialogStage;
    private Doctor doctor;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;

        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        specializationField.setText(doctor.getSpecialization());
        contactNumberField.setText(doctor.getContactNumber());
        emailField.setText(doctor.getEmail());
        addressField.setText(doctor.getAddress());
        hireDatePicker.setValue(doctor.getHireDate());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            doctor.setFirstName(firstNameField.getText());
            doctor.setLastName(lastNameField.getText());
            doctor.setSpecialization(specializationField.getText());
            doctor.setContactNumber(contactNumberField.getText());
            doctor.setEmail(emailField.getText());
            doctor.setAddress(addressField.getText());
            doctor.setHireDate(hireDatePicker.getValue());

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
