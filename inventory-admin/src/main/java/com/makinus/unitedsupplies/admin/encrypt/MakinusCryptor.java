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

/** Created by Bad_sha */
public interface MakinusCryptor {

  String hashpw(String message);

  boolean matches(String message, String digest);
}
