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

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SPECIFICATION")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class Specification {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "SPECIFICATION")
  private String specification;

  @Column(name = "CATEGORY_ID")
  private Long category;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  public Long getCategory() {
    return category;
  }

  public void setCategory(Long category) {
    this.category = category;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Specification that = (Specification) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(specification, that.specification) &&
            Objects.equals(category, that.category) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(active, that.active) &&
            Objects.equals(deleted, that.deleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, specification, category, createdBy, createdDate, updatedBy, updatedDate, active, deleted);
  }

}
