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

import com.makinus.unitedsupplies.common.data.entity.Base;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Bad_sha
 */
public class CheckoutPriceResponse extends Base {

    @ApiModelProperty(notes = "Product Price", example = "7800", position = 1)
    private String productPrice;

    @ApiModelProperty(notes = "Transport Charges", example = "1000", position = 2)
    private String transCharge;

    @ApiModelProperty(notes = "Loading Charges", example = "1000", position = 3)
    private String loadingCharge;

    @ApiModelProperty(notes = "Product Id", example = "Cv-AGG-20515", position = 4)
    private String productId;

    @ApiModelProperty(notes = "Product Vendor Id", example = "001", position = 5)
    private String productVendorId;

    @ApiModelProperty(notes = "Quantity", example = "70", position = 6)
    private String quantity;

    @ApiModelProperty(notes = "Transport group", example = "MSAND", position = 7)
    private String transGroup;


    public CheckoutPriceResponse() {
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTransCharge() {
        return transCharge;
    }

    public void setTransCharge(String transCharge) {
        this.transCharge = transCharge;
    }

    public String getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(String loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductVendorId() {
        return productVendorId;
    }

    public void setProductVendorId(String productVendorId) {
        this.productVendorId = productVendorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }
}
