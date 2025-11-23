package com.example.lawranmed.controller;

import com.example.lawranmed.model.Drug;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DrugFormController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField priceField;

    private Stage dialogStage;
    private Drug drug;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;

        if (drug != null) {
            nameField.setText(drug.getName());
            manufacturerField.setText(drug.getManufacturer());
            stockField.setText(String.valueOf(drug.getStockQuantity()));
            priceField.setText(String.valueOf(drug.getPrice()));
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            drug.setName(nameField.getText());
            drug.setManufacturer(manufacturerField.getText());
            drug.setStockQuantity(Integer.parseInt(stockField.getText()));
            drug.setPrice(Double.parseDouble(priceField.getText()));

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().isEmpty()) {
            errorMessage += "No valid drug name!\n";
        }
        if (manufacturerField.getText() == null || manufacturerField.getText().isEmpty()) {
            errorMessage += "No valid manufacturer!\n";
        }
        if (stockField.getText() == null || stockField.getText().isEmpty()) {
            errorMessage += "No valid stock quantity!\n";
        } else {
            try {
                Integer.parseInt(stockField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid stock quantity (must be an integer)!\n";
            }
        }
        if (priceField.getText() == null || priceField.getText().isEmpty()) {
            errorMessage += "No valid price!\n";
        } else {
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid price (must be a number)!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert("Invalid Fields", "Please correct the invalid fields:", errorMessage);
            return false;
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
