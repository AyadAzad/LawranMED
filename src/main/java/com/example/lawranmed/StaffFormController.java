package com.example.lawranmed;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StaffFormController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField roleField;
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker hireDatePicker;

    private Stage dialogStage;
    private Staff staff;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStaff(Staff staff, ObservableList<Department> departmentList) {
        this.staff = staff;
        departmentComboBox.setItems(departmentList);

        if (staff != null) {
            firstNameField.setText(staff.getFirstName());
            lastNameField.setText(staff.getLastName());
            roleField.setText(staff.getRole());
            departmentComboBox.setValue(staff.getDepartment());
            contactNumberField.setText(staff.getContactNumber());
            emailField.setText(staff.getEmail());
            hireDatePicker.setValue(staff.getHireDate());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            staff.setFirstName(firstNameField.getText());
            staff.setLastName(lastNameField.getText());
            staff.setRole(roleField.getText());
            staff.setDepartment(departmentComboBox.getValue());
            staff.setContactNumber(contactNumberField.getText());
            staff.setEmail(emailField.getText());
            staff.setHireDate(hireDatePicker.getValue());

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
