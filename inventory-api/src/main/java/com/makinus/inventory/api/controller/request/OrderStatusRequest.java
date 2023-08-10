/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Bad_sha
 */
public class OrderStatusRequest {

    @ApiModelProperty(notes = "Order ID", example = "79", required = true, position = 1)
    private String orderId;

    @ApiModelProperty(notes = "Order No", example = "US2563579", required = true, position = 2)
    private String orderNo;

    @ApiModelProperty(notes = "Currency", example = "INR", required = true, position = 3)
    private String currency;

    @ApiModelProperty(notes = "Txn Amount", example = "5500.00", required = true, position = 4)
    private String txnAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    @Override
    public String toString() {
        return "OrderStatusRequest{" +
                "orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", currency='" + currency + '\'' +
                ", txnAmount='" + txnAmount + '\'' +
                '}';
    }
}
