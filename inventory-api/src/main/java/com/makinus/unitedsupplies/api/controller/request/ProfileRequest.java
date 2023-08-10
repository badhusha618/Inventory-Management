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

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/** Created by abuabdul */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileRequest {

  @ApiModelProperty(notes = "Full Name, optional", example = "Abubacker", position = 1)
  private String fullName;

  @ApiModelProperty(notes = "Password", example = "123456", required = true, position = 2)
  private String password;

  @ApiModelProperty(notes = "Mobile", example = "9878978890", required = true, position = 3)
  private String mobile;

  @ApiModelProperty(notes = "Email, optional", example = "abu@makinus.com", position = 4)
  private String email;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
