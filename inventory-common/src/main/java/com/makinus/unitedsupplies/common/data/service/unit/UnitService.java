/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.unit;

import com.makinus.unitedsupplies.common.data.entity.Unit;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import java.util.List;

/** Created by Bad_sha */
public interface UnitService {

  boolean isUnitAvailable(final String unitCode);

  Unit saveUnit(final Unit unit);

  List<Unit> unitList();

  List<Unit> listActiveUnits();

  List<Unit> listActiveUnitsByUnitIds(List<Long> ids);

  Unit updateUnit(final Unit unit);

  Unit findUnit(Long id) throws InventoryException;

  Unit removeUnit(Long id) throws InventoryException;
}
