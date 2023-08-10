/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/** Created by Bad_sha */
@Entity
@Table(name = "CATEGORY")
@JsonIgnoreProperties({
  "imagePath",
  "image",
  "originalFileName",
  "createdDateAsFolderName",
  "createdBy",
  "createdDate",
  "updatedBy",
  "updatedDate",
  "deleted"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "PARENT_CATEGORY")
  private Long parentCategory;

  @Column(name = "CATEGORY_NAME")
  private String categoryName;

  @ApiModelProperty(hidden = true)
  @Column(name = "IMAGE_PATH")
  private String imagePath;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_BY")
  private String createdBy;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_DATE")
  private Date createdDate;

  @ApiModelProperty(hidden = true)
  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @ApiModelProperty(hidden = true)
  @Column(name = "UPDATED_DATE")
  private Date updatedDate;

  @Column(name = "ACTIVE")
  private String active;

  @ApiModelProperty(hidden = true)
  @Column(name = "DELETED")
  private String deleted;

  @Transient private List<Product> products;

  @ApiModelProperty(hidden = true)
  @Transient
  private byte[] image;

  @ApiModelProperty(hidden = true)
  @Transient
  private String originalFileName;

  @ApiModelProperty(hidden = true)
  @Transient
  private String createdDateAsFolderName;

  @Transient private String imageUrl;

  public Category() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(Long parentCategory) {
    this.parentCategory = parentCategory;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getCreatedDateAsFolderName() {
    return createdDateAsFolderName;
  }

  public void setCreatedDateAsFolderName(String createdDateAsFolderName) {
    this.createdDateAsFolderName = createdDateAsFolderName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
