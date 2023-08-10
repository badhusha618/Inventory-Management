/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

/** Created by Bad_sha */
public enum UnitMeasure {
  KILOGRAM("kg", "Kilogram"),
  LITRE("litre", "Litre"),
  BOX("box", "Box"),
  PIECE("piece", "Piece"),
  DOZEN("dozen", "Dozen");

  private String unitMeasure;
  private String display;

  UnitMeasure(String unitMeasure, String display) {
    this.unitMeasure = unitMeasure;
    this.display = display;
  }

  public String getUnitMeasure() {
    return unitMeasure;
  }

  public String getDisplay() {
    return display;
  }

  public static UnitMeasure statusMatch(String unitMeasure) {
    for (UnitMeasure unit : UnitMeasure.values()) {
      if (unit.getUnitMeasure().equalsIgnoreCase(unitMeasure)) {
        return unit;
      }
    }
    return PIECE;
  }

  @Override
  public String toString() {
    return "UnitMeasure values - "
        + KILOGRAM.getDisplay()
        + " "
        + LITRE.getDisplay()
        + " "
        + BOX.getDisplay()
        + " "
        + PIECE.getDisplay()
        + " "
        + DOZEN.getDisplay();
  }
}
