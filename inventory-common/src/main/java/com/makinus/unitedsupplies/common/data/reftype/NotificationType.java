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
public enum NotificationType {

    SMS("S", "SMS"),
    EMAIL("E", "Email"),
    WHATSAPP("W", "WhatsApp");

    private String status;
    private String display;

    NotificationType(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static NotificationType statusMatch(String notificationType) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getStatus().equalsIgnoreCase(notificationType)) {
                return type;
            }
        }
        return SMS;
    }

    @Override
    public String toString() {
        return "Notification Type values - "
                + SMS.getDisplay()
                + " "
                + EMAIL.getDisplay()
                + " "
                + WHATSAPP.getDisplay();
    }
}
