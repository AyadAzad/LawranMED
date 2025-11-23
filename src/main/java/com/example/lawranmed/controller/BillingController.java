package com.example.lawranmed.controller;

import com.example.lawranmed.model.Appointment;
import com.example.lawranmed.model.Billing;
import com.example.lawranmed.model.Doctor;
import com.example.lawranmed.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BillingController {

    @FXML
    private DatePicker dateFilterPicker;
    @FXML
    private ComboBox<String> statusFilterComboBox;
    @FXML
    private TableView<Billing> billingTable;
    @FXML
    private TableColumn<Billing, Integer> invoiceIdColumn;
    @FXML
    private TableColumn<Billing, String> patientNameColumn;
    @FXML
    private TableColumn<Billing, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Billing, Double> amountColumn;
    @FXML
    private TableColumn<Billing, String> statusColumn;
    @FXML
    private TableColumn<Billing, LocalDate> billingDateColumn;

    private ObservableList<Billing> billingList = FXCollections.observableArrayList();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private FilteredList<Billing> filteredData;

    @FXML
    public void initialize() {
        invoiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        billingDateColumn.setCellValueFactory(new PropertyValueFactory<>("billingDate"));

        loadStaticData();

        filteredData = new FilteredList<>(billingList, p -> true);
        billingTable.setItems(filteredData);

        statusFilterComboBox.setItems(FXCollections.observableArrayList("Paid", "Unpaid", "Partially Paid"));
    }

    private void loadStaticData() {
        Patient patient1 = new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", null);
        Patient patient2 = new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", null);
        patientList.addAll(patient1, patient2);

        Doctor doctor1 = new Doctor(1, "Alice", "Williams", "Cardiology", "111-222-3333", "alice.williams@example.com", "789 Maple St", null);

        Appointment appointment1 = new Appointment(1, patient1, doctor1, LocalDate.now(), LocalTime.of(10, 0), "Completed", "Regular Check-up");
        Appointment appointment2 = new Appointment(2, patient2, doctor1, LocalDate.now(), LocalTime.of(11, 30), "Completed", "Consultation");
        appointmentList.addAll(appointment1, appointment2);

        billingList.addAll(
            new Billing(101, patient1, appointment1, 150.00, "Paid", LocalDate.now()),
            new Billing(102, patient2, appointment2, 200.00, "Unpaid", LocalDate.now()),
            new Billing(103, patient1, null, 50.00, "Unpaid", LocalDate.now().minusDays(5))
        );
    }

    @FXML
    private void handleFilter() {
        filteredData.setPredicate(billing -> {
            boolean dateMatch = dateFilterPicker.getValue() == null || dateFilterPicker.getValue().equals(billing.getBillingDate());
            boolean statusMatch = statusFilterComboBox.getValue() == null || statusFilterComboBox.getValue().equals(billing.getStatus());
            return dateMatch && statusMatch;
        });
    }

    @FXML
    private void handleClearFilters() {
        dateFilterPicker.setValue(null);
        statusFilterComboBox.setValue(null);
        filteredData.setPredicate(p -> true);
    }

    @FXML
    private void handleCreateInvoice() {
        Billing newBilling = new Billing(0, null, null, 0.0, "Unpaid", LocalDate.now());
        boolean saveClicked = showInvoiceEditDialog(newBilling);
        if (saveClicked) {
            newBilling.setInvoiceId(billingList.size() + 101);
            billingList.add(newBilling);
        }
    }

    @FXML
    private void handleViewDetails() {
        Billing selectedBilling = billingTable.getSelectionModel().getSelectedItem();
        if (selectedBilling != null) {
            showInvoiceEditDialog(selectedBilling);
        } else {
            showAlert("No Invoice Selected", "Please select an invoice in the table.");
        }
    }

    @FXML
    private void handleUpdatePayment() {
        Billing selectedBilling = billingTable.getSelectionModel().getSelectedItem();
        if (selectedBilling != null) {
            selectedBilling.setStatus("Paid");
            billingTable.refresh();
        } else {
            showAlert("No Invoice Selected", "Please select an invoice in the table.");
        }
    }

    private boolean showInvoiceEditDialog(Billing billing) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("invoice-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Invoice Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            InvoiceFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBilling(billing, patientList, appointmentList);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
