package com.example.lawranmed.model;

import java.time.LocalDate;

public class Billing {
    private int invoiceId;
    private Patient patient;
    private Appointment appointment;
    private double amount;
    private String status; // e.g., Paid, Unpaid, Partially Paid
    private LocalDate billingDate;

    public Billing(int invoiceId, Patient patient, Appointment appointment, double amount, String status, LocalDate billingDate) {
        this.invoiceId = invoiceId;
        this.patient = patient;
        this.appointment = appointment;
        this.amount = amount;
        this.status = status;
        this.billingDate = billingDate;
    }

    // Getters and Setters
    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDate billingDate) { this.billingDate = billingDate; }

    // Convenience getters for TableView
    public String getPatientName() {
        return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
    }

    public int getAppointmentId() {
        return appointment != null ? appointment.getAppointmentId() : 0;
    }
}
