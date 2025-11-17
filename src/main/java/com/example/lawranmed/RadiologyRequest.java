package com.example.lawranmed;

import java.time.LocalDate;

public class RadiologyRequest {
    private int requestId;
    private Patient patient;
    private Doctor requestingDoctor;
    private String scanType; // X-Ray, CT Scan, MRI, Ultrasound
    private String status; // Pending, Completed, Cancelled
    private LocalDate requestDate;
    private String report;

    public RadiologyRequest(int requestId, Patient patient, Doctor requestingDoctor, String scanType, String status, LocalDate requestDate, String report) {
        this.requestId = requestId;
        this.patient = patient;
        this.requestingDoctor = requestingDoctor;
        this.scanType = scanType;
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
