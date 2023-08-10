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
public enum RazorPayReason {

    PAYMENT_FAILED("payment_failed", "Payment Failed"),
    SIGNATURE_VERIFIED("signature_verified", "Signature Verified"),
    SIGNATURE_VERIFICATION_FAILED("signature_verification_failed", "Signature Verification Failed");

    private String status;
    private String display;

    RazorPayReason(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static RazorPayReason statusMatch(String status) {
        for (RazorPayReason PaymentStatus : RazorPayReason.values()) {
            if (PaymentStatus.getStatus().equalsIgnoreCase(status)) {
                return PaymentStatus;
            }
        }
        return PAYMENT_FAILED;
    }

    public static RazorPayReason displayMatch(String display) {
        for (RazorPayReason paymentStatus : RazorPayReason.values()) {
            if (paymentStatus.getDisplay().equalsIgnoreCase(display)) {
                return paymentStatus;
            }
        }
        return PAYMENT_FAILED;
    }

    @Override
    public String toString() {
        return "RazorPayReason Values - "
                + PAYMENT_FAILED.getDisplay() + " "
                + SIGNATURE_VERIFIED.getDisplay() + " "
                + SIGNATURE_VERIFICATION_FAILED.getDisplay();
    }
}