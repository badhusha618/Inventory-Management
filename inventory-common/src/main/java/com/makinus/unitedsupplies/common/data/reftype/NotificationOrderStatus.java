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
 * @author Bad_sha
 */
public enum NotificationOrderStatus {

    NEW("N", "New"),
    IN_PROGRESS("I", "In Progress"),
    SHIPPED("S", "Shipped"),
    IN_TRANSIT("T", "In Transit"),
    DELIVERED("D", "Delivered"),
    CANCELLED("C", "Cancelled"),
    REFUND("R", "Refund");

    private String status;
    private String display;

    NotificationOrderStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static NotificationOrderStatus statusMatch(String status) {
        for (NotificationOrderStatus order : NotificationOrderStatus.values()) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                return order;
            }
        }
        return NEW;
    }

    @Override
    public String toString() {
        return "Order Status values - "
                + NEW.getDisplay()
                + " "
                + IN_PROGRESS.getDisplay()
                + " "
                + SHIPPED.getDisplay()
                + " "
                + IN_TRANSIT.getDisplay()
                + " "
                + DELIVERED.getDisplay()
                + " "
                + CANCELLED.getDisplay()
                + " "
                + REFUND.getDisplay();
    }
}
