/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.api.json.request;

import io.swagger.annotations.ApiModelProperty;

/** Created by abuabdul */
public class UserAddress {

  @ApiModelProperty(notes = "Email to be sent for identifying the user")
  private String email;

  @ApiModelProperty(notes = "Phone No to be sent for identifying the user")
  private String phoneNo;

  @ApiModelProperty(
      notes = "Type to determine email or phoneNo to be used for identifying the user",
      required = true)
  private String type;

  @ApiModelProperty(notes = "Door no on the address", required = true)
  private String doorNo;

  @ApiModelProperty(notes = "Street name on the address", required = true)
  private String street;

  @ApiModelProperty(notes = "Area on the address", required = true)
  private String area;

  @ApiModelProperty(notes = "City on the address", required = true)
  private String city;

  @ApiModelProperty(notes = "Postal code on the address", required = true)
  private String postalCode;

  @ApiModelProperty(notes = "Mobile number on the address", required = true)
  private String mobileNo;

  @ApiModelProperty(notes = "Address position, possible values[1,2,3]", required = true)
  private String position;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDoorNo() {
    return doorNo;
  }

  public void setDoorNo(String doorNo) {
    this.doorNo = doorNo;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }
}
