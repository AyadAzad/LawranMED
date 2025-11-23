package com.example.lawranmed.controller;

import com.example.lawranmed.model.Doctor;
import com.example.lawranmed.model.Drug;
import com.example.lawranmed.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PharmacyController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Drug> drugTable;
    @FXML
    private TableColumn<Drug, Integer> drugIdColumn;
    @FXML
    private TableColumn<Drug, String> nameColumn;
    @FXML
    private TableColumn<Drug, String> manufacturerColumn;
    @FXML
    private TableColumn<Drug, Integer> stockColumn;
    @FXML
    private TableColumn<Drug, Double> priceColumn;

    private ObservableList<Drug> drugList = FXCollections.observableArrayList();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
    private FilteredList<Drug> filteredData;

    @FXML
    public void initialize() {
        drugIdColumn.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadStaticData();

        filteredData = new FilteredList<>(drugList, p -> true);
        drugTable.setItems(filteredData);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(drug -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (drug.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (drug.getManufacturer().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadStaticData() {
        drugList.addAll(
            new Drug(1, "Aspirin", "Bayer", 500, 5.99),
            new Drug(2, "Ibuprofen", "Advil", 300, 8.50),
            new Drug(3, "Paracetamol", "Tylenol", 450, 6.25),
            new Drug(4, "Amoxicillin", "Generic", 150, 12.00)
        );

        patientList.addAll(
            new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", null),
            new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", null)
        );

        doctorList.addAll(
            new Doctor(1, "Alice", "Williams", "Cardiology", "111-222-3333", "alice.williams@example.com", "789 Maple St", null),
            new Doctor(2, "Robert", "Brown", "Neurology", "444-555-6666", "robert.brown@example.com", "101 Pine St", null)
        );
    }

    @FXML
    private void handleIssuePrescription() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("prescription-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Issue Prescription");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            PrescriptionFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPharmacyData(patientList, doctorList, drugList);

            dialogStage.showAndWait();
            drugTable.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddDrug() {
        Drug newDrug = new Drug(0, "", "", 0, 0.0);
        boolean saveClicked = showDrugEditDialog(newDrug);
        if (saveClicked) {
            newDrug.setDrugId(drugList.size() + 1);
            drugList.add(newDrug);
        }
    }

    @FXML
    private void handleEditDrug() {
        Drug selectedDrug = drugTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            boolean saveClicked = showDrugEditDialog(selectedDrug);
            if (saveClicked) {
                drugTable.refresh();
            }
        } else {
            showAlert("No Drug Selected", "Please select a drug in the table.");
        }
    }

    @FXML
    private void handleDeleteDrug() {
        Drug selectedDrug = drugTable.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            drugList.remove(selectedDrug);
        } else {
            showAlert("No Drug Selected", "Please select a drug in the table.");
        }
    }

    private boolean showDrugEditDialog(Drug drug) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("drug-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Drug");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            DrugFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDrug(drug);

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
