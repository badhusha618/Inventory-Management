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
import javax.persistence.*;

/** @author abuabdul */
@Entity
@Table(name = "PRODUCT_SOURCE")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class ProductSource {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "PROD_ID")
  private Long productId;

  @Column(name = "SOURCE_NAME")
  private String sourceName;

  @Column(name = "POSTAL_CODE")
  private String postelCode;

  @Column(name = "IS_DEFAULT")
  private String isDefault;

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

  @ApiModelProperty(hidden = true)
  @Column(name = "DELETED")
  private String deleted;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public String getPostelCode() {
    return postelCode;
  }

  public void setPostelCode(String postelCode) {
    this.postelCode = postelCode;
  }

  public String getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault;
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

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }
}
