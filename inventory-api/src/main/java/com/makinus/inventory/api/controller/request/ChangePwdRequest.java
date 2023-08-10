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

/** @author sabique */
public class ChangePwdRequest {

  @ApiModelProperty(notes = "Mobile", example = "9876789999", required = true, position = 1)
  private String mobile;

  @ApiModelProperty(notes = "New Password", example = "123456", required = true, position = 2)
  private String newPassword;

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
