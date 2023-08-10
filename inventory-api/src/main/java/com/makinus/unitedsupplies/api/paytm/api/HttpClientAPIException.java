/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.paytm.api;

/** @author abuabdul */
public class HttpClientAPIException extends Exception {

  private String exceptionType;

  /** Default Serial ID */
  private static final long serialVersionUID = 345456L;

  public HttpClientAPIException() {}

  public HttpClientAPIException(String message) {
    super(message);
  }

  public HttpClientAPIException(String message, String type) {
    super(message);
    this.exceptionType = type;
  }

  public HttpClientAPIException(Throwable cause) {
    super(cause);
  }

  public HttpClientAPIException(String message, Throwable cause) {
    super(message, cause);
  }

  public HttpClientAPIException(String message, Error error) {
    super(message, error);
  }

  public String getExceptionType() {
    return exceptionType;
  }

  public void setExceptionType(String exceptionType) {
    this.exceptionType = exceptionType;
  }
}
