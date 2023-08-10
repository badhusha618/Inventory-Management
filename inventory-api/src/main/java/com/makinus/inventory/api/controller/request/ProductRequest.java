/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Bad_sha
 */

public class ProductRequest {

    @ApiModelProperty(notes = "Product ID", example = "21", required = true, position = 1)
    private String productId;

    @ApiModelProperty(notes = "Product Vendor ID", example = "1", required = true, position = 2)
    private String productVendorId;

    @ApiModelProperty(notes = "Quantity", example = "2", required = true, position = 3)
    private String quantity;

    @ApiModelProperty(notes = "Unit ID", example = "2", required = true, position = 4)
    private String unitId;

    @ApiModelProperty(notes = "Transport group", example = "MSAND", required = true, position = 5)
    private String transGroup;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductVendorId() {
        return productVendorId;
    }

    public void setProductVendorId(String productVendorId) {
        this.productVendorId = productVendorId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }
}
