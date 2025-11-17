package com.example.lawranmed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DepartmentsController {

    @FXML
    private TableView<Department> departmentTable;
    @FXML
    private TableColumn<Department, Integer> departmentIdColumn;
    @FXML
    private TableColumn<Department, String> nameColumn;
    @FXML
    private TableColumn<Department, String> descriptionColumn;

    private ObservableList<Department> departmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadStaticData();
        departmentTable.setItems(departmentList);
    }

    private void loadStaticData() {
        departmentList.addAll(
            new Department(1, "Cardiology", "Handles heart-related issues."),
            new Department(2, "Neurology", "Handles brain and nerve-related issues."),
            new Department(3, "Pediatrics", "Handles medical care for children.")
        );
    }

    @FXML
    private void handleAddDepartment() {
        Department newDepartment = new Department(0, "", "");
        boolean saveClicked = showDepartmentEditDialog(newDepartment);
        if (saveClicked) {
            newDepartment.setDepartmentId(departmentList.size() + 1);
            departmentList.add(newDepartment);
        }
    }

    @FXML
    private void handleEditDepartment() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            boolean saveClicked = showDepartmentEditDialog(selectedDepartment);
            if (saveClicked) {
                departmentTable.refresh();
            }
        } else {
            showAlert("No Department Selected", "Please select a department in the table.");
        }
    }

    @FXML
    private void handleDeleteDepartment() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            departmentList.remove(selectedDepartment);
        } else {
            showAlert("No Department Selected", "Please select a department in the table.");
        }
    }

    private boolean showDepartmentEditDialog(Department department) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("department-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Department");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            DepartmentFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDepartment(department);

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
