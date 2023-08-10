/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.sms.strategy;

/** @author abuabdul */
public class NotificationException extends Exception {

  private String exceptionType;

  /** Default Serial ID */
  private static final long serialVersionUID = 345456L;

  public NotificationException() {}

  public NotificationException(String message) {
    super(message);
  }

  public NotificationException(String message, String type) {
    super(message);
    this.exceptionType = type;
  }

  public NotificationException(Throwable cause) {
    super(cause);
  }

  public NotificationException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotificationException(String message, Error error) {
    super(message, error);
  }

  public String getExceptionType() {
    return exceptionType;
  }

  public void setExceptionType(String exceptionType) {
    this.exceptionType = exceptionType;
  }
}
