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
public enum AddressType {
  INDIVIDUAL("I", "Individual"),
  FIRM_OR_COMPANY("F", "Firm or Company");

  private String status;
  private String display;

  AddressType(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static AddressType statusMatch(String status) {
    for (AddressType AddressCategory : AddressType.values()) {
      if (AddressCategory.getStatus().equalsIgnoreCase(status)) {
        return AddressCategory;
      }
    }
    return INDIVIDUAL;
  }
}
