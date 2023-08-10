/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Umar Mukthar
 */
public class CheckoutPriceRequest {

    @ApiModelProperty(notes = "Product Id", example = "7", position = 1)
    private Long productId;

    @ApiModelProperty(notes = "Quantity", example = "10", position = 2)
    private Integer quantity;

    @ApiModelProperty(notes = "Product Vendor Id ", example = "15", position = 3)
    private Long prodVendorId;

    @ApiModelProperty(notes = "Transport group", example = "MSAND", position = 4)
    private String transGroup;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProdVendorId() {
        return prodVendorId;
    }

    public void setProdVendorId(Long prodVendorId) {
        this.prodVendorId = prodVendorId;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }
}
