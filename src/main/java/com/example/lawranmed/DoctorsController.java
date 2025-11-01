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

public class DoctorsController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Doctor> doctorsTable;
    @FXML
    private TableColumn<Doctor, Integer> idColumn;
    @FXML
    private TableColumn<Doctor, String> firstNameColumn;
    @FXML
    private TableColumn<Doctor, String> lastNameColumn;
    @FXML
    private TableColumn<Doctor, String> specializationColumn;
    @FXML
    private TableColumn<Doctor, String> contactColumn;
    @FXML
    private TableColumn<Doctor, String> emailColumn;

    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadStaticData();

        FilteredList<Doctor> filteredData = new FilteredList<>(doctorList, p -> true);
        doctorsTable.setItems(filteredData);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(doctor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (doctor.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (doctor.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (doctor.getSpecialization().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadStaticData() {
        doctorList.addAll(
            new Doctor(1, "Alice", "Williams", "Cardiology", "111-222-3333", "alice.williams@example.com", "789 Maple St", LocalDate.of(2020, 1, 15)),
            new Doctor(2, "Robert", "Brown", "Neurology", "444-555-6666", "robert.brown@example.com", "101 Pine St", LocalDate.of(2018, 6, 1)),
            new Doctor(3, "Emily", "Davis", "Pediatrics", "777-888-9999", "emily.davis@example.com", "212 Birch St", LocalDate.of(2021, 9, 20))
        );
    }

    @FXML
    private void handleAddDoctor() {
        Doctor newDoctor = new Doctor(0, "", "", "", "", "", "", null);
        boolean saveClicked = showDoctorEditDialog(newDoctor);
        if (saveClicked) {
            newDoctor.setDoctorId(doctorList.size() + 1);
            doctorList.add(newDoctor);
        }
    }

    @FXML
    private void handleEditDoctor() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            boolean saveClicked = showDoctorEditDialog(selectedDoctor);
            if (saveClicked) {
                doctorsTable.refresh();
            }
        } else {
            showAlert("No Doctor Selected", "Please select a doctor in the table.");
        }
    }

    @FXML
    private void handleDeleteDoctor() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            doctorList.remove(selectedDoctor);
        } else {
            showAlert("No Doctor Selected", "Please select a doctor in the table.");
        }
    }

    private boolean showDoctorEditDialog(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("doctor-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Doctor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            DoctorFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDoctor(doctor);

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
