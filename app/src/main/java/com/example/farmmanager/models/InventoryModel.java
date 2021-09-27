package com.example.farmmanager.models;

public class InventoryModel {
    private String inventoryName, harvestCategory, landName;
    public InventoryModel(String inventoryName, String harvestCategory, String landName) {
        this.inventoryName = inventoryName;
        this.harvestCategory = harvestCategory;
        this.landName = landName;
    }

    public String getCropName() {
        return inventoryName;
    }

    public String getHarvestCategory() {
        return harvestCategory;
    }

    public String getLandName() {
        return landName;
    }
}
