/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.api.json.request;

import io.swagger.annotations.ApiModelProperty;

/** Created by Bad_sha */
public class OrderedProduct {

  @ApiModelProperty(notes = "Ordered Product ID", required = true)
  private String productId;

  @ApiModelProperty(notes = "Ordered Product Quantity", required = true)
  private String productQuantity;

  @ApiModelProperty(notes = "Ordered Product Sale Rate", required = true)
  private String productSaleRate;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(String productQuantity) {
    this.productQuantity = productQuantity;
  }

  public String getProductSaleRate() {
    return productSaleRate;
  }

  public void setProductSaleRate(String productSaleRate) {
    this.productSaleRate = productSaleRate;
  }
}
