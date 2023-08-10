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
 * Created by ammar
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class Notifications {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_REF")
    private Long orderRef;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SENT_BY")
    private String sentBy;

    @Column(name = "SENT_DATE")
    private Date sentDate;

    @Column(name = "REFUND")
    private BigDecimal refund;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "DELIVERY_DATE")
    private Date deliveryDate;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_NAME")
    private String productName;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifications that = (Notifications) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderRef, that.orderRef) &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(mobileNo, that.mobileNo) &&
                Objects.equals(status, that.status) &&
                Objects.equals(type, that.type) &&
                Objects.equals(sentBy, that.sentBy) &&
                Objects.equals(sentDate, that.sentDate) &&
                Objects.equals(refund, that.refund) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(orderStatus, that.orderStatus) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderRef, orderNo, customerName, mobileNo, status, type, sentBy, sentDate, refund, reason, orderStatus, deliveryDate, productId, productCode, productName);

    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", orderRef=" + orderRef +
                ", orderNo='" + orderNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", sentBy='" + sentBy + '\'' +
                ", sentDate=" + sentDate +
                ", refund=" + refund +
                ", reason='" + reason + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}

