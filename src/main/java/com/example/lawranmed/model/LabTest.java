package com.example.lawranmed.model;

import java.time.LocalDate;

public class LabTest {
    private int testId;
    private Patient patient;
    private String testType;
    private String status; // e.g., Pending, Completed, Cancelled
    private LocalDate testDate;
    private String results;

    public LabTest(int testId, Patient patient, String testType, String status, LocalDate testDate, String results) {
        this.testId = testId;
        this.patient = patient;
        this.testType = testType;
        this.status = status;
        this.testDate = testDate;
        this.results = results;
    }

    // Getters and Setters
    public int getTestId() { return testId; }
    public void setTestId(int testId) { this.testId = testId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getTestDate() { return testDate; }
    public void setTestDate(LocalDate testDate) { this.testDate = testDate; }
    public String getResults() { return results; }
    public void setResults(String results) { this.results = results; }

    // Convenience getter for TableView
    public String getPatientName() {
        return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
    }
}
