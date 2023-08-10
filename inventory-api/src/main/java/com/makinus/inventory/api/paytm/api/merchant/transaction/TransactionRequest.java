package com.makinus.inventory.api.paytm.api.merchant.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {

  @JsonProperty("MID")
  private String mid;

  @JsonProperty("ORDERID")
  private String orderId;

  @JsonProperty("CHECKSUMHASH")
  private String checksum;

  @JsonProperty("TXNTYPE")
  private String txnType;

  public TransactionRequest(String mid, String orderId, String checksum, String txnType) {
    this.mid = mid;
    this.orderId = orderId;
    this.checksum = checksum;
    this.txnType = txnType;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }
}
