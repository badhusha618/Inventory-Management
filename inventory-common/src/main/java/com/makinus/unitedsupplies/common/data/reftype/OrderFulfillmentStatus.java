/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/** Created by Bad_sha */
public enum OrderFulfillmentStatus {
  PREVIEW("P", "Preview"),
  ACCEPT("A", "Accept");

  private String status;
  private String display;

  OrderFulfillmentStatus(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }
  public static OrderFulfillmentStatus statusMatch(String status) {
    for (OrderFulfillmentStatus order : OrderFulfillmentStatus.values()) {
      if (order.getStatus().equalsIgnoreCase(status)) {
        return order;
      }
    }
    return PREVIEW;
  }

  @Override
  public String toString() {
    return "Order fulfillment values - "
        + PREVIEW.getDisplay()
        + " "
        + ACCEPT.getDisplay();
  }
}
