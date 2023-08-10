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
import io.swagger.annotations.ApiModelProperty;

/** Created by abuabdul */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutUsResponse {

  @ApiModelProperty(
      notes = "About Us Introduction",
      example =
          "UnitedSupplies is an ecommerce application for site engineers, contracts, builders to purchase building materials without any broker intervention.",
      position = 1)
  private String description;

  @ApiModelProperty(notes = "App Version", example = "v1.0.0", position = 2)
  private String version;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
