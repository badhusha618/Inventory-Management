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
public class MobileUser {

  @ApiModelProperty(notes = "Name of the user", required = true)
  private String name;

  @ApiModelProperty(notes = "Email to be sent for identifying the user")
  private String email;

  @ApiModelProperty(notes = "Phone No to be sent for identifying the user")
  private String phoneNo;

  @ApiModelProperty(
      notes = "Type to determine email or phoneNo to be used for identifying the user",
      required = true)
  private String type;

  @ApiModelProperty(notes = "Password of the user", required = true)
  private String secret;

  @ApiModelProperty(notes = "Order id placed by user", required = true)
  private String userOrderId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUserOrderId() {
    return userOrderId;
  }

  public void setUserOrderId(String userOrderId) {
    this.userOrderId = userOrderId;
  }
}
