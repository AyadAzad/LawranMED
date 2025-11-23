package com.example.lawranmed.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status; // e.g., Scheduled, Completed, Cancelled
    private String reason;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String status, String reason) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.reason = reason;
    }

    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    // Convenience getters for TableView
    public String getPatientName() {
        return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
    }

    public String getDoctorName() {
        return doctor != null ? doctor.getFirstName() + " " + doctor.getLastName() : "";
    }
}
