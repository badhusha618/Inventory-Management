/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INVOICE_SETTINGS")
public class InvoiceSettings {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SEQUENCE_TYPE")
    private String sequenceType;

    @Column(name = "PREFIX")
    private String prefix;

    @Column(name = "SEQUENCE_NO")
    private String sequenceNo;

    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;

    public Long nextReceiptRef() {
        return sequenceNo == null ? 0 : new Long(sequenceNo);
    }

    public InvoiceSettings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceSettings invoiceSettings = (InvoiceSettings) o;
        return id.equals(invoiceSettings.id) &&
                sequenceType.equals(invoiceSettings.sequenceType) &&
                prefix.equals(invoiceSettings.prefix) &&
                sequenceNo.equals(invoiceSettings.sequenceNo) &&
                financialYear.equals(invoiceSettings.financialYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceType, prefix, sequenceNo, financialYear);
    }

    @Override
    public String toString() {
        return "InvoiceSettings{" +
                "id=" + id +
                ", sequenceType='" + sequenceType + '\'' +
                ", prefix='" + prefix + '\'' +
                ", sequenceNo='" + sequenceNo + '\'' +
                ", financialYear='" + financialYear + '\'' +
                '}';
    }
}
