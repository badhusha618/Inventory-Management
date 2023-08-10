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
public enum RazorPayStep {

    PAYMENT_AUTHORIZATION("payment_authorization", "Payment Authorization"),
    SIGNATURE_VERIFICATION("signature_verification", "Signature Verified");

    private String status;
    private String display;

    RazorPayStep(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static RazorPayStep statusMatch(String status) {
        for (RazorPayStep PaymentStatus : RazorPayStep.values()) {
            if (PaymentStatus.getStatus().equalsIgnoreCase(status)) {
                return PaymentStatus;
            }
        }
        return PAYMENT_AUTHORIZATION;
    }

    public static RazorPayStep displayMatch(String display) {
        for (RazorPayStep paymentStatus : RazorPayStep.values()) {
            if (paymentStatus.getDisplay().equalsIgnoreCase(display)) {
                return paymentStatus;
            }
        }
        return PAYMENT_AUTHORIZATION;
    }

    @Override
    public String toString() {
        return "RazorPayStep Values - "
                + PAYMENT_AUTHORIZATION.getDisplay() + " "
                + SIGNATURE_VERIFICATION.getDisplay();
    }
}