package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/** Created by abuabdul */
public class SizeForm implements Serializable {

  private String sizeID;
  private String size;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the sizeID */
  public String getSizeID() {
    return sizeID;
  }
  /** @param sizeID the sizeID to set */
  public void setSizeID(String sizeID) {
    this.sizeID = sizeID;
  }

  /** @return the size */
  public String getSize() {
    return size;
  }
  /** @param size the size to set */
  public void setSize(String size) {
    this.size = size;
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
