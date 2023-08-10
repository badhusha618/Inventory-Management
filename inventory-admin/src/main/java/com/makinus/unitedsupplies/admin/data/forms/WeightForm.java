package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

public class WeightForm implements Serializable {

    private String weightID;
    private String weight;
    private String category;
    private boolean active;
    private String createdOn;

    public String getWeightID() {
        return weightID;
    }

    public void setWeightID(String weightID) {
        this.weightID = weightID;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
