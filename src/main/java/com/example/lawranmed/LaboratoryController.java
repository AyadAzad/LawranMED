package com.example.lawranmed;

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

public class LaboratoryController {

    @FXML
    private DatePicker dateFilterPicker;
    @FXML
    private ComboBox<String> statusFilterComboBox;
    @FXML
    private TableView<LabTest> labTestTable;
    @FXML
    private TableColumn<LabTest, Integer> testIdColumn;
    @FXML
    private TableColumn<LabTest, String> patientNameColumn;
    @FXML
    private TableColumn<LabTest, String> testTypeColumn;
    @FXML
    private TableColumn<LabTest, String> statusColumn;
    @FXML
    private TableColumn<LabTest, LocalDate> testDateColumn;

    private ObservableList<LabTest> labTestList = FXCollections.observableArrayList();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private FilteredList<LabTest> filteredData;

    @FXML
    public void initialize() {
        testIdColumn.setCellValueFactory(new PropertyValueFactory<>("testId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        testTypeColumn.setCellValueFactory(new PropertyValueFactory<>("testType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        testDateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));

        loadStaticData();

        filteredData = new FilteredList<>(labTestList, p -> true);
        labTestTable.setItems(filteredData);

        statusFilterComboBox.setItems(FXCollections.observableArrayList("Pending", "Completed", "Cancelled"));
    }

    private void loadStaticData() {
        Patient patient1 = new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", null);
        Patient patient2 = new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", null);
        patientList.addAll(patient1, patient2);

        labTestList.addAll(
            new LabTest(1, patient1, "Blood Test", "Completed", LocalDate.now(), "Normal"),
            new LabTest(2, patient2, "Urine Test", "Pending", LocalDate.now(), ""),
            new LabTest(3, patient1, "X-Ray", "Pending", LocalDate.now().plusDays(1), "")
        );
    }

    @FXML
    private void handleFilter() {
        filteredData.setPredicate(labTest -> {
            boolean dateMatch = dateFilterPicker.getValue() == null || dateFilterPicker.getValue().equals(labTest.getTestDate());
            boolean statusMatch = statusFilterComboBox.getValue() == null || statusFilterComboBox.getValue().equals(labTest.getStatus());
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
    private void handleAddTest() {
        LabTest newLabTest = new LabTest(0, null, "", "Pending", LocalDate.now(), "");
        boolean saveClicked = showLabTestEditDialog(newLabTest);
        if (saveClicked) {
            newLabTest.setTestId(labTestList.size() + 1);
            labTestList.add(newLabTest);
        }
    }

    @FXML
    private void handleEnterResults() {
        LabTest selectedLabTest = labTestTable.getSelectionModel().getSelectedItem();
        if (selectedLabTest != null) {
            if (selectedLabTest.getStatus().equals("Completed")) {
                showAlert("Cannot Enter Results", "Results have already been entered for this test.");
                return;
            }
            boolean saveClicked = showLabResultsDialog(selectedLabTest);
            if (saveClicked) {
                labTestTable.refresh();
            }
        } else {
            showAlert("No Test Selected", "Please select a test in the table.");
        }
    }

    @FXML
    private void handleViewDetails() {
        LabTest selectedLabTest = labTestTable.getSelectionModel().getSelectedItem();
        if (selectedLabTest != null) {
            showLabResultsDialog(selectedLabTest);
        } else {
            showAlert("No Test Selected", "Please select a test in the table.");
        }
    }

    private boolean showLabTestEditDialog(LabTest labTest) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("lab-test-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Lab Test");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            LabTestFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLabTest(labTest, patientList);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean showLabResultsDialog(LabTest labTest) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("lab-results-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Lab Results");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            LabResultsFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLabTest(labTest);

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
