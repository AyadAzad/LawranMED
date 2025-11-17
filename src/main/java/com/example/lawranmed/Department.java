package com.example.lawranmed;

public class Department {
    private int departmentId;
    private String name;
    private String description;

    public Department(int departmentId, String name, String description) {
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return name; // Used to display the name in ComboBoxes
    }
}
