/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.forms.json;

/** Created by abuabdul */
public class ProductOrderJSON {

  private String orderId;

  private String productName;

  private String proQuantity;

  private String prodSaleRate;

  public ProductOrderJSON() {}

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProQuantity() {
    return proQuantity;
  }

  public void setProQuantity(String proQuantity) {
    this.proQuantity = proQuantity;
  }

  public String getProdSaleRate() {
    return prodSaleRate;
  }

  public void setProdSaleRate(String prodSaleRate) {
    this.prodSaleRate = prodSaleRate;
  }
}
