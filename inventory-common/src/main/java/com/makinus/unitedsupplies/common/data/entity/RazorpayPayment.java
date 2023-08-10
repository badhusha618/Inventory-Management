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
@Table(name = "RAZORPAY_PAYMENT")
public class RazorpayPayment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "RAZOR_PAYMENT_ID")
    private String razorPaymentID;

    @Column(name = "RAZOR_ORDER_ID")
    private String razorOrderID;

    @Column(name = "RAZOR_SIGNATURE")
    private String razorSignature;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    public RazorpayPayment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRazorPaymentID() {
        return razorPaymentID;
    }

    public void setRazorPaymentID(String razorPaymentID) {
        this.razorPaymentID = razorPaymentID;
    }

    public String getRazorOrderID() {
        return razorOrderID;
    }

    public void setRazorOrderID(String razorOrderID) {
        this.razorOrderID = razorOrderID;
    }

    public String getRazorSignature() {
        return razorSignature;
    }

    public void setRazorSignature(String razorSignature) {
        this.razorSignature = razorSignature;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RazorpayPayment)) return false;
        RazorpayPayment that = (RazorpayPayment) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getOrderNo(), that.getOrderNo()) &&
                Objects.equals(getOrderId(), that.getOrderId()) &&
                Objects.equals(getRazorPaymentID(), that.getRazorPaymentID()) &&
                Objects.equals(getRazorOrderID(), that.getRazorOrderID()) &&
                Objects.equals(getRazorSignature(), that.getRazorSignature()) &&
                Objects.equals(getPaymentStatus(), that.getPaymentStatus()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getCurrency(), that.getCurrency()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreatedBy(), that.getCreatedBy()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getOrderNo(), getOrderId(), getRazorPaymentID(), getRazorOrderID(), getRazorSignature(), getPaymentStatus(), getAmount(), getCurrency(), getReason(), getDescription(), getCreatedBy(), getCreatedDate());
    }

    @Override
    public String toString() {
        return "RazorpayPayment{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderId=" + orderId +
                ", razorPaymentID='" + razorPaymentID + '\'' +
                ", razorOrderID='" + razorOrderID + '\'' +
                ", razorSignature='" + razorSignature + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}