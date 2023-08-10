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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makinus.inventory.api.controller.request.ProductCharges;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Bad_sha
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CheckoutResponse {

    @ApiModelProperty(notes = "Product Price", example = "78788", position = 1)
    private String productPrice;

    @ApiModelProperty(notes = "Service Charge", example = "600", position = 2)
    private String serviceCharge;

    @ApiModelProperty(notes = "Transport Charge", example = "4600", position = 3)
    private String transportCharge;

    @ApiModelProperty(notes = "Loading Charge, Optional", example = "500", position = 4)
    private String loadingCharge;

    @ApiModelProperty(notes = "Total Charge", example = "4000", position = 5)
    private String totalCharge;

    @ApiModelProperty(notes = "List of Checkout Products", example = "[{productId: '1', unitCode: 'Sq.Ft', unitName: 'Square Feet', vendorId: '1', vendorCode: '321', vendorName: 'Abu', pincode: '627005', quantity: '5', saleRate: '400', subTotal: '2000', transportCharge: '500', loadingCharge: '500'}]", position = 6)
    private List<ProductCharges> productCharges;

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTransportCharge() {
        return transportCharge;
    }

    public void setTransportCharge(String transportCharge) {
        this.transportCharge = transportCharge;
    }

    public String getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(String loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public List<ProductCharges> getProductCharges() {
        return productCharges;
    }

    public void setProductCharges(List<ProductCharges> productCharges) {
        this.productCharges = productCharges;
    }
}
