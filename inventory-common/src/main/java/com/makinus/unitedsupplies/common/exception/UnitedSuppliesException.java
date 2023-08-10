/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.exception;

/** @author abuabdul */
public class UnitedSuppliesException extends Exception {

  /** Default Serial ID */
  private static final long serialVersionUID = 345456L;

  public UnitedSuppliesException() {}

  public UnitedSuppliesException(String message) {
    super(message);
  }

  public UnitedSuppliesException(Throwable cause) {
    super(cause);
  }

  public UnitedSuppliesException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnitedSuppliesException(String message, Error error) {
    super(message, error);
  }
}
