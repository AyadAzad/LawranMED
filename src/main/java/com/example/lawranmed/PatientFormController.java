package com.example.lawranmed;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientFormController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField bloodGroupField;
    @FXML
    private TextField emergencyContactField;

    private Stage dialogStage;
    private Patient patient;
    private boolean saveClicked = false;

    @FXML
    private void initialize() {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        genderComboBox.setValue(patient.getGender());
        dobPicker.setValue(patient.getDob());
        contactNumberField.setText(patient.getContactNumber());
        emailField.setText(patient.getEmail());
        addressField.setText(patient.getAddress());
        bloodGroupField.setText(patient.getBloodGroup());
        emergencyContactField.setText(patient.getEmergencyContact());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            patient.setFirstName(firstNameField.getText());
            patient.setLastName(lastNameField.getText());
            patient.setGender(genderComboBox.getValue());
            patient.setDob(dobPicker.getValue());
            patient.setContactNumber(contactNumberField.getText());
            patient.setEmail(emailField.getText());
            patient.setAddress(addressField.getText());
            patient.setBloodGroup(bloodGroupField.getText());
            patient.setEmergencyContact(emergencyContactField.getText());
            if (patient.getRegistrationDate() == null) {
                patient.setRegistrationDate(LocalDateTime.now());
            }

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
