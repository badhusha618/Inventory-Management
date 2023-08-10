package com.makinus.unitedsupplies.api.paytm.api.merchant.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {

  @JsonProperty("MID")
  private String mid;

  @JsonProperty("TXNID")
  private String txnId;

  @JsonProperty("ORDERID")
  private String orderId;

  @JsonProperty("BANKTXNID")
  private String bankTxnId;

  @JsonProperty("TXNAMOUNT")
  private String txnAmount;

  @JsonProperty("STATUS")
  private String status;

  @JsonProperty("RESPCODE")
  private String responseCode;

  @JsonProperty("RESPMSG")
  private String responseMsg;

  @JsonProperty("TXNDATE")
  private String txnDate;

  @JsonProperty("GATEWAYNAME")
  private String gatewayName;

  @JsonProperty("BANKNAME")
  private String bankName;

  @JsonProperty("PAYMENTMODE")
  private String paymentMode;

  @JsonProperty("TXNTYPE")
  private String txnType;

  @JsonProperty("REFUNDAMT")
  private String refundAmt;

  @JsonProperty("SUBS_ID")
  private String subscriptionId;

  @JsonProperty("PAYABLE_AMOUNT")
  private String payableAmount;

  @JsonProperty("PAYMENT_PROMO_CHECKOUT_DATA")
  private String paymentPromoData;

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getTxnId() {
    return txnId;
  }

  public void setTxnId(String txnId) {
    this.txnId = txnId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getBankTxnId() {
    return bankTxnId;
  }

  public void setBankTxnId(String bankTxnId) {
    this.bankTxnId = bankTxnId;
  }

  public String getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(String txnAmount) {
    this.txnAmount = txnAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String getResponseMsg() {
    return responseMsg;
  }

  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }

  public String getTxnDate() {
    return txnDate;
  }

  public void setTxnDate(String txnDate) {
    this.txnDate = txnDate;
  }

  public String getGatewayName() {
    return gatewayName;
  }

  public void setGatewayName(String gatewayName) {
    this.gatewayName = gatewayName;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
  }

  public String getTxnType() {
    return txnType;
  }

  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  public String getRefundAmt() {
    return refundAmt;
  }

  public void setRefundAmt(String refundAmt) {
    this.refundAmt = refundAmt;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public String getPayableAmount() {
    return payableAmount;
  }

  public void setPayableAmount(String payableAmount) {
    this.payableAmount = payableAmount;
  }

  public String getPaymentPromoData() {
    return paymentPromoData;
  }

  public void setPaymentPromoData(String paymentPromoData) {
    this.paymentPromoData = paymentPromoData;
  }
}
