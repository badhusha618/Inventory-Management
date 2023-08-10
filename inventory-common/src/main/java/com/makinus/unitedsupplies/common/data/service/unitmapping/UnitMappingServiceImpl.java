/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.unitmapping;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

import com.makinus.unitedsupplies.common.data.dao.UnitMappingRepository;
import com.makinus.unitedsupplies.common.data.entity.Type;
import com.makinus.unitedsupplies.common.data.entity.UnitMapping;
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
public class UnitMappingServiceImpl implements UnitMappingService {

  private final Logger LOG = LogManager.getLogger(UnitMappingServiceImpl.class);

  private final UnitMappingRepository unitMappingRepository;

  public UnitMappingServiceImpl(@Autowired UnitMappingRepository unitMappingRepository) {
    this.unitMappingRepository = unitMappingRepository;
  }

  @Override
  public boolean isUnitMappingExists(Long unit, Long category) {
    LOG.info("Check if UnitedSupplies UnitMapping is available from database");
    Optional<UnitMapping> findUnit = unitMappingRepository.findAvailableCategoryForUnitMapping(unit, category);
    return findUnit.isPresent();
  }

  @Override
  public UnitMapping saveUnitMapping(UnitMapping unitMapping) {
    LOG.info("Saving UnitMapping in the database");
    UnitMapping savedUnitMapping = unitMappingRepository.save(unitMapping);
    LOG.info("Saved UnitMapping in the database");
    return savedUnitMapping;
  }

  @Override
  public List<UnitMapping> unitMappingList() {
    LOG.info("List UnitMappings from database");
    return unitMappingRepository.listAllUnitMappings();
  }

  @Override
  public List<UnitMapping> unitMappingListByCategory(Long categoryId) {
    LOG.info("List Unit Ids By category from database");
    return unitMappingRepository.listAllUnitMappingByCategory(categoryId);
  }

  @Override
  public UnitMapping updateUnitMapping(final UnitMapping unitMapping) {
    LOG.info("Update existing unitMapping in the catalog");
    return unitMappingRepository.save(unitMapping);
  }

  @Override
  public UnitMapping findUnitMapping(Long id) throws UnitedSuppliesException {
    Optional<UnitMapping> unitMappingOptional = unitMappingRepository.findById(id);
    if (unitMappingOptional.isPresent()) {
      return unitMappingOptional.get();
    }
    throw new UnitedSuppliesException(String.format("UnitMapping is not found with the id %d", id));
  }

  @Override
  public UnitMapping removeUnitMapping(Long id) throws UnitedSuppliesException {
    Optional<UnitMapping> unitMappingOptional = unitMappingRepository.findById(id);
    if (unitMappingOptional.isPresent()) {
      UnitMapping unitMapping = unitMappingOptional.get();
      unitMapping.setDeleted(YNStatus.YES.getStatus());
      unitMapping.setUpdatedBy(getCurrentUser());
      unitMapping.setUpdatedDate(getInstant());
      return unitMapping;
    }
    throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
  }
}
