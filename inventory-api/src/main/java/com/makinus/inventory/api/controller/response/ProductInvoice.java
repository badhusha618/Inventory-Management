/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

import java.util.Date;

/**
 * @author Bad_sha
 */
public class ProductInvoice {

    private String orderRef;
    private String orderNo;
    private String fulfillmentId;
    private String fulfillmentRef;
    private String invoiceNo;
    private String invoiceDate;
    private String seller;
    private String ProductInvoiceDownloadLink;

    public ProductInvoice() {
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFulfillmentId() {
        return fulfillmentId;
    }

    public void setFulfillmentId(String fulfillmentId) {
        this.fulfillmentId = fulfillmentId;
    }

    public String getFulfillmentRef() {
        return fulfillmentRef;
    }

    public void setFulfillmentRef(String fulfillmentRef) {
        this.fulfillmentRef = fulfillmentRef;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getProductInvoiceDownloadLink() {
        return ProductInvoiceDownloadLink;
    }

    public void setProductInvoiceDownloadLink(String productInvoiceDownloadLink) {
        ProductInvoiceDownloadLink = productInvoiceDownloadLink;
    }
}
