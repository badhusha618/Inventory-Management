/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.paytm.api.reftype;

/** Created by abuabdul */
public enum PaymentMode {

  PPI("PPI", "Paytm Wallet"),
  UPI("UPI", "BHIM UPI"),
  CC("CC", "Credit Card"),
  DC("DC", "Debit Card"),
  NB("NB", "Net Banking");

  private String status;
  private String display;

  PaymentMode(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public static PaymentMode statusMatch(String status) {
    for (PaymentMode mode : PaymentMode.values()) {
      if (mode.getStatus().equalsIgnoreCase(status)) {
        return mode;
      }
    }
    return PPI;
  }

  @Override
  public String toString() {
    return "Paytm Payment Mode values - "
        + PPI.getDisplay()
        + " "
        + UPI.getDisplay()
        + " "
        + CC.getDisplay()
        + " "
        + DC.getDisplay()
        + " "
        + NB.getDisplay();
  }
}
