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

import java.math.BigDecimal;

/** Created by sabique */
public class ProductOrderRequest {

  @ApiModelProperty(notes = "Product ID", example = "21", required = true, position = 1)
  private String productId;

  @ApiModelProperty(notes = "Vendor Id", example = "22", required = true, position = 2)
  private String prodVendorId;

  @ApiModelProperty(notes = "Quantity", example = "2", required = true, position = 3)
  private Integer quantity;

  @ApiModelProperty(notes = "Unit Id", example = "1", required = true, position = 4)
  private String unitId;

  @ApiModelProperty(notes = "Sale Rate", example = "20899", required = true, position = 5)
  private BigDecimal saleRate;

  @ApiModelProperty(notes = "Product Transport Charge", example = "4600", required = true, position = 6)
  private BigDecimal transportCharge;

  @ApiModelProperty(notes = "Product Loading Charge", example = "500", required = true, position = 7)
  private BigDecimal loadingCharge;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProdVendorId() {
    return prodVendorId;
  }

  public void setProdVendorId(String prodVendorId) {
    this.prodVendorId = prodVendorId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public BigDecimal getSaleRate() {
    return saleRate;
  }

  public void setSaleRate(BigDecimal saleRate) {
    this.saleRate = saleRate;
  }

  public BigDecimal getTransportCharge() {
    return transportCharge;
  }

  public void setTransportCharge(BigDecimal transportCharge) {
    this.transportCharge = transportCharge;
  }

  public BigDecimal getLoadingCharge() {
    return loadingCharge;
  }

  public void setLoadingCharge(BigDecimal loadingCharge) {
    this.loadingCharge = loadingCharge;
  }

  @Override
  public String toString() {
    return "ProductOrderRequest{" +
            "productId='" + productId + '\'' +
            ", prodVendorId='" + prodVendorId + '\'' +
            ", quantity=" + quantity +
            ", unitId='" + unitId + '\'' +
            ", saleRate=" + saleRate +
            ", transportCharge=" + transportCharge +
            ", loadingCharge=" + loadingCharge +
            '}';
  }
}
