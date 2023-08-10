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
import org.springframework.web.multipart.MultipartFile;

/** Created by abuabdul */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdateRequest {

  @ApiModelProperty(notes = "Full Name, optional", example = "Abubacker", position = 1)
  private String fullName;

  @ApiModelProperty(
      notes = "Mobile, optional",
      example = "9878978890",
      required = true,
      position = 3)
  private String mobile;

  @ApiModelProperty(notes = "Email, optional", example = "abu@makinus.com", position = 4)
  private String email;

  @ApiModelProperty(notes = "Profile Image, optional", example = "JPG or PNG", position = 5)
  private MultipartFile profileImage;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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

  public MultipartFile getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(MultipartFile profileImage) {
    this.profileImage = profileImage;
  }
}
