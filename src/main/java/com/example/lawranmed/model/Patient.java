package com.example.lawranmed.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
    private int patientId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String contactNumber;
    private String email;
    private String address;
    private String bloodGroup;
    private String emergencyContact;
    private LocalDateTime registrationDate;

    public Patient(int patientId, String firstName, String lastName, String gender, LocalDate dob, String contactNumber, String email, String address, String bloodGroup, String emergencyContact, LocalDateTime registrationDate) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.emergencyContact = emergencyContact;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}
