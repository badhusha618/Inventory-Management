/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/** Created by sabique */
public class OrderVerificationRequest {

  @ApiModelProperty(notes = "Order ID", example = "79", required = true, position = 1)
  private String orderId;

  @ApiModelProperty(notes = "User ID", example = "4", required = true, position = 2)
  private String userId;

  @ApiModelProperty(
      notes = "Checksum",
      example =
          "QOOoWh2O5Ev8CVjiFKLuywh6ptTa5qiH1eVP8PVjjc9nm8eiJmeF4+49tbUs0kh+pIAQ3Zq2jnyScr3MJ4puovBquNH/LT1xXlFXY1dpizw=",
      required = true,
      position = 3)
  private String checksum;

  @ApiModelProperty(
      notes = "User Mobile Number",
      example = "9878987889",
      required = true,
      position = 4)
  private String mobile;

  @ApiModelProperty(notes = "User Email Address", example = "abu@makinus.com", position = 5)
  private String email;

  @ApiModelProperty(
      notes = "Total Payable Charge",
      example = "55500.00",
      required = true,
      position = 6)
  private String totalCharges;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTotalCharges() {
    return totalCharges;
  }

  public void setTotalCharges(String totalCharges) {
    this.totalCharges = totalCharges;
  }
}
