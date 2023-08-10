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

/** Created by sabique */
@Entity
@Table(name = "ADDRESS")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class Address {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "REF")
  private Long ref;

  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "MOBILE_NO")
  private String mobile;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "POSTAL_CODE")
  private String postalCode;

  @Column(name = "CITY")
  private String city;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "CATEGORY")
  private String category;

  @Column(name = "GST_NO")
  private String gstNo;

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

  @Transient
  private String cityDisplay;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRef() {
    return ref;
  }

  public void setRef(Long ref) {
    this.ref = ref;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getGstNo() {
    return gstNo;
  }

  public void setGstNo(String gstNo) {
    this.gstNo = gstNo;
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

  public String getCityDisplay() {
    return cityDisplay;
  }

  public void setCityDisplay(String cityDisplay) {
    this.cityDisplay = cityDisplay;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Address)) return false;
    Address address1 = (Address) o;
    return Objects.equals(id, address1.id) &&
            Objects.equals(ref, address1.ref) &&
            Objects.equals(userId, address1.userId) &&
            Objects.equals(name, address1.name) &&
            Objects.equals(mobile, address1.mobile) &&
            Objects.equals(address, address1.address) &&
            Objects.equals(postalCode, address1.postalCode) &&
            Objects.equals(city, address1.city) &&
            Objects.equals(type, address1.type) &&
            Objects.equals(category, address1.category) &&
            Objects.equals(gstNo, address1.gstNo) &&
            Objects.equals(isDefault, address1.isDefault) &&
            Objects.equals(createdBy, address1.createdBy) &&
            Objects.equals(createdDate, address1.createdDate) &&
            Objects.equals(updatedBy, address1.updatedBy) &&
            Objects.equals(updatedDate, address1.updatedDate) &&
            Objects.equals(deleted, address1.deleted) &&
            Objects.equals(cityDisplay, address1.cityDisplay);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, ref, userId, name, mobile, address, postalCode, city, type, category, gstNo, isDefault, createdBy, createdDate, updatedBy, updatedDate, deleted, cityDisplay);
  }

  @Override
  public String toString() {
    return "Address{" +
            "id=" + id +
            ", ref=" + ref +
            ", userId=" + userId +
            ", name='" + name + '\'' +
            ", mobile='" + mobile + '\'' +
            ", address='" + address + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", city='" + city + '\'' +
            ", type='" + type + '\'' +
            ", category='" + category + '\'' +
            ", gstNo='" + gstNo + '\'' +
            ", isDefault='" + isDefault + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", updatedBy='" + updatedBy + '\'' +
            ", updatedDate=" + updatedDate +
            ", deleted='" + deleted + '\'' +
            ", cityDisplay='" + cityDisplay + '\'' +
            '}';
  }

  public String toDisplayableString() {
    return  name + ", "+
            address + ", " +
            city + "-"  +
            postalCode + ", " +
            mobile;
  }

}
