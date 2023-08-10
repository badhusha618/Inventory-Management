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
 * Created by abuabdul
 */
public enum OrderStatus {

    NEW("N", "New"),
    IN_PROGRESS("I", "In Progress"),
    COMPLETED("C", "Completed");

    private String status;
    private String display;

    OrderStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static OrderStatus statusMatch(String status) {
        for (OrderStatus order : OrderStatus.values()) {
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
                + COMPLETED.getDisplay();
    }
}
