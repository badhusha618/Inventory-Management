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
 * Created by ibrahim
 */
public enum SequenceGenerationType {

    PRODUCT_INVOICE("P", "Product Invoice"),
    SERVICE_INVOICE("S", "Service Invoice");

    private String status;
    private String display;

    SequenceGenerationType(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static SequenceGenerationType statusMatch(String sequenceType) {
        for (SequenceGenerationType type : SequenceGenerationType.values()) {
            if (type.getStatus().equalsIgnoreCase(sequenceType)) {
                return type;
            }
        }
        return SERVICE_INVOICE;
    }

    @Override
    public String toString() {
        return "Sequence Generation Type values - "
                + PRODUCT_INVOICE.getDisplay()
                + " "
                + SERVICE_INVOICE.getDisplay();
    }
}
