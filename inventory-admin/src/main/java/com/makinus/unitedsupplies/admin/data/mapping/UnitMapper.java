/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.mapping;

import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

import com.makinus.Inventory.admin.data.forms.UnitForm;
import com.makinus.Inventory.common.data.entity.Unit;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
@Qualifier("UnitMapper")
public class UnitMapper
    implements EntityMapper<UnitForm, Unit>,
        EntityUpdateMapper<UnitForm, Unit>,
        EntityRemapper<Unit, UnitForm> {

  private final Logger LOG = LogManager.getLogger(UnitMapper.class);

  @Override
  public Unit map(UnitForm unitForm) throws InventoryException {
    LOG.info("Map Unit Form to Unit Entity");
    Unit unit = new Unit();
    unit.setUnitName(unitForm.getUnitName());
    unit.setUnitCode(unitForm.getUnitCode());
    unit.setActive(unitForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    unit.setCreatedBy(getCurrentUser());
    unit.setCreatedDate(getInstant());
    unit.setUpdatedBy(getCurrentUser());
    unit.setUpdatedDate(getInstant());
    unit.setDeleted(YNStatus.NO.getStatus());
    return unit;
  }

  @Override
  public Unit map(UnitForm unitForm, Unit unit) throws InventoryException {

    LOG.info("Map Unit Form to Updated Unit Entity");
    unit.setUnitName(unitForm.getUnitName());
    unit.setUnitCode(unitForm.getUnitCode());
    unit.setActive(unitForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    unit.setUpdatedBy(getCurrentUser());
    unit.setUpdatedDate(getInstant());
    unit.setDeleted(YNStatus.NO.getStatus());
    return unit;
  }

  @Override
  public UnitForm remap(Unit unit) throws InventoryException {
    LOG.info("Map Unit Entity to Unit Form");
    UnitForm unitForm = new UnitForm();
    unitForm.setUnitID(String.valueOf(unit.getId()));
    unitForm.setUnitName(unit.getUnitName());
    unitForm.setUnitCode(String.valueOf(unit.getUnitCode()));
    unitForm.setActive(unit.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return unitForm;
  }
}
