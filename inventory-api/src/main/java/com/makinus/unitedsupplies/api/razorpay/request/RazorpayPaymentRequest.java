/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.razorpay.request;

import java.io.Serializable;

/**
 * @author sabique
 */
public class RazorpayPaymentRequest implements Serializable {

    private String orderID;
    private String orderNo;
    private String razorPaymentID;
    private String razorOrderID;
    private String razorSignature;
    private String amount;
    private String currency;
    private String reason;
    private String description;
    private String step;

    public RazorpayPaymentRequest() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
