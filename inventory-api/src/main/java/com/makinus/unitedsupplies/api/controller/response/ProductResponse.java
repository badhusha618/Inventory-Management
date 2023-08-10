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
import java.util.List;

/** Created by sabique */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

  private String id;
  private String productName;
  private String description;
  private List<String> imagePath;
  private String productPrice;
  private String transportation;
  private String loadingCharge;
  private String serviceCharge;
  private String deliveryCharge;
  private String totalProductAmount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getImagePath() {
    return imagePath;
  }

  public void setImagePath(List<String> imagePath) {
    this.imagePath = imagePath;
  }

  public String getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }

  public String getTransportation() {
    return transportation;
  }

  public void setTransportation(String transportation) {
    this.transportation = transportation;
  }

  public String getLoadingCharge() {
    return loadingCharge;
  }

  public void setLoadingCharge(String loadingCharge) {
    this.loadingCharge = loadingCharge;
  }

  public String getServiceCharge() {
    return serviceCharge;
  }

  public void setServiceCharge(String serviceCharge) {
    this.serviceCharge = serviceCharge;
  }

  public String getDeliveryCharge() {
    return deliveryCharge;
  }

  public void setDeliveryCharge(String deliveryCharge) {
    this.deliveryCharge = deliveryCharge;
  }

  public String getTotalProductAmount() {
    return totalProductAmount;
  }

  public void setTotalProductAmount(String totalProductAmount) {
    this.totalProductAmount = totalProductAmount;
  }
}
