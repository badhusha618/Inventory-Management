/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.paytm.api.reftype;

/**
 * Created by abuabdul
 */
public enum TransactionStatus {

    TXN_SUCCESS("TXN_SUCCESS", "Payment Success", "01"),
    PAYMENT_FAILED("TXN_FAILURE", "Payment Declined by Bank", "227"),
    BALANCE_INSUFFICIENT("TXN_FAILURE", "Wallet Balance Insufficient", "235"),
    UPI_INCORRECT("TXN_FAILURE", "UPI ID Incorrect", "295"),
    INVALID_ORDER_ID("TXN_FAILURE", "Invalid Order ID", "334"),
    MID_INCORRECT("TXN_FAILURE", "Merchant ID Incorrect", "335"),
    TXN_UNCONFIRMED("PENDING", "Transaction Not Confirmed", "400"),
    PAYMENT_UNAUTHORIZED("TXN_FAILURE", "Payment Declined by Bank", "401"),
    PAYMENT_INCOMPLETE("PENDING", "Payment Incomplete", "402"),
    SERVER_DOWN("TXN_FAILURE", "Server Down", "501"),
    TXN_FAILED("TXN_FAILURE", "Transaction Failed", "810");

    private String status;
    private String display;
    private String code;

    TransactionStatus(String status, String display, String code) {
        this.status = status;
        this.display = display;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public String getCode() {
        return code;
    }

    public static TransactionStatus statusMatch(String status) {
        for (TransactionStatus responseStatus : TransactionStatus.values()) {
            if (responseStatus.getStatus().equalsIgnoreCase(status)) {
                return responseStatus;
            }
        }
        return TXN_FAILED;
    }

    public static TransactionStatus codeMatch(String code) {
        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.getCode().equalsIgnoreCase(code)) {
                return transactionStatus;
            }
        }
        return TXN_FAILED;
    }

}
