package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class BrandForm implements Serializable {

  private String brandID;
  private String brandName;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the brandID */
  public String getBrandID() {
    return brandID;
  }
  /** @param brandID the brandID to set */
  public void setBrandID(String brandID) {
    this.brandID = brandID;
  }

  /** @return the brandName */
  public String getBrandName() {
    return brandName;
  }
  /** @param brandName the brandName to set */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
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
