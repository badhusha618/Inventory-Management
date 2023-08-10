/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Created by abuabdul */
public class BCryptEncryptorHelper {

  public static void main(String[] args) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println("username -> " + encoder.encode(args[0]));
    System.out.println("password -> " + encoder.encode(args[1]));
    // System.out.println("management username -> " + encoder.encode(args[2]));
    // System.out.println("management password -> " + encoder.encode(args[3]));
  }
}
