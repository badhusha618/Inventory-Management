/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
public class MakinusCryptorImpl implements MakinusCryptor {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public MakinusCryptorImpl(final PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String hashpw(String message) {
    return this.passwordEncoder.encode(message);
  }

  @Override
  public boolean matches(String message, String digest) {
    return this.passwordEncoder.matches(message, digest);
  }
}
