package com.example.lawranmed;

import java.time.LocalDate;
import java.util.List; // Import List

public class RadiologyRequest {
    private int requestId;
    private Patient patient;
    private Doctor requestingDoctor;
    private String scanType; // X-Ray, CT Scan, MRI, Ultrasound
    private List<String> detailedTests; // New field for specific X-Ray tests
    private String status; // Pending, Completed, Cancelled
    private LocalDate requestDate;
    private String report;

    public RadiologyRequest(int requestId, Patient patient, Doctor requestingDoctor, String scanType, String status, LocalDate requestDate, String report) {
        this(requestId, patient, requestingDoctor, scanType, null, status, requestDate, report); // Call new constructor
    }

    public RadiologyRequest(int requestId, Patient patient, Doctor requestingDoctor, String scanType, List<String> detailedTests, String status, LocalDate requestDate, String report) {
        this.requestId = requestId;
        this.patient = patient;
        this.requestingDoctor = requestingDoctor;
        this.scanType = scanType;
        this.detailedTests = detailedTests; // Initialize new field
        this.status = status;
        this.requestDate = requestDate;
        this.report = report;
    }

    // Getters and Setters
    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getRequestingDoctor() { return requestingDoctor; }
    public void setRequestingDoctor(Doctor requestingDoctor) { this.requestingDoctor = requestingDoctor; }
    public String getScanType() { return scanType; }
    public void setScanType(String scanType) { this.scanType = scanType; }
    public List<String> getDetailedTests() { return detailedTests; } // Getter for detailedTests
    public void setDetailedTests(List<String> detailedTests) { this.detailedTests = detailedTests; } // Setter for detailedTests
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
    public String getReport() { return report; }
    public void setReport(String report) { this.report = report; }

    // Convenience getters for TableView
    public String getPatientName() {
        return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
    }
    public String getDoctorName() {
        return requestingDoctor != null ? requestingDoctor.getFirstName() + " " + requestingDoctor.getLastName() : "";
    }
}
