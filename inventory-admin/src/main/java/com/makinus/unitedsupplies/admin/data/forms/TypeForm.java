package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class TypeForm implements Serializable {

  private String typeID;
  private String type;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the typeID */
  public String getTypeID() {
    return typeID;
  }
  /** @param typeID the typeID to set */
  public void setTypeID(String typeID) {
    this.typeID = typeID;
  }

  /** @return the type */
  public String getType() {
    return type;
  }
  /** @param type the type to set */
  public void setType(String type) {
    this.type = type;
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
