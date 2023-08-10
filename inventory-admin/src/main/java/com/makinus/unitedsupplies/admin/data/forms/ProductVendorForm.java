/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;

/**
 * Created by abuabdul
 */
public class ProductVendorForm implements Serializable {

    private String id;
    private String prodId;
    private String vendorId;
    private String mrpRate;
    private String saleRate;
    private String actualRate;
    private String pinCode;
    private boolean defaultVendor;
    private boolean disableVendor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getMrpRate() {
        return mrpRate;
    }

    public void setMrpRate(String mrpRate) {
        this.mrpRate = mrpRate;
    }

    public String getActualRate() {
        return actualRate;
    }

    public void setActualRate(String actualRate) {
        this.actualRate = actualRate;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isDefaultVendor() {
        return defaultVendor;
    }

    public void setDefaultVendor(boolean defaultVendor) {
        this.defaultVendor = defaultVendor;
    }

    public boolean isDisableVendor() {
        return disableVendor;
    }

    public void setDisableVendor(boolean disableVendor) {
        this.disableVendor = disableVendor;
    }
}
