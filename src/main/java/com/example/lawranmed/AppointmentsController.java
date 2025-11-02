package com.example.lawranmed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class AppointmentsController {

    @FXML
    private DatePicker dateFilterPicker;
    @FXML
    private ComboBox<String> doctorFilterComboBox;
    @FXML
    private ComboBox<String> statusFilterComboBox;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, String> patientNameColumn;
    @FXML
    private TableColumn<Appointment, String> doctorNameColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> dateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> timeColumn;
    @FXML
    private TableColumn<Appointment, String> statusColumn;
    @FXML
    private TableColumn<Appointment, String> reasonColumn;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
    private FilteredList<Appointment> filteredData;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        loadStaticData();

        filteredData = new FilteredList<>(appointmentList, p -> true);
        appointmentsTable.setItems(filteredData);

        doctorFilterComboBox.setItems(doctorList.stream().map(d -> d.getFirstName() + " " + d.getLastName()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        statusFilterComboBox.setItems(FXCollections.observableArrayList("Scheduled", "Completed", "Cancelled"));
    }

    private void loadStaticData() {
        patientList.addAll(
            new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", null),
            new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", null)
        );

        doctorList.addAll(
            new Doctor(1, "Alice", "Williams", "Cardiology", "111-222-3333", "alice.williams@example.com", "789 Maple St", null),
            new Doctor(2, "Robert", "Brown", "Neurology", "444-555-6666", "robert.brown@example.com", "101 Pine St", null)
        );

        appointmentList.addAll(
            new Appointment(1, patientList.get(0), doctorList.get(0), LocalDate.now(), LocalTime.of(10, 0), "Scheduled", "Regular Check-up"),
            new Appointment(2, patientList.get(1), doctorList.get(1), LocalDate.now(), LocalTime.of(11, 30), "Scheduled", "Headache"),
            new Appointment(3, patientList.get(0), doctorList.get(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0), "Completed", "Follow-up"),
            new Appointment(4, patientList.get(1), doctorList.get(0), LocalDate.now().plusDays(1), LocalTime.of(14, 0), "Cancelled", "Patient request")
        );
    }

    @FXML
    private void handleFilter() {
        filteredData.setPredicate(appointment -> {
            boolean dateMatch = dateFilterPicker.getValue() == null || dateFilterPicker.getValue().equals(appointment.getAppointmentDate());
            boolean doctorMatch = doctorFilterComboBox.getValue() == null || doctorFilterComboBox.getValue().equals(appointment.getDoctorName());
            boolean statusMatch = statusFilterComboBox.getValue() == null || statusFilterComboBox.getValue().equals(appointment.getStatus());
            return dateMatch && doctorMatch && statusMatch;
        });
    }

    @FXML
    private void handleClearFilters() {
        dateFilterPicker.setValue(null);
        doctorFilterComboBox.setValue(null);
        statusFilterComboBox.setValue(null);
        filteredData.setPredicate(p -> true);
    }

    @FXML
    private void handleAddAppointment() {
        Appointment newAppointment = new Appointment(0, null, null, null, null, "Scheduled", "");
        boolean saveClicked = showAppointmentEditDialog(newAppointment);
        if (saveClicked) {
            newAppointment.setAppointmentId(appointmentList.size() + 1);
            appointmentList.add(newAppointment);
        }
    }

    @FXML
    private void handleEditAppointment() {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            boolean saveClicked = showAppointmentEditDialog(selectedAppointment);
            if (saveClicked) {
                appointmentsTable.refresh();
            }
        } else {
            showAlert("No Appointment Selected", "Please select an appointment in the table.");
        }
    }

    @FXML
    private void handleCancelAppointment() {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            selectedAppointment.setStatus("Cancelled");
            appointmentsTable.refresh();
        } else {
            showAlert("No Appointment Selected", "Please select an appointment in the table.");
        }
    }

    private boolean showAppointmentEditDialog(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("appointment-form.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Appointment");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(scene);

            AppointmentFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAppointment(appointment, patientList, doctorList);

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
