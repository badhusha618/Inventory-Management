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
public enum ProdOrderStatus {

    CANCELLED("C", "Cancelled"),
    DELIVERED("D", "Delivered"),
    IN_PROGRESS("I", "In Progress"),
    IN_TRANSIT("T", "In Transit"),
    LOADING("L", "Loading"),
    NEW("N", "New"),
    REFUND("R", "Refund");

    private String status;
    private String display;

    ProdOrderStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static ProdOrderStatus statusMatch(String status) {
        for (ProdOrderStatus order : ProdOrderStatus.values()) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                return order;
            }
        }
        return NEW;
    }

    @Override
    public String toString() {
        return "Order Status values - "
                + NEW.getDisplay() + " "
                + IN_PROGRESS.getDisplay() + " "
                + LOADING.getDisplay() + " "
                + IN_TRANSIT.getDisplay() + " "
                + DELIVERED.getDisplay() + " "
                + CANCELLED.getDisplay() + " "
                + REFUND.getDisplay();
    }
}
