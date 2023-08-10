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
public class CategoryJSON {

  @ApiModelProperty(notes = "Id of the category")
  private String id;

  @ApiModelProperty(notes = "Name of the category")
  private String name;

  @ApiModelProperty(notes = "Count of the products under this category")
  private String items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getItems() {
    return items;
  }

  public void setItems(String items) {
    this.items = items;
  }
}
