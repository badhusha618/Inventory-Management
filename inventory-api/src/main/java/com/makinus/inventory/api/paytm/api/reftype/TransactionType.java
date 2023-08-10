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
public enum TransactionType {

  SALE("SALE"),
  PREAUTH("PREAUTH"),
  RELEASE("RELEASE"),
  CAPTURE("CAPTURE"),
  WITHDRAW("WITHDRAW");

  private String status;

  TransactionType(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public static TransactionType statusMatch(String status) {
    for (TransactionType txnType : TransactionType.values()) {
      if (txnType.getStatus().equalsIgnoreCase(status)) {
        return txnType;
      }
    }
    return SALE;
  }

  @Override
  public String toString() {
    return "Paytm Transaction Types - "
        + SALE.getStatus()
        + " "
        + PREAUTH.getStatus()
        + " "
        + RELEASE.getStatus()
        + " "
        + CAPTURE.getStatus()
        + " "
        + WITHDRAW.getStatus();
  }
}
