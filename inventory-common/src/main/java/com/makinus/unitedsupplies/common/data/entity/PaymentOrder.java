/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Bad_sha
 */
@Entity
@Table(name = "PAYMENT_ORDERS")
public class PaymentOrder {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_REF")
    private Long orderRef;

    @Column(name = "RESULT_STATUS")
    private String resultStatus;

    @Column(name = "RESULT_CODE")
    private String resultCode;

    @Column(name = "RESULT_MSG")
    private String resultMsg;

    @Column(name = "TXN_ID")
    private String txnId;

    @Column(name = "BANK_TXN_ID")
    private String bankTxnId;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TXN_AMOUNT")
    private BigDecimal txnAmount;

    @Column(name = "TXN_TYPE")
    private String txnType;

    @Column(name = "GATEWAY_NAME")
    private String gatewayName;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "PAYMENT_MODE")
    private String paymentMode;

    @Column(name = "REFUND_AMOUNT")
    private BigDecimal refundAmount;

    @Column(name = "TXN_DATE")
    private Date txnDate;

    @Column(name = "SUBS_ID")
    private String subsId;

    @Column(name = "PAYABLE_AMOUNT")
    private BigDecimal payableAmount;

    @Column(name = "PAYMENT_PROMO")
    private String paymentPromo;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "TRANSFER_MODE")
    private String transferMode;

    @Column(name = "UTR")
    private String utr;

    @Column(name = "BANK_TXN_DATE")
    private Date bankTransactionDate;

    @OneToOne(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
    private VirtualAccount virtualAccount;

    @OneToOne(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
    private SourceAccount sourceAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(Long orderRef) {
        this.orderRef = orderRef;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(BigDecimal txnAmount) {
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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getSubsId() {
        return subsId;
    }

    public void setSubsId(String subsId) {
        this.subsId = subsId;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPaymentPromo() {
        return paymentPromo;
    }

    public void setPaymentPromo(String paymentPromo) {
        this.paymentPromo = paymentPromo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public Date getBankTransactionDate() {
        return bankTransactionDate;
    }

    public void setBankTransactionDate(Date bankTransactionDate) {
        this.bankTransactionDate = bankTransactionDate;
    }

    public VirtualAccount getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(VirtualAccount virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public SourceAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(SourceAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentOrder that = (PaymentOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(orderRef, that.orderRef) && Objects.equals(resultStatus, that.resultStatus) && Objects.equals(resultCode, that.resultCode) && Objects.equals(resultMsg, that.resultMsg) && Objects.equals(txnId, that.txnId) && Objects.equals(bankTxnId, that.bankTxnId) && Objects.equals(currency, that.currency) && Objects.equals(txnAmount, that.txnAmount) && Objects.equals(txnType, that.txnType) && Objects.equals(gatewayName, that.gatewayName) && Objects.equals(bankName, that.bankName) && Objects.equals(paymentMode, that.paymentMode) && Objects.equals(refundAmount, that.refundAmount) && Objects.equals(txnDate, that.txnDate) && Objects.equals(subsId, that.subsId) && Objects.equals(payableAmount, that.payableAmount) && Objects.equals(paymentPromo, that.paymentPromo) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updatedBy, that.updatedBy) && Objects.equals(updatedDate, that.updatedDate) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(transferMode, that.transferMode) && Objects.equals(utr, that.utr) && Objects.equals(bankTransactionDate, that.bankTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderRef, resultStatus, resultCode, resultMsg, txnId, bankTxnId, currency, txnAmount, txnType, gatewayName, bankName, paymentMode, refundAmount, txnDate, subsId, payableAmount, paymentPromo, createdBy, createdDate, updatedBy, updatedDate, orderStatus, transferMode, utr, bankTransactionDate);
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "id=" + id +
                ", orderRef=" + orderRef +
                ", resultStatus='" + resultStatus + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", txnId='" + txnId + '\'' +
                ", bankTxnId='" + bankTxnId + '\'' +
                ", currency='" + currency + '\'' +
                ", txnAmount=" + txnAmount +
                ", txnType='" + txnType + '\'' +
                ", gatewayName='" + gatewayName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", refundAmount=" + refundAmount +
                ", txnDate=" + txnDate +
                ", subsId='" + subsId + '\'' +
                ", payableAmount=" + payableAmount +
                ", paymentPromo='" + paymentPromo + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", transferMode='" + transferMode + '\'' +
                ", utr='" + utr + '\'' +
                ", bankTransactionDate=" + bankTransactionDate +
                '}';
    }
}
