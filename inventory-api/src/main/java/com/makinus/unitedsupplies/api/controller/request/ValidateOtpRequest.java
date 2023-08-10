/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.request;

import io.swagger.annotations.ApiModelProperty;

/** @author sabique */
public class ValidateOtpRequest {

  @ApiModelProperty(notes = "Mobile", example = "9878998999", required = true, position = 1)
  private String mobile;

  @ApiModelProperty(notes = "OTP", example = "1234", required = true, position = 2)
  private String otp;

  @ApiModelProperty(notes = "OTP Type", example = "L or F", required = true, position = 3)
  private String otpType;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public String getOtpType() {
    return otpType;
  }

  public void setOtpType(String otpType) {
    this.otpType = otpType;
  }
}
