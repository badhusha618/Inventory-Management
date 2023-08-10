/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/** Created by Bad_sha */
public class CategoryForm implements Serializable {

  private String categoryID;
  private String parentCategory;
  private MultipartFile categoryImage;
  private MultipartFile editCategoryImage;
  private String categoryName;
  private boolean activeCategory;
  private String createdOn;

  public String getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(String categoryID) {
    this.categoryID = categoryID;
  }

  public String getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(String parentCategory) {
    this.parentCategory = parentCategory;
  }

  public MultipartFile getCategoryImage() {
    return categoryImage;
  }

  public void setCategoryImage(MultipartFile categoryImage) {
    this.categoryImage = categoryImage;
  }

  public MultipartFile getEditCategoryImage() {
    return editCategoryImage;
  }

  public void setEditCategoryImage(MultipartFile editCategoryImage) {
    this.editCategoryImage = editCategoryImage;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public boolean isActiveCategory() {
    return activeCategory;
  }

  public void setActiveCategory(boolean activeCategory) {
    this.activeCategory = activeCategory;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }
}
