/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.form;

import java.io.Serializable;

/**
 * Created by abuabdul
 */
public class ProductOrderFilterForm implements Serializable {

    private String productCode;
    private String productName;
    private String vendorCode;
    private String status;
    private String fromOrderDate;
    private String toOrderDate;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromOrderDate() {
        return fromOrderDate;
    }

    public void setFromOrderDate(String fromOrderDate) {
        this.fromOrderDate = fromOrderDate;
    }

    public String getToOrderDate() {
        return toOrderDate;
    }

    public void setToOrderDate(String toOrderDate) {
        this.toOrderDate = toOrderDate;
    }
}
