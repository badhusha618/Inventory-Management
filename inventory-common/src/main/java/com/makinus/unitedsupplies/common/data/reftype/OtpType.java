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

/** Created by abuabdul */
public enum OtpType {
  FORGET_PASSWORD("F", "Forget Password"),
  LOGIN_VIA_OTP("L", "Login");

  private String status;
  private String display;

  OtpType(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static OtpType statusMatch(String status) {
    for (OtpType role : OtpType.values()) {
      if (role.getStatus().equalsIgnoreCase(status)) {
        return role;
      }
    }
    return FORGET_PASSWORD;
  }

  @Override
  public String toString() {
    return "UserRole values - " + FORGET_PASSWORD.getDisplay() + " " + LOGIN_VIA_OTP.getDisplay();
  }
}
