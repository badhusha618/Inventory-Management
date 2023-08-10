package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class UnitMappingForm implements Serializable {

  private String unitMappingID;
  private String unit;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the unitMappingID */
  public String getUnitMappingID() {
    return unitMappingID;
  }
  /** @param unitMappingID the unitMappingID to set */
  public void setUnitMappingID(String unitMappingID) {
    this.unitMappingID = unitMappingID;
  }

  /** @return the unit */
  public String getUnit() {
    return unit;
  }
  /** @param unit the unit to set */
  public void setUnit(String unit) {
    this.unit = unit;
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
