package com.makinus.unitedsupplies.admin.data.service.excel;

import java.math.BigDecimal;
import java.util.Date;

public class OrderExcelDTO {


    private String id;
    private Date orderDate;
    private String orderNo;
    private BigDecimal subTotal;
    private BigDecimal transportCharges;
    private BigDecimal serviceCharges;
    private BigDecimal loadingCharges;
    private BigDecimal deliveryCharges;
    private BigDecimal orderTotal;
    private String delAddressRef;
    private String billAddressRef;
    private String paymentType;
    private BigDecimal paidAmount;
    private String status;
    private String distanceBtwLoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(BigDecimal deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getDelAddressRef() {
        return delAddressRef;
    }

    public void setDelAddressRef(String delAddressRef) {
        this.delAddressRef = delAddressRef;
    }

    public String getBillAddressRef() {
        return billAddressRef;
    }

    public void setBillAddressRef(String billAddressRef) {
        this.billAddressRef = billAddressRef;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistanceBtwLoc() {
        return distanceBtwLoc;
    }

    public void setDistanceBtwLoc(String distanceBtwLoc) {
        this.distanceBtwLoc = distanceBtwLoc;
    }
}
