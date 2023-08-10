/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/** Created by abuabdul */
public enum UserRole {
  S_ADMIN("S", "Super Administrator", "ROLE_SUPER_ADMIN"),
  ADMIN("A", "Administrator", "ROLE_ADMIN"),
  MANAGER("M", "Manager", "ROLE_MANAGER"),
  USER("U", "User", "ROLE_USER");

  private String status;
  private String display;
  private String roleName;

  UserRole(String status, String display, String roleName) {
    this.status = status;
    this.display = display;
    this.roleName = roleName;
  }

  public String getStatus() {
    return status;
  }

  public String getDisplay() {
    return display;
  }

  public String getRoleName() {
    return roleName;
  }

  public static UserRole statusMatch(String status) {
    for (UserRole role : UserRole.values()) {
      if (role.getStatus().equalsIgnoreCase(status)) {
        return role;
      }
    }
    return MANAGER;
  }

  public static UserRole statusMatchByName(String roleName) {
    for (UserRole role : UserRole.values()) {
      if (role.getRoleName().equalsIgnoreCase(roleName)) {
        return role;
      }
    }
    return MANAGER;
  }

  @Override
  public String toString() {
    return "UserRole values - "
        + ADMIN.getDisplay()
        + " "
        + S_ADMIN.getDisplay()
        + " "
        + MANAGER.getDisplay()
        + " "
        + USER.getDisplay();
  }
}
