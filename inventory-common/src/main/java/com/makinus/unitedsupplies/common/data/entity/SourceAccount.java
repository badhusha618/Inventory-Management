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
 * Created by abuabdul
 */
@Entity
@Table(name = "SOURCE_ACCOUNT")
public class SourceAccount {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ORDER_ID")
    private PaymentOrder paymentOrder;

    @Column(name = "MASKED_ACC_NO")
    private String maskedAccountNumber;

    @Column(name = "ACC_HOLDER_NAME")
    private String accountHolderName;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

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

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceAccount that = (SourceAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(paymentOrder, that.paymentOrder) && Objects.equals(maskedAccountNumber, that.maskedAccountNumber) && Objects.equals(accountHolderName, that.accountHolderName) && Objects.equals(ifscCode, that.ifscCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentOrder, maskedAccountNumber, accountHolderName, ifscCode);
    }

    @Override
    public String toString() {
        return "SourceAccount{" +
                "id=" + id +
                ", paymentOrder=" + paymentOrder +
                ", maskedAccountNumber='" + maskedAccountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                '}';
    }
}
