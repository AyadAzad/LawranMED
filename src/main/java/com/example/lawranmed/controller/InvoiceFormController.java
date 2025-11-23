package com.example.lawranmed.controller;

import com.example.lawranmed.model.Appointment;
import com.example.lawranmed.model.Billing;
import com.example.lawranmed.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class InvoiceFormController {

    @FXML
    private TextField invoiceIdField;
    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Appointment> appointmentComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private DatePicker billingDatePicker;

    private Stage dialogStage;
    private Billing billing;
    private boolean saveClicked = false;

    @FXML
    private void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList("Paid", "Unpaid", "Partially Paid"));

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

        appointmentComboBox.setConverter(new StringConverter<Appointment>() {
            @Override
            public String toString(Appointment appointment) {
                return appointment != null ? "ID: " + appointment.getAppointmentId() + " - " + appointment.getReason() : "";
            }

            @Override
            public Appointment fromString(String string) {
                return null;
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setBilling(Billing billing, ObservableList<Patient> patientList, ObservableList<Appointment> appointmentList) {
        this.billing = billing;

        patientComboBox.setItems(patientList);
        appointmentComboBox.setItems(appointmentList);

        if (billing != null) {
            invoiceIdField.setText(String.valueOf(billing.getInvoiceId()));
            patientComboBox.setValue(billing.getPatient());
            appointmentComboBox.setValue(billing.getAppointment());
            amountField.setText(String.valueOf(billing.getAmount()));
            statusComboBox.setValue(billing.getStatus());
            billingDatePicker.setValue(billing.getBillingDate());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            billing.setPatient(patientComboBox.getValue());
            billing.setAppointment(appointmentComboBox.getValue());
            billing.setAmount(Double.parseDouble(amountField.getText()));
            billing.setStatus(statusComboBox.getValue());
            billing.setBillingDate(billingDatePicker.getValue());

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
