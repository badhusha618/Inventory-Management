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

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

import com.makinus.unitedsupplies.common.data.dao.UnitRepository;
import com.makinus.unitedsupplies.common.data.entity.Unit;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by abuabdul */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

  private final Logger LOG = LogManager.getLogger(UnitServiceImpl.class);

  private final UnitRepository unitRepository;

  public UnitServiceImpl(@Autowired UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  @Override
  public boolean isUnitAvailable(String unitCode) {
    LOG.info("Check if UnitedSupplies Unit is available from database");
    Unit findUnit = unitRepository.findActiveUnit(unitCode);
    return findUnit != null;
  }

  @Override
  public Unit saveUnit(Unit unit) {
    LOG.info("Saving Unit in the database");
    Unit savedUnit = unitRepository.save(unit);
    LOG.info("Saved Unit in the database");
    return savedUnit;
  }

  @Override
  public List<Unit> unitList() {
    LOG.info("List Units from database");
    return unitRepository.listAllUnits();
  }

  @Override
  public List<Unit> listActiveUnits() {
    LOG.info("List Active Units from database");
    return unitRepository.listAllActiveUnits();
  }

  @Override
  public List<Unit> listActiveUnitsByUnitIds(List<Long> ids) {
    LOG.info("List Active Units By ids from database");
    return unitRepository.listAllUnitsByIds(ids);
  }

  @Override
  public Unit updateUnit(final Unit unit) {
    LOG.info("Update existing unit in the catalog");
    return unitRepository.save(unit);
  }

  @Override
  public Unit findUnit(Long id) throws UnitedSuppliesException {
    Optional<Unit> unitOptional = unitRepository.findById(id);
    if (unitOptional.isPresent()) {
      return unitOptional.get();
    }
    throw new UnitedSuppliesException(String.format("Unit is not found with the id %d", id));
  }

  @Override
  public Unit removeUnit(Long id) throws UnitedSuppliesException {
    Optional<Unit> unitOptional = unitRepository.findById(id);
    if (unitOptional.isPresent()) {
      Unit unit = unitOptional.get();
      unit.setDeleted(YNStatus.YES.getStatus());
      unit.setUpdatedBy(getCurrentUser());
      unit.setUpdatedDate(getInstant());
      return unit;
    }
    throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
  }
}
