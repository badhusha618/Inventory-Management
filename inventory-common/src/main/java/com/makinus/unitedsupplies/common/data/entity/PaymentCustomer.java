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
@Table(name = "PAYMENT_CUSTOMER")
public class PaymentCustomer {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "VAN_ID")
    private VirtualAccount virtualAccount;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "UDF1")
    private String udf1;

    @Column(name = "UDF2")
    private String udf2;

    @Column(name = "UDF3")
    private String udf3;

    @Column(name = "UDF4")
    private String udf4;

    @Column(name = "UDF5")
    private String udf5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VirtualAccount getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(VirtualAccount virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentCustomer that = (PaymentCustomer) o;
        return Objects.equals(id, that.id) && Objects.equals(virtualAccount, that.virtualAccount) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(udf1, that.udf1) && Objects.equals(udf2, that.udf2) && Objects.equals(udf3, that.udf3) && Objects.equals(udf4, that.udf4) && Objects.equals(udf5, that.udf5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, virtualAccount, name, email, phone, udf1, udf2, udf3, udf4, udf5);
    }

    @Override
    public String toString() {
        return "PaymentCustomer{" +
                "id=" + id +
                ", virtualAccount=" + virtualAccount +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", udf1='" + udf1 + '\'' +
                ", udf2='" + udf2 + '\'' +
                ", udf3='" + udf3 + '\'' +
                ", udf4='" + udf4 + '\'' +
                ", udf5='" + udf5 + '\'' +
                '}';
    }
}
