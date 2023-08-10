package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class CrusherForm implements Serializable {

  private String crusherID;
  private String crusher;
  private String category;
  private String location;
  private boolean active;
  private String createdOn;

  /** @return the crusherID */
  public String getCrusherID() {
    return crusherID;
  }

  /** @return the location */
  public String getLocation() {
    return location;
  }

  /** @param location the location to set */
  public void setLocation(String location) {
    this.location = location;
  }

  /** @param crusherID the crusherID to set */
  public void setCrusherID(String crusherID) {
    this.crusherID = crusherID;
  }

  /** @return the crusher */
  public String getCrusher() {
    return crusher;
  }
  /** @param crusher the crusher to set */
  public void setCrusher(String crusher) {
    this.crusher = crusher;
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
