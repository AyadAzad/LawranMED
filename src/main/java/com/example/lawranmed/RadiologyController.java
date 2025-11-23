package com.example.lawranmed;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class RadiologyController {

    @FXML
    private TabPane radiologyTabPane;

    // Removed specific VBox FXML fields as content is now loaded dynamically per tab
    // @FXML private VBox xrayOptionsVBox;
    // @FXML private VBox ctScanOptionsVBox;
    // @FXML private VBox mriOptionsVBox;
    // @FXML private VBox ultrasoundOptionsVBox;

    private ObservableList<RadiologyRequest> allRequests = FXCollections.observableArrayList();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadStaticData();
        setupTabs(); // Now dynamically loads content for all tabs
    }

    private void loadStaticData() {
        Patient patient1 = new Patient(1, "John", "Doe", "Male", LocalDate.of(1985, 5, 20), "123-456-7890", "john.doe@example.com", "123 Main St", "A+", "Jane Doe", null);
        Patient patient2 = new Patient(2, "Jane", "Smith", "Female", LocalDate.of(1990, 8, 15), "098-765-4321", "jane.smith@example.com", "456 Oak Ave", "B-", "John Smith", null);
        patientList.addAll(patient1, patient2);

        Doctor doctor1 = new Doctor(1, "Alice", "Williams", "Cardiology", "111-222-3333", "alice.williams@example.com", "789 Maple St", null);
        doctorList.addAll(doctor1);

        // Sample RadiologyRequest with detailedTests for X-Ray
        allRequests.addAll(
            new RadiologyRequest(1, patient1, doctor1, "X-Ray", Arrays.asList("Skull X-Ray", "Cervical spine X-Ray"), "Completed", LocalDate.now(), "No abnormalities found."),
            new RadiologyRequest(2, patient2, doctor1, "CT Scan", Arrays.asList("CT Head"), "Pending", LocalDate.now(), ""),
            new RadiologyRequest(3, patient1, doctor1, "MRI", Arrays.asList("Brain MRI"), "Pending", LocalDate.now().plusDays(1), ""),
            new RadiologyRequest(4, patient2, doctor1, "Ultrasound", Arrays.asList("Abdomen Ultrasound"), "Completed", LocalDate.now().minusDays(1), "Normal.")
        );
    }

    private void setupTabs() {
        for (Tab tab : radiologyTabPane.getTabs()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("radiology-tab-view.fxml"));
                AnchorPane tabContent = loader.load();
                RadiologyTabController controller = loader.getController();
                // Pass the specific scanType and shared data to each tab's controller
                controller.setScanType(tab.getText(), allRequests, patientList, doctorList);
                tab.setContent(tabContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Removed handleSubmit...Tests() methods and helper methods as they are no longer needed here.
}
