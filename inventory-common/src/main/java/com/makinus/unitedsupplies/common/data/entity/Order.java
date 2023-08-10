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
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_REF")
    private Long orderRef;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "ORDER_SUBTOTAL")
    private BigDecimal subTotal;

    @Column(name = "TRANS_CHARGES")
    private BigDecimal transportCharges;

    @Column(name = "SERV_CHARGES")
    private BigDecimal serviceCharges;

    @Column(name = "LOAD_CHARGES")
    private BigDecimal loadingCharges;

    @Column(name = "ORDER_TOTAL")
    private BigDecimal orderTotal;

    @Column(name = "DEL_ADDRESS_ID")
    private Long delAddressId;

    @Column(name = "BILL_ADDRESS_ID")
    private Long billAddressId;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "PAID_AMOUNT")
    private BigDecimal paidAmount;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DELETED")
    private String deleted;

    @Column(name = "IS_READ")
    private String isRead;

    @Column(name = "CUST_SERV_INVOICE_NO")
    private String custServInvoiceNo;

    @Column(name = "CUST_SERV_INVOICE_DATE")
    private Date custServInvoiceDate;

    @Column(name = "CHANGE_LOG")
    private String changeLog;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "GW_ORDER_ID")
    private String gatewayOrderId;

    @Column(name = "PAYMENT_PROVIDER")
    private String paymentProvider;

    @Transient
    private String paymentDisplay;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(BigDecimal transportCharges) {
        this.transportCharges = transportCharges;
    }

    public BigDecimal getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(BigDecimal serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public BigDecimal getLoadingCharges() {
        return loadingCharges;
    }

    public void setLoadingCharges(BigDecimal loadingCharges) {
        this.loadingCharges = loadingCharges;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Long getDelAddressId() {
        return delAddressId;
    }

    public void setDelAddressId(Long delAddressId) {
        this.delAddressId = delAddressId;
    }

    public Long getBillAddressId() {
        return billAddressId;
    }

    public void setBillAddressId(Long billAddressId) {
        this.billAddressId = billAddressId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentDisplay() {
        return paymentDisplay;
    }

    public void setPaymentDisplay(String paymentDisplay) {
        this.paymentDisplay = paymentDisplay;
    }

    public String getCustServInvoiceNo() {
        return custServInvoiceNo;
    }

    public void setCustServInvoiceNo(String custServInvoiceNo) {
        this.custServInvoiceNo = custServInvoiceNo;
    }

    public Date getCustServInvoiceDate() {
        return custServInvoiceDate;
    }

    public void setCustServInvoiceDate(Date custServInvoiceDate) {
        this.custServInvoiceDate = custServInvoiceDate;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getGatewayOrderId() {
        return gatewayOrderId;
    }

    public void setGatewayOrderId(String gatewayOrderId) {
        this.gatewayOrderId = gatewayOrderId;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderRef, order.orderRef) &&
                Objects.equals(user, order.user) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(orderNo, order.orderNo) &&
                Objects.equals(subTotal, order.subTotal) &&
                Objects.equals(transportCharges, order.transportCharges) &&
                Objects.equals(serviceCharges, order.serviceCharges) &&
                Objects.equals(loadingCharges, order.loadingCharges) &&
                Objects.equals(orderTotal, order.orderTotal) &&
                Objects.equals(delAddressId, order.delAddressId) &&
                Objects.equals(billAddressId, order.billAddressId) &&
                Objects.equals(paymentType, order.paymentType) &&
                Objects.equals(paidAmount, order.paidAmount) &&
                Objects.equals(createdBy, order.createdBy) &&
                Objects.equals(createdDate, order.createdDate) &&
                Objects.equals(updatedBy, order.updatedBy) &&
                Objects.equals(updatedDate, order.updatedDate) &&
                Objects.equals(status, order.status) &&
                Objects.equals(deleted, order.deleted) &&
                Objects.equals(isRead, order.isRead) &&
                Objects.equals(custServInvoiceNo, order.custServInvoiceNo) &&
                Objects.equals(custServInvoiceDate, order.custServInvoiceDate) &&
                Objects.equals(changeLog, order.changeLog) &&
                Objects.equals(paymentStatus, order.paymentStatus) &&
                Objects.equals(gatewayOrderId, order.gatewayOrderId) &&
                Objects.equals(paymentProvider, order.paymentProvider) &&
                Objects.equals(paymentDisplay, order.paymentDisplay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderRef, user, orderDate, orderNo, subTotal, transportCharges, serviceCharges, loadingCharges, orderTotal, delAddressId, billAddressId, paymentType, paidAmount, createdBy, createdDate, updatedBy, updatedDate, status, deleted, isRead, custServInvoiceNo, custServInvoiceDate, changeLog, paymentStatus, gatewayOrderId, paymentProvider, paymentDisplay);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderRef=" + orderRef +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", orderNo='" + orderNo + '\'' +
                ", subTotal=" + subTotal +
                ", transportCharges=" + transportCharges +
                ", serviceCharges=" + serviceCharges +
                ", loadingCharges=" + loadingCharges +
                ", orderTotal=" + orderTotal +
                ", delAddressId=" + delAddressId +
                ", billAddressId=" + billAddressId +
                ", paymentType='" + paymentType + '\'' +
                ", paidAmount=" + paidAmount +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", status='" + status + '\'' +
                ", deleted='" + deleted + '\'' +
                ", isRead='" + isRead + '\'' +
                ", custServInvoiceNo='" + custServInvoiceNo + '\'' +
                ", custServInvoiceDate=" + custServInvoiceDate +
                ", changeLog='" + changeLog + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", gatewayOrderId='" + gatewayOrderId + '\'' +
                ", paymentProvider='" + paymentProvider + '\'' +
                ", paymentDisplay='" + paymentDisplay + '\'' +
                '}';
    }
}
