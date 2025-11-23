package com.example.lawranmed.controller;

import com.example.lawranmed.model.Doctor;
import com.example.lawranmed.model.Patient;
import com.example.lawranmed.model.RadiologyRequest;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RadiologyRequestFormController {

    @FXML
    private Label titleLabel;
    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Doctor> doctorComboBox;
    @FXML
    private DatePicker requestDatePicker;
    @FXML
    private Label scanTypeLabel; // New FXML field
    @FXML
    private VBox testSelectionVBox; // New FXML field for dynamic test options
    @FXML
    private TextArea reportTextArea; // New FXML field for report

    private Stage dialogStage;
    private RadiologyRequest request;
    private boolean saveClicked = false;
    private String currentScanType; // To store the scan type passed from RadiologyTabController

    // Static map to hold all radiology test options
    private static final Map<String, Map<String, List<String>>> ALL_RADIOLOGY_TESTS = new HashMap<>();

    static {
        // X-Ray Tests
        Map<String, List<String>> xRayTests = new LinkedHashMap<>();
        xRayTests.put("1- Head & Neck", Arrays.asList(
                "Skull X-Ray", "Sinuses X-Ray", "Facial bones X-Ray", "Orbits (eyes) X-Ray",
                "Nasal bones X-Ray", "Mandible (jaw) X-Ray", "Cervical spine X-Ray", "Neck soft tissue X-Ray"
        ));
        xRayTests.put("2- Chest & Lungs", Arrays.asList(
                "Chest (PA/AP) X-Ray", "Ribs X-Ray", "Sternum X-Ray", "Clavicle X-Ray"
        ));
        xRayTests.put("3- Spine & Back", Arrays.asList(
                "Cervical spine X-Ray", "Thoracic spine X-Ray", "Lumbar spine X-Ray",
                "Sacrum X-Ray", "Coccyx X-Ray", "Whole spine X-Ray"
        ));
        xRayTests.put("4- Upper Limb", Arrays.asList(
                "Shoulder X-Ray", "Humerus X-Ray", "Elbow X-Ray", "Forearm X-Ray",
                "Wrist X-Ray", "Hand X-Ray", "Fingers X-Ray"
        ));
        xRayTests.put("5- Lower Limb", Arrays.asList(
                "Pelvis X-Ray", "Hip X-Ray", "Femur X-Ray", "Knee X-Ray",
                "Leg (Tibia/Fibula) X-Ray", "Ankle X-Ray", "Foot X-Ray", "Toes X-Ray"
        ));
        xRayTests.put("6- Abdomen & Pelvis", Arrays.asList(
                "KUB – kidney, ureter, bladder X-Ray", "Pelvis X-Ray", "Erect abdomen X-Ray", "Supine abdomen X-Ray"
        ));
        xRayTests.put("7- Dental", Arrays.asList(
                "Panoramic (OPG) X-Ray", "Intraoral X-Ray", "Bitewing X-Ray"
        ));
        xRayTests.put("8- Special Views", Arrays.asList(
                "Chest lateral X-Ray", "Oblique views X-Ray", "Decubitus X-Ray"
        ));
        ALL_RADIOLOGY_TESTS.put("X-Ray", xRayTests);

        // CT Scan Tests (random names for now as requested)
        Map<String, List<String>> ctScanTests = new LinkedHashMap<>();
        ctScanTests.put("CT Scan Types", Arrays.asList(
                "CT Head", "CT Chest", "CT Abdomen", "CT Pelvis", "CT Spine", "CT Angiography"
        ));
        ALL_RADIOLOGY_TESTS.put("CT Scan", ctScanTests);

        // MRI Tests
        Map<String, List<String>> mriTests = new LinkedHashMap<>();
        mriTests.put("MRI Types", Arrays.asList(
                "Brain MRI", "Spine MRI (C-spine, T-spine, L-spine)", "Neck MRI", "Chest MRI",
                "Abdomen MRI", "Pelvis MRI", "Musculoskeletal MRI (joints & limbs)", "Vascular MRI (MRA)"
        ));
        ALL_RADIOLOGY_TESTS.put("MRI", mriTests);

        // Ultrasound Tests
        Map<String, List<String>> ultrasoundTests = new LinkedHashMap<>();
        ultrasoundTests.put("Ultrasound Types", Arrays.asList(
                "Abdomen Ultrasound", "Pelvis Ultrasound", "Pregnancy Ultrasound", "Thyroid Ultrasound",
                "Doppler Ultrasound (veins/arteries)", "Soft tissue Ultrasound"
        ));
        ALL_RADIOLOGY_TESTS.put("Ultrasound", ultrasoundTests);
    }

    @FXML
    private void initialize() {
        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
            }
            @Override
            public Patient fromString(String string) { return null; }
        });

        doctorComboBox.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doctor) {
                return doctor != null ? doctor.getFirstName() + " " + doctor.getLastName() : "";
            }
            @Override
            public Doctor fromString(String string) { return null; }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRequest(RadiologyRequest request, ObservableList<Patient> patientList, ObservableList<Doctor> doctorList, String scanType) {
        this.request = request;
        this.currentScanType = scanType; // Store the scan type
        patientComboBox.setItems(patientList);
        doctorComboBox.setItems(doctorList);

        titleLabel.setText("New " + scanType + " Request");
        scanTypeLabel.setText(scanType); // Display the scan type

        populateTestOptions(scanType); // Dynamically populate test options

        if (request != null) {
            patientComboBox.setValue(request.getPatient());
            doctorComboBox.setValue(request.getRequestingDoctor());
            requestDatePicker.setValue(request.getRequestDate());
            reportTextArea.setText(request.getReport()); // Set existing report

            // Pre-select checkboxes if editing an existing request
            if (request.getDetailedTests() != null) {
                for (Node parentNode : testSelectionVBox.getChildren()) {
                    if (parentNode instanceof TitledPane) {
                        TitledPane titledPane = (TitledPane) parentNode;
                        if (titledPane.getContent() instanceof VBox) {
                            VBox categoryVBox = (VBox) titledPane.getContent();
                            for (Node childNode : categoryVBox.getChildren()) {
                                if (childNode instanceof CheckBox) {
                                    CheckBox checkBox = (CheckBox) childNode;
                                    if (request.getDetailedTests().contains(checkBox.getText())) {
                                        checkBox.setSelected(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // For new requests, set default date
            requestDatePicker.setValue(LocalDate.now());
        }
    }

    private void populateTestOptions(String scanType) {
        testSelectionVBox.getChildren().clear(); // Clear existing options

        Map<String, List<String>> testsForScanType = ALL_RADIOLOGY_TESTS.get(scanType);
        if (testsForScanType != null) {
            for (Map.Entry<String, List<String>> entry : testsForScanType.entrySet()) {
                String categoryName = entry.getKey();
                List<String> testNames = entry.getValue();

                VBox categoryVBox = new VBox(5);
                for (String testName : testNames) {
                    categoryVBox.getChildren().add(new CheckBox(testName));
                }

                TitledPane titledPane = new TitledPane(categoryName, categoryVBox);
                titledPane.setAnimated(false);
                testSelectionVBox.getChildren().add(titledPane);
            }
        } else {
            testSelectionVBox.getChildren().add(new Label("No specific tests defined for " + scanType));
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            request.setPatient(patientComboBox.getValue());
            request.setRequestingDoctor(doctorComboBox.getValue());
            request.setRequestDate(requestDatePicker.getValue());
            request.setScanType(currentScanType); // Ensure scan type is set

            // Collect selected detailed tests
            List<String> selectedTests = new ArrayList<>();
            for (Node parentNode : testSelectionVBox.getChildren()) {
                if (parentNode instanceof TitledPane) {
                    TitledPane titledPane = (TitledPane) parentNode;
                    if (titledPane.getContent() instanceof VBox) {
                        VBox categoryVBox = (VBox) titledPane.getContent();
                        for (Node childNode : categoryVBox.getChildren()) {
                            if (childNode instanceof CheckBox) {
                                CheckBox checkBox = (CheckBox) childNode;
                                if (checkBox.isSelected()) {
                                    selectedTests.add(checkBox.getText());
                                }
                            }
                        }
                    }
                }
            }
            request.setDetailedTests(selectedTests);
            request.setReport(reportTextArea.getText()); // Set the report text

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        // Basic validation: ensure patient, doctor, and date are selected
        if (patientComboBox.getValue() == null || doctorComboBox.getValue() == null || requestDatePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a patient, a requesting doctor, and a request date.");
            return false;
        }
        // Optionally, add validation for selected tests or report text
        return true;
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
