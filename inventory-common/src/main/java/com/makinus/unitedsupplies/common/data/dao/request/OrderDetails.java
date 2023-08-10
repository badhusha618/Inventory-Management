/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.request;

/**
 * @author Bad_sha
 */
@Deprecated
public class OrderDetails {

    private String userId;
    private String orderDate;
    private String subTotal;
    private String transportCharges;
    private String serviceCharges;
    private String deliveryCharges;
    private String loadingCharges;
    private String orderTotal;
    private String delAddressRef;
    private String billAddressRef;
    private Boolean isApplyLoading = Boolean.FALSE;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(String transportCharges) {
        this.transportCharges = transportCharges;
    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public String getLoadingCharges() {
        return loadingCharges;
    }

    public void setLoadingCharges(String loadingCharges) {
        this.loadingCharges = loadingCharges;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getDelAddressRef() {
        return delAddressRef;
    }

    public void setDelAddressRef(String delAddressRef) {
        this.delAddressRef = delAddressRef;
    }

    public String getBillAddressRef() {
        return billAddressRef;
    }

    public void setBillAddressRef(String billAddressRef) {
        this.billAddressRef = billAddressRef;
    }

    public Boolean getApplyLoading() {
        return isApplyLoading;
    }

    public void setApplyLoading(Boolean applyLoading) {
        isApplyLoading = applyLoading;
    }
}
