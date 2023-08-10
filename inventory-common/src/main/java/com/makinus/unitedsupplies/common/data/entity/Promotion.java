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
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/** Created by Bad_sha */
@Entity
@Table(name = "PROMOTION")
@JsonIgnoreProperties({
  "imagePath",
  "image",
  "createdBy",
  "createdDate",
  "updatedBy",
  "updatedDate",
  "deleted",
  "originalFileName",
  "createdDateAsFolderName"
})
public class Promotion {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "PROMOTION_NAME")
  private String promotionName;

  @Column(name = "DESCRIPTION")
  private String description;

  @ApiModelProperty(hidden = true)
  @Column(name = "IMAGE_PATH")
  private String imagePath;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_BY")
  private String createdBy;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_DATE")
  private Date createdDate;

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

  @ApiModelProperty(hidden = true)
  @Transient
  private byte[] image;

  @ApiModelProperty(hidden = true)
  @Transient
  private String originalFileName;

  @ApiModelProperty(hidden = true)
  @Transient
  private String createdDateAsFolderName;

  @Transient private String imageAsString;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public String getImageAsString() {
    return imageAsString;
  }

  public void setImageAsString(String imageAsString) {
    this.imageAsString = imageAsString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Promotion promotion = (Promotion) o;
    return Objects.equals(id, promotion.id)
        && Objects.equals(promotionName, promotion.promotionName)
        && Objects.equals(description, promotion.description)
        && Objects.equals(imagePath, promotion.imagePath)
        && Objects.equals(createdBy, promotion.createdBy)
        && Objects.equals(createdDate, promotion.createdDate)
        && Objects.equals(updatedBy, promotion.updatedBy)
        && Objects.equals(updatedDate, promotion.updatedDate)
        && Objects.equals(active, promotion.active)
        && Objects.equals(deleted, promotion.deleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        promotionName,
        description,
        imagePath,
        createdBy,
        createdDate,
        updatedBy,
        updatedDate,
        active,
        deleted);
  }

  @Override
  public String toString() {
    return "Promotion{"
        + "id="
        + id
        + ", promotionName='"
        + promotionName
        + '\''
        + ", description='"
        + description
        + '\''
        + ", imagePath='"
        + imagePath
        + '\''
        + ", createdBy='"
        + createdBy
        + '\''
        + ", createdDate="
        + createdDate
        + ", updatedBy='"
        + updatedBy
        + '\''
        + ", updatedDate="
        + updatedDate
        + ", active='"
        + active
        + '\''
        + ", deleted='"
        + deleted
        + '\''
        + '}';
  }
}
