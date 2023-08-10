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

/** Created by Bad_sha */
public class OrderReceiptRequest {

  @ApiModelProperty(notes = "Order ID", example = "11", required = true, position = 1)
  private String orderId;

  @ApiModelProperty(notes = "User ID", example = "3", required = true, position = 2)
  private String userId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
