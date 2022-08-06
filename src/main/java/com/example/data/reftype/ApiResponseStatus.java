/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.example.data.reftype;

/**
 * Created by BAD_SHA
 */
public enum ApiResponseStatus {

    SUCCESS("S", "Success"),
    FAILURE("F", "Failure"),
    NOT_FOUND("N", "Item/User Not Found"),
    INCORRECT_PWD("P", "Incorrect Password"),
    EXISTS("E", "Already Exists"),
    INVALID_DATE("D", "Invalid Date Format"),
    INVALID_ACTION("A", "Invalid Action, Not Permitted"),
    IMAGE_IO("O", "Image cannot be read/write");

    private String status;
    private String display;

    ApiResponseStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static ApiResponseStatus statusMatch(String status) {
        for (ApiResponseStatus order : ApiResponseStatus.values()) {
            if (order.getStatus().equalsIgnoreCase(status)) {
                return order;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return "Api Response Status values - " + SUCCESS.getDisplay() + " "
                + NOT_FOUND.getDisplay() + " " + INCORRECT_PWD.getDisplay() + " "
                + EXISTS.getDisplay() + " " + INVALID_DATE.getDisplay()+ " "
                + INVALID_ACTION.getDisplay() + " " + IMAGE_IO.getDisplay();
    }

}