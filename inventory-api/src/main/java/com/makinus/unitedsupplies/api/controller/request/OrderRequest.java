/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sabique
 */
public class OrderRequest {

    @ApiModelProperty(notes = "User ID", example = "4", required = true, position = 3)
    private Long userId;

    @ApiModelProperty(
            notes = "User Mobile Number",
            example = "9878987889",
            required = true,
            position = 1)
    private String mobile;

    @ApiModelProperty(notes = "User Email Address", example = "abu@makinus.com", position = 2)
    private String email;

    @ApiModelProperty(notes = "Delivery Address Id", example = "1", required = true, position = 5)
    private String deliveryAddressId;

    @ApiModelProperty(notes = "Billing Address Id", example = "2", position = 6)
    private String billingAddressId;

    @ApiModelProperty(notes = "Product Price", example = "78788", required = true, position = 8)
    private BigDecimal productPrice;

    @ApiModelProperty(notes = "Service Charge", example = "600", required = true, position = 9)
    private BigDecimal serviceCharge;

    @ApiModelProperty(notes = "Transport Charge", example = "4600", required = true, position = 10)
    private BigDecimal transportCharge;

    @ApiModelProperty(notes = "Loading Charge, Optional", example = "500", position = 11)
    private BigDecimal loadingCharge;

    @ApiModelProperty(notes = "Total Payable Charge", example = "55500", required = true, position = 13)
    private BigDecimal totalCharges;

    @ApiModelProperty(
            notes = "List of Order Products",
            example =
                    "[{productId: 1, prodVendorID:1, quantity: 5, unitId: 5, saleRate: 388, transportCharge: 225,loadingCharge: 125},{productId: 1, prodVendorID:1, quantity: 5, unitId: 5, saleRate: 388, transportCharge: 225,loadingCharge: 125}]",
            position = 4)
    private List<ProductOrderRequest> products;

    @ApiModelProperty(notes = "Payment Type", example = "Advance", required = true, position = 14)
    private String paymentType;

    @ApiModelProperty(notes = "Paid Amount", example = "9000", required = true, position = 15)
    private BigDecimal paidAmount;

    @ApiModelProperty(notes = "From cart or Direct Buy", example = "True", required = true, position = 16)
    private boolean buyFromCart;

    @ApiModelProperty(notes = "Payment Provider", example = "R", position = 17)
    private String paymentProvider;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getTransportCharge() {
        return transportCharge;
    }

    public void setTransportCharge(BigDecimal transportCharge) {
        this.transportCharge = transportCharge;
    }

    public BigDecimal getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(BigDecimal loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public BigDecimal getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    public List<ProductOrderRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderRequest> products) {
        this.products = products;
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

    public boolean isBuyFromCart() {
        return buyFromCart;
    }

    public void setBuyFromCart(boolean buyFromCart) {
        this.buyFromCart = buyFromCart;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", deliveryAddressId='" + deliveryAddressId + '\'' +
                ", billingAddressId='" + billingAddressId + '\'' +
                ", productPrice=" + productPrice +
                ", serviceCharge=" + serviceCharge +
                ", transportCharge=" + transportCharge +
                ", loadingCharge=" + loadingCharge +
                ", totalCharges=" + totalCharges +
                ", products=" + products +
                ", paymentType='" + paymentType + '\'' +
                ", paidAmount=" + paidAmount +
                ", buyFromCart=" + buyFromCart +
                ", paymentProvider='" + paymentProvider + '\'' +
                '}';
    }
}
