package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

public class ServiceInvoiceForm implements Serializable {

    private String orderRef;
    private String orderDiv;
    private String invoiceNo;
    private String invoiceDate;
    private String serviceCharge;
    private String invoiceHeader;
    private String invoiceHeaderDisplay;


    public ServiceInvoiceForm() {
    }

    public ServiceInvoiceForm(String orderRef) {
        this.orderRef = orderRef;
    }

    public ServiceInvoiceForm(String orderRef, String invoiceHeader) {
        this.orderRef = orderRef;
        this.invoiceHeader = invoiceHeader;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderDiv() {
        return orderDiv;
    }

    public void setOrderDiv(String orderDiv) {
        this.orderDiv = orderDiv;
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

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
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
