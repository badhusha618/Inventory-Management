package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/** Created by abuabdul */
public class GradeForm implements Serializable {

  private String gradeID;
  private String grade;
  private String category;
  private boolean active;
  private String createdOn;

  /** @return the gradeID */
  public String getGradeID() {
    return gradeID;
  }
  /** @param gradeID the gradeID to set */
  public void setGradeID(String gradeID) {
    this.gradeID = gradeID;
  }

  /** @return the grade */
  public String getGrade() {
    return grade;
  }
  /** @param grade the grade to set */
  public void setGrade(String grade) {
    this.grade = grade;
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
