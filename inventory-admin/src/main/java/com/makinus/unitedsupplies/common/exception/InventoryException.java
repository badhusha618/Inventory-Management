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

/**
 * @author Bad_sha
 */
public class InventoryException extends Exception {

  /** Default Serial ID */
  private static final long serialVersionUID = 345456L;

  public InventoryException() {}

  public InventoryException(String message) {
    super(message);
  }

  public InventoryException(Throwable cause) {
    super(cause);
  }

  public InventoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public InventoryException(String message, Error error) {
    super(message, error);
  }
}
