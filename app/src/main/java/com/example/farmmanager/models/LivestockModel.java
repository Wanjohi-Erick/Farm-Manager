package com.example.farmmanager.models;

public class LivestockModel {
    private String livestockName, harvestUnits, landName;
    public LivestockModel(String livestockName, String harvestUnits, String landName) {
        this.livestockName = livestockName;
        this.harvestUnits = harvestUnits;
        this.landName = landName;
    }

    public String getLivestockName() {
        return livestockName;
    }

    public String getHarvestUnits() {
        return harvestUnits;
    }

    public String getLandName() {
        return landName;
    }
}
