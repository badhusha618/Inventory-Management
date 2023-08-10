package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/** Created by abuabdul */
public class QualityForm implements Serializable {

  private String qualityID;
  private String quality;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the QualityID */
  public String getQualityID() {
    return qualityID;
  }
  /** @param qualityID the qualityID to set */
  public void setQualityID(String qualityID) {
    this.qualityID = qualityID;
  }

  /** @return the quality */
  public String getQuality() {
    return quality;
  }
  /** @param quality the quality to set */
  public void setQuality(String quality) {
    this.quality = quality;
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
