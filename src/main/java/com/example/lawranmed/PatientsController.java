package com.example.lawranmed;

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
import java.time.LocalDateTime;

public class PatientsController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, Integer> idColumn;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> genderColumn;
    @FXML
    private TableColumn<Patient, LocalDate> dobColumn;
    @FXML
    private TableColumn<Patient, String> contactColumn;
    @FXML
    private TableColumn<Patient, String> emailColumn;
    @FXML
    private TableColumn<Patient, String> bloodGroupColumn;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        bloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));

        loadStaticData();

        FilteredList<Patient> filteredData = new FilteredList<>(patientList, p -> true);
        patientsTable.setItems(filteredData);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (patient.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getContactNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadStaticData() {
        patientList.addAll(
            new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", LocalDateTime.now()),
            new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", LocalDateTime.now()),
            new Patient(3, "Peter", "Jones", "Male", LocalDate.of(1978, 12, 1), "555-555-5555", "peter.jones@example.com", "789 Pine Ln", "O+", "Mary Jones", LocalDateTime.now())
        );
    }

    @FXML
    private void handleAddPatient() {
        Patient newPatient = new Patient(0, "", "", "", null, "", "", "", "", "", null);
        boolean saveClicked = showPatientEditDialog(newPatient);
        if (saveClicked) {
            // In a real application, you would get the new ID from the database
            newPatient.setPatientId(patientList.size() + 1);
            patientList.add(newPatient);
        }
    }

    @FXML
    private void handleEditPatient() {
        Patient selectedPatient = patientsTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            boolean saveClicked = showPatientEditDialog(selectedPatient);
            if (saveClicked) {
                patientsTable.refresh();
            }
        } else {
            showAlert("No Patient Selected", "Please select a patient in the table.");
        }
    }

    @FXML
    private void handleDeletePatient() {
        int selectedIndex = patientsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            patientsTable.getItems().remove(selectedIndex);
        } else {
            showAlert("No Patient Selected", "Please select a patient in the table.");
        }
    }

    private boolean showPatientEditDialog(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("patient-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Patient");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            PatientFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);

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
