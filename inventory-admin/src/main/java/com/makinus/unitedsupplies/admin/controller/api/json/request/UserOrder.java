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
import java.util.List;

/** Created by Bad_sha */
public class UserOrder {

  @ApiModelProperty(notes = "Email to be sent for identifying the user")
  private String email;

  @ApiModelProperty(notes = "Phone No to be sent for identifying the user")
  private String phoneNo;

  @ApiModelProperty(
      notes = "Type to determine email or phoneNo to be used for identifying the user",
      required = true)
  private String type;

  @ApiModelProperty(notes = "Order Sub Total amount", required = true)
  private String subTotal;

  @ApiModelProperty(notes = "Delivery Charges for the order", required = true)
  private String deliveryCharges;

  @ApiModelProperty(notes = "Order Total Amount", required = true)
  private String orderTotal;

  @ApiModelProperty(notes = "Contact number for the user when placing the order", required = true)
  private String contactNo;

  @ApiModelProperty(notes = "Delivery Address for the user when placing the order", required = true)
  private String deliveryAddress;

  @ApiModelProperty(notes = "Ordered Products of the order", required = true)
  private List<OrderedProduct> orderedProducts;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(String subTotal) {
    this.subTotal = subTotal;
  }

  public String getDeliveryCharges() {
    return deliveryCharges;
  }

  public void setDeliveryCharges(String deliveryCharges) {
    this.deliveryCharges = deliveryCharges;
  }

  public String getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(String orderTotal) {
    this.orderTotal = orderTotal;
  }

  public String getContactNo() {
    return contactNo;
  }

  public void setContactNo(String contactNo) {
    this.contactNo = contactNo;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public List<OrderedProduct> getOrderedProducts() {
    return orderedProducts;
  }

  public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
    this.orderedProducts = orderedProducts;
  }
}
