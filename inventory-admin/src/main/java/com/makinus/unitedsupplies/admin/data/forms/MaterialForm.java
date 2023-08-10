package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/** Created by ammar */
public class MaterialForm implements Serializable {

    private String materialID;
    private String material;
    private String category;
    private boolean active;
    private String createdOn;

    /** @return the MaterialID */
    public String getMaterialID() {
        return materialID;
    }
    /** @param materialID the materialID to set */
    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    /** @return the material */
    public String getMaterial() {
        return material;
    }
    /** @param material the material to set */
    public void setMaterial(String material) {
        this.material = material;
    }

    /** @return the category */
    public String getCategory() {
        return category;
    }

    /** @param category the category to set */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @return the active */
    public boolean isActive() {
        return active;
    }

    /** @param active the active to set */
    public void setActive(boolean active) {
        this.active = active;
    }

    /** @return the createdOn */
    public String getCreatedOn() {
        return createdOn;
    }

    /** @param createdOn the createdOn to set */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
