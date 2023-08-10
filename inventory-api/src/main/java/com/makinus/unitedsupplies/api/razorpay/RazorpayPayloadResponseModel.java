/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.razorpay;

/**
 * Created by sabique
 */
public class RazorpayPayloadResponseModel {

    private String orderNo;
    private String orderId;
    private String key;
    private String amount;
    private String currency;
    private String companyName;
    private String companyLogo;
    private String razorOrderId;
    private Prefill prefill;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRazorOrderId() {
        return razorOrderId;
    }

    public void setRazorOrderId(String razorOrderId) {
        this.razorOrderId = razorOrderId;
    }

    public Prefill getPrefill() {
        return prefill;
    }

    public void setPrefill(Prefill prefill) {
        this.prefill = prefill;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    @Override
    public String toString() {
        return "RazorpayPayloadResponseModel{" +
                "orderNo='" + orderNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", key='" + key + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLogo='" + companyLogo + '\'' +
                ", razorOrderId='" + razorOrderId + '\'' +
                ", prefill=" + prefill +
                '}';
    }
}
