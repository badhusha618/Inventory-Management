/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;

/** Created by Bad_sha */
public class LoginForm implements Serializable {

  private String username;
  private String password;
  private boolean remember_me;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isRemember_me() {
    return remember_me;
  }

  public void setRemember_me(boolean remember_me) {
    this.remember_me = remember_me;
  }
}
