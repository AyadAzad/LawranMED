package com.example.lawranmed.model;

public class PrescriptionItem {
    private Drug drug;
    private int quantity;

    public PrescriptionItem(Drug drug, int quantity) {
        this.drug = drug;
        this.quantity = quantity;
    }

    public Drug getDrug() {
        return drug;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDrugName() {
        return drug.getName();
    }

    public double getPrice() {
        return drug.getPrice();
    }

    public double getTotal() {
        return drug.getPrice() * quantity;
    }
}
