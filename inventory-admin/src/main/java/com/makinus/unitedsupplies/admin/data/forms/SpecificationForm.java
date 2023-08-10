package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class SpecificationForm implements Serializable {

  private String specificationID;
  private String specification;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the SpecificationID */
  public String getSpecificationID() {
    return specificationID;
  }
  /** @param specificationID the specificationID to set */
  public void setSpecificationID(String specificationID) {
    this.specificationID = specificationID;
  }

  /** @return the specification */
  public String getSpecification() {
    return specification;
  }
  /** @param specification the specification to set */
  public void setSpecification(String specification) {
    this.specification = specification;
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
