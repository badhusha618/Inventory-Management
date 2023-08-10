/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.api.json.request;

/** Created by abuabdul */
public enum InsertOrderStatus {
  SUCCESS("S", "User Order Placed Successfully"),
  FAILED("F", "User Order cannot be inserted, Failed");

  private String status;
  private String description;

  InsertOrderStatus(String status, String desc) {
    this.status = status;
    this.description = desc;
  }

  public String getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public static InsertOrderStatus statusMatch(String status) {
    for (InsertOrderStatus insertOrder : InsertOrderStatus.values()) {
      if (insertOrder.getStatus().equalsIgnoreCase(status)) {
        return insertOrder;
      }
    }
    return FAILED;
  }
}
