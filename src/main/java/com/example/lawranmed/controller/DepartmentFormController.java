package com.example.lawranmed.controller;

import com.example.lawranmed.model.Department;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepartmentFormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;

    private Stage dialogStage;
    private Department department;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDepartment(Department department) {
        this.department = department;

        if (department != null) {
            nameField.setText(department.getName());
            descriptionArea.setText(department.getDescription());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            department.setName(nameField.getText());
            department.setDescription(descriptionArea.getText());

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
