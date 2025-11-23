package com.example.lawranmed.controller;

import com.example.lawranmed.model.Department;
import com.example.lawranmed.model.Staff;
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

public class StaffController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TableColumn<Staff, Integer> staffIdColumn;
    @FXML
    private TableColumn<Staff, String> firstNameColumn;
    @FXML
    private TableColumn<Staff, String> lastNameColumn;
    @FXML
    private TableColumn<Staff, String> roleColumn;
    @FXML
    private TableColumn<Staff, String> departmentColumn;

    private ObservableList<Staff> staffList = FXCollections.observableArrayList();
    private ObservableList<Department> departmentList = FXCollections.observableArrayList();
    private FilteredList<Staff> filteredData;

    @FXML
    public void initialize() {
        staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        loadStaticData();

        filteredData = new FilteredList<>(staffList, p -> true);
        staffTable.setItems(filteredData);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(staff -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (staff.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (staff.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (staff.getRole().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadStaticData() {
        Department dept1 = new Department(1, "Cardiology", "Handles heart-related issues.");
        Department dept2 = new Department(2, "Neurology", "Handles brain and nerve-related issues.");
        departmentList.addAll(dept1, dept2);

        staffList.addAll(
            new Staff(1, "Mark", "Johnson", "Nurse", dept1, "123-123-1234", "mark.j@example.com", LocalDate.now()),
            new Staff(2, "Susan", "White", "Administrator", dept2, "456-456-4567", "susan.w@example.com", LocalDate.now())
        );
    }

    @FXML
    private void handleAddStaff() {
        Staff newStaff = new Staff(0, "", "", "", null, "", "", null);
        boolean saveClicked = showStaffEditDialog(newStaff);
        if (saveClicked) {
            newStaff.setStaffId(staffList.size() + 1);
            staffList.add(newStaff);
        }
    }

    @FXML
    private void handleEditStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            boolean saveClicked = showStaffEditDialog(selectedStaff);
            if (saveClicked) {
                staffTable.refresh();
            }
        } else {
            showAlert("No Staff Selected", "Please select a staff member in the table.");
        }
    }

    @FXML
    private void handleDeleteStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            staffList.remove(selectedStaff);
        } else {
            showAlert("No Staff Selected", "Please select a staff member in the table.");
        }
    }

    private boolean showStaffEditDialog(Staff staff) {
        try {
            // Corrected resource path to be absolute from classpath root
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lawranmed/staff-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Staff");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            StaffFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStaff(staff, departmentList);

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
