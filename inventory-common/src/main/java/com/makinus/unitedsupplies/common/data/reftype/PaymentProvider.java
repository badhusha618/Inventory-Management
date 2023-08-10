/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/** Created by ibrahim */
public enum PaymentProvider {

    PAYTM("P", "Paytm"),
    RAZORPAY("R", "Razorpay");

    private String status;
    private String display;

    PaymentProvider(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static PaymentProvider statusMatch(String status) {
        for (PaymentProvider provider : PaymentProvider.values()) {
            if (provider.getStatus().equalsIgnoreCase(status)) {
                return provider;
            }
        }
        return PAYTM;
    }
}
