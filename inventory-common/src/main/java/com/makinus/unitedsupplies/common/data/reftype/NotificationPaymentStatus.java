/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/**
 * Created by hussain
 */
public enum NotificationPaymentStatus {

    NEW("N", "Order Placed"),
    PENDING("P", "Pending"),
    FAILED("F", "Failed");

    private String status;
    private String display;

    NotificationPaymentStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static NotificationPaymentStatus statusMatch(String status) {
        for (NotificationPaymentStatus payment : NotificationPaymentStatus.values()) {
            if (payment.getStatus().equalsIgnoreCase(status)) {
                return payment;
            }
        }
        return NEW;
    }

    @Override
    public String toString() {
        return "Payment Status values - "
                + NEW.getDisplay()
                + " "
                + PENDING.getDisplay()
                + " "
                + FAILED.getDisplay();
    }
}
