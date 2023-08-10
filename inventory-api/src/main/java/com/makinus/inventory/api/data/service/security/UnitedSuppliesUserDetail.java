/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.data.service.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/** Created by Bad_sha */
public class UnitedSuppliesUserDetail extends User {

  private static final long serialVersionUID = 23423L;

  private Long userId;

  public UnitedSuppliesUserDetail(
      String mobile,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      Long userId) {
    super(mobile, password, authorities);
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }
}
