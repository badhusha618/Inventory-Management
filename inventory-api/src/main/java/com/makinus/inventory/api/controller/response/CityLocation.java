/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

/** Created by ibrahim */
public class CityLocation {

  private String city;
  private String[] pinCodes;

  public CityLocation() {
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String[] getPinCodes() {
    return pinCodes;
  }

  public void setPinCodes(String[] pinCodes) {
    this.pinCodes = pinCodes;
  }
}
