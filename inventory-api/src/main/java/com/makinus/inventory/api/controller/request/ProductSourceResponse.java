/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/** Created by sabique */
public class ProductSourceResponse {

  @ApiModelProperty(notes = "Product ID", example = "2", required = true, position = 1)
  private long productId;

  @ApiModelProperty(notes = "Selected Source", example = "22", position = 2)
  private String selectedSource;

  @ApiModelProperty(
      notes = "List of Source Locations",
      example =
          "[{source: 'Tharuvai', pinCode: '627005'},{source: 'Tirunelveli', pinCode: '627001'}]",
      required = true,
      position = 3)
  private List<SourceLocation> productSources;

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }

  public List<SourceLocation> getProductSources() {
    return productSources;
  }

  public void setProductSources(List<SourceLocation> productSources) {
    this.productSources = productSources;
  }

  public String getSelectedSource() {
    return selectedSource;
  }

  public void setSelectedSource(String selectedSource) {
    this.selectedSource = selectedSource;
  }
}
