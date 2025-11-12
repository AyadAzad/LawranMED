package com.example.lawranmed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PrescriptionFormController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private ComboBox<Drug> drugComboBox;
    @FXML
    private TextField quantityField;
    @FXML
    private TableView<PrescriptionItem> prescriptionTable;
    @FXML
    private TableColumn<PrescriptionItem, String> drugNameColumn;
    @FXML
    private TableColumn<PrescriptionItem, Integer> quantityColumn;
    @FXML
    private TableColumn<PrescriptionItem, Double> priceColumn;
    @FXML
    private TableColumn<PrescriptionItem, Double> totalColumn;
    @FXML
    private Label totalBillLabel;

    private Stage dialogStage;
    private ObservableList<Drug> inventory;
    private ObservableList<PrescriptionItem> prescriptionItems = FXCollections.observableArrayList();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPharmacyData(ObservableList<Patient> patientList, ObservableList<Doctor> doctorList, ObservableList<Drug> inventory) {
        this.inventory = inventory;
        patientComboBox.setItems(patientList);
        doctorComboBox.setItems(doctorList);
        drugComboBox.setItems(inventory);
    }

    @FXML
    private void initialize() {
        drugNameColumn.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        prescriptionTable.setItems(prescriptionItems);
        setupComboBoxConverters();
    }

    private void setupComboBoxConverters() {
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

        drugComboBox.setConverter(new StringConverter<Drug>() {
            @Override
            public String toString(Drug drug) {
                return drug != null ? drug.getName() : "";
            }
            @Override
            public Drug fromString(String string) { return null; }
        });
    }

    @FXML
    private void handleAddDrugToPrescription() {
        Drug selectedDrug = drugComboBox.getValue();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Quantity", "Please enter a valid number for the quantity.");
            return;
        }

        if (selectedDrug != null && quantity > 0) {
            if (quantity > selectedDrug.getStockQuantity()) {
                showAlert("Insufficient Stock", "Not enough stock for " + selectedDrug.getName() + ". Available: " + selectedDrug.getStockQuantity());
                return;
            }
            prescriptionItems.add(new PrescriptionItem(selectedDrug, quantity));
            updateTotalBill();
        }
    }

    @FXML
    private void handleDispense() {
        if (patientComboBox.getValue() == null || doctorComboBox.getValue() == null || prescriptionItems.isEmpty()) {
            showAlert("Missing Information", "Please select a patient, a doctor, and add at least one drug to the prescription.");
            return;
        }

        for (PrescriptionItem item : prescriptionItems) {
            Drug drug = item.getDrug();
            drug.setStockQuantity(drug.getStockQuantity() - item.getQuantity());
        }

        showAlert("Success", "Prescription dispensed successfully!");
        dialogStage.close();
    }



    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void updateTotalBill() {
        double totalBill = prescriptionItems.stream().mapToDouble(PrescriptionItem::getTotal).sum();
        totalBillLabel.setText(String.format("Total: $%.2f", totalBill));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
