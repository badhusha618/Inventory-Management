/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/** Created by sabique */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductChargesResponse {

  @ApiModelProperty(notes = "Product Id", example = "1", position = 1)
  private Long productId;

  @ApiModelProperty(notes = "Quantity", example = "5", position = 2)
  private int quantity;

  @ApiModelProperty(notes = "Sale Rate", example = "400", position = 3)
  private int saleRate;

  @ApiModelProperty(notes = "Sub Total", example = "200", position = 4)
  private int subTotal;

  @ApiModelProperty(notes = "Transportation Charge", example = "500", position = 5)
  private int transportationCharge;

  @ApiModelProperty(notes = "Loading Charge, Optional", example = "500", position = 6)
  private int loadingCharge;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getSaleRate() {
    return saleRate;
  }

  public void setSaleRate(int saleRate) {
    this.saleRate = saleRate;
  }

  public int getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(int subTotal) {
    this.subTotal = subTotal;
  }

  public int getTransportationCharge() {
    return transportationCharge;
  }

  public void setTransportationCharge(int transportationCharge) {
    this.transportationCharge = transportationCharge;
  }

  public int getLoadingCharge() {
    return loadingCharge;
  }

  public void setLoadingCharge(int loadingCharge) {
    this.loadingCharge = loadingCharge;
  }
}
