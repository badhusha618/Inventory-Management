/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/** Created by sabique */
public class BillingAddressRequest {

  @ApiModelProperty(notes = "Full Name", example = "Abubacker", position = 1)
  private String fullName;

  @ApiModelProperty(notes = "Mobile for Contact", example = "9876789999", position = 2)
  private String mobile;

  @ApiModelProperty(
      notes = "Address",
      example = "67, 1st Cross Street, Adyar",
      position = 3)
  private String address;

  @ApiModelProperty(notes = "Postal Code", example = "600010", position = 4)
  private String postalCode;

  @ApiModelProperty(notes = "City Name", example = "Chennai", position = 5)
  private String city;

  @ApiModelProperty(notes = "Address Type", example = "I", position = 6)
  private String type;

  @ApiModelProperty(notes = "Gst Number", example = "33AACU693781ZJ", position = 7)
  private String gstNo;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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

  public String getGstNo() {
    return gstNo;
  }

  public void setGstNo(String gstNo) {
    this.gstNo = gstNo;
  }
}
