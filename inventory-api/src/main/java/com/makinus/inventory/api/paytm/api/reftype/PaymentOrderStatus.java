/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.paytm.api.reftype;

/** Created by Bad_sha */
public enum PaymentOrderStatus {

  ORDER_SUCCESS("S", "Order Success"),
  ORDER_FAILED("F", "Order Failed");

  private String status;
  private String display;

  PaymentOrderStatus(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static PaymentOrderStatus statusMatch(String status) {
    for (PaymentOrderStatus order : PaymentOrderStatus.values()) {
      if (order.getStatus().equalsIgnoreCase(status)) {
        return order;
      }
    }
    return ORDER_FAILED;
  }

  @Override
  public String toString() {
    return "Payment Order Status values - "
        + ORDER_SUCCESS.getDisplay()
        + " "
        + ORDER_FAILED.getDisplay();
  }
}
