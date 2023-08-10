package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class ColorForm implements Serializable {

    private String colorID;
    private String color;
    private String category;
    private boolean active;
    private String createdOn;

    public String getColorID() { return colorID; }

    public void setColorID(String colorID) { this.colorID = colorID; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getCreatedOn() { return createdOn; }

    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }
}
