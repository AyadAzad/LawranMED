package com.example.lawranmed.model;

public class Drug {
    private int drugId;
    private String name;
    private String manufacturer;
    private int stockQuantity;
    private double price;

    public Drug(int drugId, String name, String manufacturer, int stockQuantity, double price) {
        this.drugId = drugId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    // Getters and Setters
    public int getDrugId() { return drugId; }
    public void setDrugId(int drugId) { this.drugId = drugId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
