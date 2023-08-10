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
import java.util.List;

/** Created by sabique */
public class CheckoutRequest {

  @ApiModelProperty(notes = "List of Checkout Products", example ="[{productId: '1', productVendorId: '5', quantity: '3', unitId: '1', transGroup: 'MSAND'}, {productId: '21', productVendorId: '4', quantity: '2', unitId: '1', transGroup: 'MSAND'}]", position = 1)
  private List<ProductRequest> products;

  public List<ProductRequest> getProducts() {
    return products;
  }

  public void setProducts(List<ProductRequest> products) {
    this.products = products;
  }

}
