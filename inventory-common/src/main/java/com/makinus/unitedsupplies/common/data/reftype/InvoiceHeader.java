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
public enum InvoiceHeader {

  ORIGINAL_FOR_RECIPIENT("O", "Original for Recipient"),
  DUPLICATE_FOR_TRANSPORTER("D", "Duplicate for Transporter"),
  TRIPLICATE_FOR_SUPPLIER("T", "Triplicate for Supplier");

  private String status;
  private String display;

  InvoiceHeader(String status, String display) {
    this.status = status;
    this.display = display;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }
  public static InvoiceHeader statusMatch(String status) {
    for (InvoiceHeader order : InvoiceHeader.values()) {
      if (order.getStatus().equalsIgnoreCase(status)) {
        return order;
      }
    }
    return ORIGINAL_FOR_RECIPIENT;
  }

  @Override
  public String toString() {
    return "Invoice header values - "
        + ORIGINAL_FOR_RECIPIENT.getDisplay()
        + " "
        + DUPLICATE_FOR_TRANSPORTER.getDisplay()
        + " "
        + TRIPLICATE_FOR_SUPPLIER.getDisplay();
  }
}
