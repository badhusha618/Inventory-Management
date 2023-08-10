/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.api.json.response;

import io.swagger.annotations.ApiModelProperty;

/** Created by abuabdul */
public class ProdOrderJSON {

  @ApiModelProperty(notes = "Id of the product Order")
  private String id;

  @ApiModelProperty(notes = "Id of the product ordered")
  private String prodId;

  @ApiModelProperty(notes = "Name of the product ordered")
  private String proname;

  @ApiModelProperty(notes = "Quantity of the product ordered")
  private String qty;

  @ApiModelProperty(notes = "Price of the product ordered")
  private String proprice;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProdId() {
    return prodId;
  }

  public void setProdId(String prodId) {
    this.prodId = prodId;
  }

  public String getProname() {
    return proname;
  }

  public void setProname(String proname) {
    this.proname = proname;
  }

  public String getQty() {
    return qty;
  }

  public void setQty(String qty) {
    this.qty = qty;
  }

  public String getProprice() {
    return proprice;
  }

  public void setProprice(String proprice) {
    this.proprice = proprice;
  }
}
