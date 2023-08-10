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

/**
 * Created by abuabdul
 */
public enum DeliveryCharge {

    DISTANCE_ONE(3, 500, "1 km to 3 km"),
    DISTANCE_TWO(6, 1000, "3 km to 6 km"),
    DISTANCE_THREE(9, 1500, "6 km to 9 km"),
    DISTANCE_FOUR(12, 2000, "9 km to 12 km"),
    DISTANCE_FIVE(15, 2500, "12 km to 15 km");

    private int status;
    private int charge;
    private String display;

    DeliveryCharge(int status, int charge, String display) {
        this.status = status;
        this.charge = charge;
        this.display = display;
    }

    public int getStatus() {
        return status;
    }

    public int getCharge() {
        return charge;
    }

    public String getDisplay() {
        return display;
    }

    public static DeliveryCharge statusMatch(int status) {
        for (DeliveryCharge DeliveryCharge : DeliveryCharge.values()) {
            if (DeliveryCharge.getStatus() == status) {
                return DeliveryCharge;
            }
        }
        return DISTANCE_ONE;
    }
}
