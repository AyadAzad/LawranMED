package com.example.lawranmed.controller;

import com.example.lawranmed.model.Appointment;
import com.example.lawranmed.model.Doctor;
import com.example.lawranmed.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AppointmentFormController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextArea reasonArea;

    private Stage dialogStage;
    private Appointment appointment;
    private boolean saveClicked = false;
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList("Scheduled", "Completed", "Cancelled"));

        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
            }

            @Override
            public Patient fromString(String string) {
                return null; // Not needed
            }
        });

        doctorComboBox.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doctor) {
                return doctor != null ? doctor.getFirstName() + " " + doctor.getLastName() : "";
            }

            @Override
            public Doctor fromString(String string) {
                return null; // Not needed
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAppointment(Appointment appointment, ObservableList<Patient> patientList, ObservableList<Doctor> doctorList) {
        this.appointment = appointment;
        this.patientList.setAll(patientList);
        this.doctorList.setAll(doctorList);

        patientComboBox.setItems(this.patientList);
        doctorComboBox.setItems(this.doctorList);

        if (appointment != null) {
            patientComboBox.setValue(appointment.getPatient());
            doctorComboBox.setValue(appointment.getDoctor());
            datePicker.setValue(appointment.getAppointmentDate());
            timeField.setText(appointment.getAppointmentTime() != null ? appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")) : "");
            statusComboBox.setValue(appointment.getStatus());
            reasonArea.setText(appointment.getReason());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            appointment.setPatient(patientComboBox.getValue());
            appointment.setDoctor(doctorComboBox.getValue());
            appointment.setAppointmentDate(datePicker.getValue());
            appointment.setAppointmentTime(LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm")));
            appointment.setStatus(statusComboBox.getValue());
            appointment.setReason(reasonArea.getText());

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (patientComboBox.getValue() == null) {
            errorMessage += "No valid patient selected!\n";
        }
        if (doctorComboBox.getValue() == null) {
            errorMessage += "No valid doctor selected!\n";
        }
        if (datePicker.getValue() == null) {
            errorMessage += "No valid date selected!\n";
        }
        if (timeField.getText() == null || timeField.getText().isEmpty()) {
            errorMessage += "No valid time entered!\n";
        } else {
            try {
                LocalTime.parse(timeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                errorMessage += "Invalid time format. Please use HH:mm!\n";
            }
        }
        if (statusComboBox.getValue() == null) {
            errorMessage += "No valid status selected!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert("Invalid Fields", "Please correct the invalid fields:", errorMessage);
            return false;
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
