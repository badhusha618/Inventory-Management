/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.paytm.api.reftype;

/**
 * @author Bad_sha
 */
public enum InitTransactionStatus {

    SUCCESS("S", "Success"),
    FAILURE("F", "Failure"),
    SYS_ERROR("U", "System error");

    private String status;
    private String display;

    InitTransactionStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static InitTransactionStatus statusMatch(String status) {
        for (InitTransactionStatus paytmStatus : InitTransactionStatus.values()) {
            if (paytmStatus.getStatus().equalsIgnoreCase(status)) {
                return paytmStatus;
            }
        }
        return FAILURE;
    }
}
