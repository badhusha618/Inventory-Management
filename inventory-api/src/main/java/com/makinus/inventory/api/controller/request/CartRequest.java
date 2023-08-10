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
public class CartRequest {

    @ApiModelProperty(notes = "Product ID", example = "1", required = true, position = 1)
    private String productId;

    @ApiModelProperty(notes = "Transport Group", example = "MSAND", required = true, position = 2)
    private String transGroup;

    @ApiModelProperty(notes = "Product Vendor ID", example = "1", required = true, position = 3)
    private String productVendorId;

    @ApiModelProperty(notes = "Product Quantity", example = "15", required = true, position = 4)
    private String quantity;

    @ApiModelProperty(notes = "Unit ID", example = "1", required = true, position = 5)
    private String unitId;

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

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "CartRequest{" +
                "productId='" + productId + '\'' +
                ", transGroup='" + transGroup + '\'' +
                ", productVendorId='" + productVendorId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitId='" + unitId + '\'' +
                '}';
    }
}
