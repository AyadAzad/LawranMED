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

public class RadiologyTabController {

    @FXML
    private DatePicker dateFilterPicker;
    @FXML
    private ComboBox<String> statusFilterComboBox;
    @FXML
    private TableView<RadiologyRequest> radiologyTable;
    @FXML
    private TableColumn<RadiologyRequest, Integer> requestIdColumn;
    @FXML
    private TableColumn<RadiologyRequest, String> patientNameColumn;
    @FXML
    private TableColumn<RadiologyRequest, String> doctorNameColumn;
    @FXML
    private TableColumn<RadiologyRequest, String> statusColumn;
    @FXML
    private TableColumn<RadiologyRequest, LocalDate> requestDateColumn;

    private String scanType;
    private ObservableList<RadiologyRequest> allRequests;
    private ObservableList<Patient> patientList;
    private ObservableList<Doctor> doctorList;
    private FilteredList<RadiologyRequest> filteredData;

    public void setScanType(String scanType, ObservableList<RadiologyRequest> allRequests, ObservableList<Patient> patientList, ObservableList<Doctor> doctorList) {
        this.scanType = scanType;
        this.allRequests = allRequests;
        this.patientList = patientList;
        this.doctorList = doctorList;

        initializeTable();
    }

    private void initializeTable() {
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestDateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        filteredData = new FilteredList<>(allRequests.filtered(r -> r.getScanType().equals(scanType)), p -> true);
        radiologyTable.setItems(filteredData);

        statusFilterComboBox.setItems(FXCollections.observableArrayList("Pending", "Completed", "Cancelled"));
    }

    @FXML
    private void handleFilter() {
        filteredData.setPredicate(request -> {
            boolean dateMatch = dateFilterPicker.getValue() == null || dateFilterPicker.getValue().equals(request.getRequestDate());
            boolean statusMatch = statusFilterComboBox.getValue() == null || statusFilterComboBox.getValue().equals(request.getStatus());
            return dateMatch && statusMatch && request.getScanType().equals(scanType);
        });
    }

    @FXML
    private void handleClearFilters() {
        dateFilterPicker.setValue(null);
        statusFilterComboBox.setValue(null);
        filteredData.setPredicate(r -> r.getScanType().equals(scanType));
    }

    @FXML
    private void handleNewRequest() {
        RadiologyRequest newRequest = new RadiologyRequest(0, null, null, scanType, "Pending", LocalDate.now(), "");
        boolean saveClicked = showRequestEditDialog(newRequest);
        if (saveClicked) {
            newRequest.setRequestId(allRequests.size() + 1);
            allRequests.add(newRequest);
        }
    }

    @FXML
    private void handleEnterReport() {
        RadiologyRequest selectedRequest = radiologyTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            if (selectedRequest.getStatus().equals("Completed")) {
                showAlert("Cannot Enter Report", "A report has already been entered for this request.");
                return;
            }
            boolean saveClicked = showReportDialog(selectedRequest);
            if (saveClicked) {
                radiologyTable.refresh();
            }
        } else {
            showAlert("No Request Selected", "Please select a request in the table.");
        }
    }

    @FXML
    private void handleViewReport() {
        RadiologyRequest selectedRequest = radiologyTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            showReportDialog(selectedRequest);
        } else {
            showAlert("No Request Selected", "Please select a request in the table.");
        }
    }

    private boolean showRequestEditDialog(RadiologyRequest request) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("radiology-request-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New " + scanType + " Request");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            RadiologyRequestFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRequest(request, patientList, doctorList);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean showReportDialog(RadiologyRequest request) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("radiology-report-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle(scanType + " Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            RadiologyReportFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRequest(request);

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
