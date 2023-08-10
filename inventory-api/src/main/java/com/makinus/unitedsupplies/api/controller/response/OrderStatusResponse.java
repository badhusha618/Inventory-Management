/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sabique
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderStatusResponse {

    @ApiModelProperty(notes = "Order ID", example = "79", required = true, position = 1)
    private String orderId;

    @ApiModelProperty(notes = "Order No", example = "US2563579", required = true, position = 2)
    private String orderNo;

    @ApiModelProperty(
            notes = "Transaction ID",
            example = "202005081112128XXXXXX68470101509706",
            required = true,
            position = 3)
    private String txnId;

    @ApiModelProperty(notes = "Paytm Response Code", example = "01", required = true, position = 4)
    private String responseCode;

    @ApiModelProperty(
            notes = "Paytm Response Status",
            example = "Payment Success",
            required = true,
            position = 5)
    private String responseStatus;

    @ApiModelProperty(
            notes = "Paytm Response Msg",
            example = "Txn Success",
            required = true,
            position = 6)
    private String responseMsg;

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

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "OrderStatusResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", txnId='" + txnId + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseStatus='" + responseStatus + '\'' +
                ", responseMsg='" + responseMsg + '\'' +
                '}';
    }
}
