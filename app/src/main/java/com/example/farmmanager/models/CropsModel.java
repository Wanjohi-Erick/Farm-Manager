package com.example.farmmanager.models;

public class CropsModel {
    private String cropName, harvestUnits, landName;
    public CropsModel(String cropName, String harvestUnits, String landName) {
        this.cropName = cropName;
        this.harvestUnits = harvestUnits;
        this.landName = landName;
    }

    public String getCropName() {
        return cropName;
    }

    public String getHarvestUnits() {
        return harvestUnits;
    }

    public String getLandName() {
        return landName;
    }
}
