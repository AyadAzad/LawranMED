package com.example.lawranmed.model;

import java.time.LocalDate;

public class Staff {
    private int staffId;
    private String firstName;
    private String lastName;
    private String role;
    private Department department;
    private String contactNumber;
    private String email;
    private LocalDate hireDate;

    public Staff(int staffId, String firstName, String lastName, String role, Department department, String contactNumber, String email, LocalDate hireDate) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.contactNumber = contactNumber;
        this.email = email;
        this.hireDate = hireDate;
    }

    // Getters and Setters
    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    // Convenience getter for TableView
    public String getDepartmentName() {
        return department != null ? department.getName() : "";
    }
}
