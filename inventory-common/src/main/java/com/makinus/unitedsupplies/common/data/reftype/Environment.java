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
public enum Environment {
  STAGING("Staging", "Staging"),
  PRODUCTION("Production", "Production");

  private String status;
  private String display;

  Environment(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static Environment statusMatch(String status) {
    for (Environment Environment : Environment.values()) {
      if (Environment.getStatus().equalsIgnoreCase(status)) {
        return Environment;
      }
    }
    return STAGING;
  }
}
