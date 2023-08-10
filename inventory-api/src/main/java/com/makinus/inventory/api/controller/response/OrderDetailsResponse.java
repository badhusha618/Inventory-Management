/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Bad_sha
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderDetailsResponse {

    private String orderRef;
    private String orderNo;
    private String orderDate;
    private String totalAmount;
    private String paymentStatus;
    private String description;
    private String orderStatus;
    private String purchaseOrderDownloadLink;
    private String serviceInvoiceDownloadLink;
    private String deliveryAddress;
    private List<ProductOrderResponse> productStatus;
    private List<ProductInvoice> productInvoice;

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPurchaseOrderDownloadLink() {
        return purchaseOrderDownloadLink;
    }

    public void setPurchaseOrderDownloadLink(String purchaseOrderDownloadLink) {
        this.purchaseOrderDownloadLink = purchaseOrderDownloadLink;
    }

    public String getServiceInvoiceDownloadLink() {
        return serviceInvoiceDownloadLink;
    }

    public void setServiceInvoiceDownloadLink(String serviceInvoiceDownloadLink) {
        this.serviceInvoiceDownloadLink = serviceInvoiceDownloadLink;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<ProductOrderResponse> getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(List<ProductOrderResponse> productStatus) {
        this.productStatus = productStatus;
    }

    public List<ProductInvoice> getProductInvoice() {
        return productInvoice;
    }

    public void setProductInvoice(List<ProductInvoice> productInvoice) {
        this.productInvoice = productInvoice;
    }
}
