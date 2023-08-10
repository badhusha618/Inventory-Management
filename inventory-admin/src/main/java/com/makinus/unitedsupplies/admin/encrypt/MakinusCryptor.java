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

/** Created by abuabdul */
public interface MakinusCryptor {

  String hashpw(String message);

  boolean matches(String message, String digest);
}
