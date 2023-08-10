/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.razorpay.reftype;

/**
 * @author ibrahim
 */
public enum RazorPayPaymentStatus {

    PAYMENT_COMPLETED("S", "Payment Success"),
    PAYMENT_FAILURE("F", "Payment Failure");

    private String status;
    private String display;

    RazorPayPaymentStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static RazorPayPaymentStatus statusMatch(String status) {
        for (RazorPayPaymentStatus PaymentStatus : RazorPayPaymentStatus.values()) {
            if (PaymentStatus.getStatus().equalsIgnoreCase(status)) {
                return PaymentStatus;
            }
        }
        return PAYMENT_FAILURE;
    }

    public static RazorPayPaymentStatus displayMatch(String display) {
        for (RazorPayPaymentStatus paymentStatus : RazorPayPaymentStatus.values()) {
            if (paymentStatus.getDisplay().equalsIgnoreCase(display)) {
                return paymentStatus;
            }
        }
        return PAYMENT_FAILURE;
    }

    @Override
    public String toString() {
        return "RazorPayPaymentStatus Values - "
                + PAYMENT_COMPLETED.getDisplay() + " "
                + PAYMENT_FAILURE.getDisplay();
    }
}