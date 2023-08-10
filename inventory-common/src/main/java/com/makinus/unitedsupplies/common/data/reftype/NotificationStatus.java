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
public enum NotificationStatus {

    MSG_SENT("S", "Message Sent"),
    MSG_FAILED("F", "Messaged Failed");

    private String status;
    private String display;

    NotificationStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static NotificationStatus statusMatch(String notificationStatus) {
        for (NotificationStatus status : NotificationStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(notificationStatus)) {
                return status;
            }
        }
        return MSG_FAILED;
    }

    @Override
    public String toString() {
        return "Notification Status values - "
                + MSG_SENT.getDisplay()
                + " "
                + MSG_FAILED.getDisplay();
    }
}
