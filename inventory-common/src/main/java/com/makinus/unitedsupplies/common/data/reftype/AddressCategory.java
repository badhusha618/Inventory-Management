/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/** Created by Bad_sha */
public enum AddressCategory {
  DELIVERY_ADDRESS("D", "Delivery Address"),
  BILLING_ADDRESS("B", "Billing Address");

  private String status;
  private String display;

  AddressCategory(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static AddressCategory statusMatch(String status) {
    for (AddressCategory AddressCategory : AddressCategory.values()) {
      if (AddressCategory.getStatus().equalsIgnoreCase(status)) {
        return AddressCategory;
      }
    }
    return DELIVERY_ADDRESS;
  }
}
