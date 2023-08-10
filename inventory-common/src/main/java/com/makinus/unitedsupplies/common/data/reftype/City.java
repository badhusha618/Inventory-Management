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
public enum City {

    TIRUNELVELI("A", "Tirunelveli");

    private String status;
    private String display;

    City(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static City statusMatch(String status) {
        for (City AddressCategory : City.values()) {
            if (AddressCategory.getStatus().equalsIgnoreCase(status)) {
                return AddressCategory;
            }
        }
        return TIRUNELVELI;
    }

    public static City displayMatch(String display) {
        for (City AddressCategory : City.values()) {
            if (AddressCategory.getDisplay().equalsIgnoreCase(display)) {
                return AddressCategory;
            }
        }
        return TIRUNELVELI;
    }
}
