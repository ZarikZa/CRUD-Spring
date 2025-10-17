package com.mpt.journal.model;

public class CategoryModel {
    private int id;
    private String name;
    private String description;
    private boolean isDeleted;

    public CategoryModel() {}

    public CategoryModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDeleted = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
}