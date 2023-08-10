package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/**
 * Created by abuabdul
 */
public class ProductInvoiceForm implements Serializable {

    private String orderRef;
    private String orderFulfillment;
    private String invoiceNo;
    private String invoiceDate;
    private String invoiceHeader;
    private String invoiceHeaderDisplay;

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderFulfillment() {
        return orderFulfillment;
    }

    public void setOrderFulfillment(String orderFulfillment) {
        this.orderFulfillment = orderFulfillment;
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

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public String getInvoiceHeaderDisplay() {
        return invoiceHeaderDisplay;
    }

    public void setInvoiceHeaderDisplay(String invoiceHeaderDisplay) {
        this.invoiceHeaderDisplay = invoiceHeaderDisplay;
    }
}
