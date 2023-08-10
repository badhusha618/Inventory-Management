/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.service.excel;

/**
 * @author ammar
 */
public class ProductOrderExcelDTO {

    private String id;
    private String productId;
    private String orderRef;
    private String proQuantity;
    private String productOrderStatus;
    private String vendorNameWithCode;
    private String prodSaleRate;
    private String loadingCharges;
    private String transportCharges;
    private String orderDate;
    private String orderStatus;
    private String orderNo;
    private String productName;
    private String productCode;
    private String totalCharges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getProQuantity() {
        return proQuantity;
    }

    public void setProQuantity(String proQuantity) {
        this.proQuantity = proQuantity;
    }

    public String getProductOrderStatus() {
        return productOrderStatus;
    }

    public void setProductOrderStatus(String productOrderStatus) {
        this.productOrderStatus = productOrderStatus;
    }

    public String getVendorNameWithCode() {
        return vendorNameWithCode;
    }

    public void setVendorNameWithCode(String vendorNameWithCode) {
        this.vendorNameWithCode = vendorNameWithCode;
    }

    public String getProdSaleRate() {
        return prodSaleRate;
    }

    public void setProdSaleRate(String prodSaleRate) {
        this.prodSaleRate = prodSaleRate;
    }

    public String getLoadingCharges() {
        return loadingCharges;
    }

    public void setLoadingCharges(String loadingCharges) {
        this.loadingCharges = loadingCharges;
    }

    public String getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(String transportCharges) {
        this.transportCharges = transportCharges;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(String totalCharges) {
        this.totalCharges = totalCharges;
    }
}
