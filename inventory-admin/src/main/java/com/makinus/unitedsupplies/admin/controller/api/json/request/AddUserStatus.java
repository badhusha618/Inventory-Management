/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.api.json.request;

/** Created by abuabdul */
public enum AddUserStatus {
  EXISTS("E", "User Already Exists"),
  SUCCESS("S", "User Added Successfully"),
  FAILED("F", "User cannot be added, Failed");

  private String status;
  private String description;

  AddUserStatus(String status, String desc) {
    this.status = status;
    this.description = desc;
  }

  public String getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public static AddUserStatus statusMatch(String status) {
    for (AddUserStatus addUser : AddUserStatus.values()) {
      if (addUser.getStatus().equalsIgnoreCase(status)) {
        return addUser;
      }
    }
    return EXISTS;
  }
}
