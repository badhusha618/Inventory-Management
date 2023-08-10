package com.makinus.inventory.api.paytm.api.transaction.status;

public class ResponseBody {

    private ResultInfo resultInfo;
    private String txnId;
    private String bankTxnId;
    private String orderId;
    private String txnAmount;
    private String txnType;
    private String gatewayName;
    private String bankName;
    private String mid;
    private String paymentMode;
    private String refundAmount;
    private String txnDate;
    private String subsId;
    private String payableAmount;
    private String paymentPromoCheckoutData;
    private VanInfo vanInfo;
    private SourceAccount sourceAccountDetails;
    private String transferMode;
    private String utr;
    private String bankTransactionDate;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getSubsId() {
        return subsId;
    }

    public void setSubsId(String subsId) {
        this.subsId = subsId;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPaymentPromoCheckoutData() {
        return paymentPromoCheckoutData;
    }

    public void setPaymentPromoCheckoutData(String paymentPromoCheckoutData) {
        this.paymentPromoCheckoutData = paymentPromoCheckoutData;
    }

    public VanInfo getVanInfo() {
        return vanInfo;
    }

    public void setVanInfo(VanInfo vanInfo) {
        this.vanInfo = vanInfo;
    }

    public SourceAccount getSourceAccountDetails() {
        return sourceAccountDetails;
    }

    public void setSourceAccountDetails(SourceAccount sourceAccountDetails) {
        this.sourceAccountDetails = sourceAccountDetails;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getBankTransactionDate() {
        return bankTransactionDate;
    }

    public void setBankTransactionDate(String bankTransactionDate) {
        this.bankTransactionDate = bankTransactionDate;
    }
}
