/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import java.util.List;

/** Created by ibrahim */
public class CityLocationResponse {

  private List<CityLocation> cityLocations;

  public CityLocationResponse() {
  }

  public CityLocationResponse(List<CityLocation> cityLocations) {
    this.cityLocations = cityLocations;
  }

  public List<CityLocation> getCityLocations() {
    return cityLocations;
  }

  public void setCityLocations(List<CityLocation> cityLocations) {
    this.cityLocations = cityLocations;
  }
}
