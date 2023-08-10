/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.api.json.response;

import io.swagger.annotations.ApiModelProperty;

/** Created by Bad_sha */
public class MyOrderJSON {

  @ApiModelProperty(notes = "Id of the order placed")
  private String id;

  @ApiModelProperty(notes = "Date of the order placed")
  private String date;

  @ApiModelProperty(notes = "Order total amount")
  private String amount;

  @ApiModelProperty(notes = "Count of the products placed under order")
  private String items;

  @ApiModelProperty(notes = "Order status")
  private String status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getItems() {
    return items;
  }

  public void setItems(String items) {
    this.items = items;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
