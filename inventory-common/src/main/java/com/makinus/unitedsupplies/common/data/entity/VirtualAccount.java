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
@Table(name = "VIRTUAL_ACCOUNT")
public class VirtualAccount {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ORDER_ID")
    private PaymentOrder paymentOrder;

    @Column(name = "VAN")
    private String van;

    @Column(name = "BENEFICIARY_NAME")
    private String beneficiaryName;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "PURPOSE")
    private String purpose;

    @OneToOne(mappedBy = "virtualAccount", cascade = CascadeType.ALL)
    private PaymentCustomer paymentCustomer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getVan() {
        return van;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public PaymentCustomer getPaymentCustomer() {
        return paymentCustomer;
    }

    public void setPaymentCustomer(PaymentCustomer paymentCustomer) {
        this.paymentCustomer = paymentCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualAccount that = (VirtualAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(paymentOrder, that.paymentOrder) && Objects.equals(van, that.van) && Objects.equals(beneficiaryName, that.beneficiaryName) && Objects.equals(ifscCode, that.ifscCode) && Objects.equals(bankName, that.bankName) && Objects.equals(purpose, that.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentOrder, van, beneficiaryName, ifscCode, bankName, purpose);
    }

    @Override
    public String toString() {
        return "VirtualAccount{" +
                "id=" + id +
                ", paymentOrder=" + paymentOrder +
                ", van='" + van + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
