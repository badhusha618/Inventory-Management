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

import java.util.List;

/** Created by ibrahim */
public class ProductInvoiceResponse {

  private String orderRef;
  private String orderNo;
  private List<ProductInvoice> productInvoices;

  public ProductInvoiceResponse() {
  }

  public ProductInvoiceResponse(String orderRef, String orderNo, List<ProductInvoice> productInvoices) {
    this.orderRef = orderRef;
    this.orderNo = orderNo;
    this.productInvoices = productInvoices;
  }

  public String getOrderRef() {
    return orderRef;
  }

  public String getOrderNo() { return orderNo; }

  public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

  public void setOrderRef(String orderRef) {
    this.orderRef = orderRef;
  }

  public List<ProductInvoice> getProductInvoices() {
    return productInvoices;
  }

  public void setProductInvoices(List<ProductInvoice> productInvoices) {
    this.productInvoices = productInvoices;
  }
}
