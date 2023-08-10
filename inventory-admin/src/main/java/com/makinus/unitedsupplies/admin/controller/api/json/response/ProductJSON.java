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
public class ProductJSON {

  @ApiModelProperty(notes = "Id of the product")
  private String procode;

  @ApiModelProperty(notes = "Name of the product")
  private String proname;

  @ApiModelProperty(notes = "Sale Price of the product")
  private String proprice;

  @ApiModelProperty(notes = "MRP rate of the product")
  private String originalprice;

  @ApiModelProperty(notes = "Image path of the product")
  private String image;

  @ApiModelProperty(notes = "Description of the product")
  private String description;

  @ApiModelProperty(notes = "Stock status of the product")
  private String stock;

  @ApiModelProperty(notes = "Category of the product")
  private String category;

  public String getProcode() {
    return procode;
  }

  public void setProcode(String procode) {
    this.procode = procode;
  }

  public String getProname() {
    return proname;
  }

  public void setProname(String proname) {
    this.proname = proname;
  }

  public String getProprice() {
    return proprice;
  }

  public void setProprice(String proprice) {
    this.proprice = proprice;
  }

  public String getOriginalprice() {
    return originalprice;
  }

  public void setOriginalprice(String originalprice) {
    this.originalprice = originalprice;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
