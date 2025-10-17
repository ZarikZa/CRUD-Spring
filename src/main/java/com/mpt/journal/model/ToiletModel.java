package com.mpt.journal.model;

public class ToiletModel {
    private int id;
    private String name;
    private String brand;
    private String model;
    private String color;
    private double price;
    private int stockQuantity;
    private String material;
    private int categoryId; // ID категории вместо объекта
    private boolean waterSaving;
    private boolean isActive;

    public ToiletModel() {
        this.isActive = true;
    }

    public ToiletModel(int id, String name, String brand, String model, String color,
                       double price, int stockQuantity, String material, int categoryId,
                       boolean waterSaving) {
        this();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.material = material;
        this.categoryId = categoryId;
        this.waterSaving = waterSaving;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public boolean isWaterSaving() { return waterSaving; }
    public void setWaterSaving(boolean waterSaving) { this.waterSaving = waterSaving; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}