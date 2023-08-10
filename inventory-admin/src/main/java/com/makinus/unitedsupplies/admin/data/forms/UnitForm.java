package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/** Created by abuabdul */
public class UnitForm implements Serializable {

  private String unitID;
  private String unitName;
  private String unitCode;
  private boolean active;
  private String createdOn;

  /** @return the unitID */
  public String getUnitID() {
    return unitID;
  }
  /** @param unitID the unitID to set */
  public void setUnitID(String unitID) {
    this.unitID = unitID;
  }

  /** @return the unitName */
  public String getUnitName() {
    return unitName;
  }
  /** @param unitName the unitName to set */
  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  /** @return the unit */
  public String getUnitCode() {
    return unitCode;
  }

  /** @param unitCode the unitCode to set */
  public void setUnitCode(String unitCode) {
    this.unitCode = unitCode;
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
