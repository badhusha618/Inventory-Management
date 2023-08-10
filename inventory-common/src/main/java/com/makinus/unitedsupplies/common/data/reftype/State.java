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
import com.fasterxml.jackson.annotation.JsonIgnore;

/** Created by abuabdul */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum State {
  ALABAMA(1, "Alabama"),
  ALASKA(2, "Alaska"),
  ARIZONA(3, "Arizona"),
  ARKANSAS(4, "Arkansas"),
  CALIFORNIA(5, "California"),
  COLORADO(6, "Colorado"),
  CONNECTICUT(7, "Connecticut"),
  DELAWARE(8, "Delaware"),
  FLORIDA(9, "Florida"),
  GEORGIA(10, "Georgia"),
  HAWAII(11, "Hawaii"),
  IDAHO(12, "Idaho"),
  ILLINOIS(13, "Illinois"),
  INDIANA(14, "Indiana"),
  IOWA(15, "Iowa"),
  KANSAS(16, "Kansas"),
  KENTUCKY(17, "Kentucky"),
  LOUISIANA(18, "Louisiana"),
  MAINE(19, "Maine"),
  MARYLAND(20, "Maryland"),
  MASSACHUSETTS(21, "Massachusetts"),
  MICHIGAN(22, "Michigan"),
  MINNESOTA(23, "Minnesota"),
  MISSISSIPPI(24, "Mississippi"),
  MISSOURI(25, "Missouri"),
  MONTANA(26, "Montana"),
  NEBRASKA(27, "Nebraska"),
  NEVADA(28, "Nevada"),
  NEW_HAMPSHIRE(29, "New Hampshire"),
  NEW_JERSEY(30, "New Jersey"),
  NEW_MEXICO(31, "New Mexico"),
  NEW_YORK(32, "New York"),
  NORTH_CAROLINA(33, "North Carolina"),
  NORTH_DAKOTA(34, "North Dakota"),
  OHIO(35, "Ohio"),
  OKLAHOMA(36, "Oklahoma"),
  OREGON(37, "Oregon"),
  PENNSYLVANIA(38, "Pennsylvania"),
  RHODE_ISLAND(39, "Rhode Island"),
  SOUTH_CAROLINA(40, "South Carolina"),
  SOUTH_DAKOTA(41, "South Dakota"),
  TENNESSEE(42, "Tennessee"),
  TEXAS(43, "Texas"),
  UTAH(44, "Utah"),
  VERMONT(45, "Vermont"),
  VIRGINIA(46, "Virginia"),
  WASHINGTON(47, "Washington"),
  WEST_VIRGINIA(48, "West Virginia"),
  WISCONSIN(49, "Wisconsin"),
  WYOMING(50, "Wyoming");

  private int code;
  private String state;

  State(int code, String state) {
    this.code = code;
    this.state = state;
  }

  public int getCode() {
    return code;
  }

  @JsonIgnore
  public String getCodeStr() {
    return String.valueOf(code);
  }

  public String getState() {
    return state;
  }

  public static State statusMatch(int code) {
    for (State state : State.values()) {
      if (state.getCode() == code) {
        return state;
      }
    }
    return ALABAMA;
  }
}
