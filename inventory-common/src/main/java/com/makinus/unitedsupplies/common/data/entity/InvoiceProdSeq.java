/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Bad_sha
 */
@Entity
@Table(name = "INVOICE_PROD_SEQ")
public class InvoiceProdSeq {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INVOICE_PROD_REF")
    private String invoiceProdRef;

    @Column(name = "VENDOR_ID")
    private Long vendorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceProdRef() {
        return invoiceProdRef;
    }

    public void setInvoiceProdRef(String invoiceProdRef) {
        this.invoiceProdRef = invoiceProdRef;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long nextInvoiceProdRef() {
        return id == null ? 0 : new Long(invoiceProdRef);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceProdSeq that = (InvoiceProdSeq) o;
        return id.equals(that.id) &&
                invoiceProdRef.equals(that.invoiceProdRef) &&
                vendorId.equals(that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceProdRef, vendorId);
    }

    @Override
    public String toString() {
        return "InvoiceProdSeq{" +
                "id=" + id +
                ", invoiceProdRef='" + invoiceProdRef + '\'' +
                ", vendorId=" + vendorId +
                '}';
    }
}
