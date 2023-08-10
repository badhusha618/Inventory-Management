/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

import com.fasterxml.jackson.annotation.JsonFormat;

/** Created by Bad_sha */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServiceType {
  PROFESSIONAL("P", "Professional"),
  SOCIAL("S", "Social"),
  OTHER("O", "Others");

  private String type;
  private String display;

  ServiceType(String type, String display) {
    this.type = type;
    this.display = display;
  }

  public String getType() {
    return type;
  }

  public String getDisplay() {
    return display;
  }

  public static ServiceType statusMatch(String type) {
    for (ServiceType service : ServiceType.values()) {
      if (service.getType().equalsIgnoreCase(type)) {
        return service;
      }
    }
    return PROFESSIONAL;
  }
}
