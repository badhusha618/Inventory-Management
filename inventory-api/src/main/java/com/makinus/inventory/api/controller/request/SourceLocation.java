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

/** Created by Bad_sha */
public class SourceLocation {

  @ApiModelProperty(
      notes = "Source Location Name",
      example = "Tharuvai",
      required = true,
      position = 1)
  private String source;

  @ApiModelProperty(
      notes = "Source Location PinCode",
      example = "21",
      required = true,
      position = 2)
  private String pinCode;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }
}
