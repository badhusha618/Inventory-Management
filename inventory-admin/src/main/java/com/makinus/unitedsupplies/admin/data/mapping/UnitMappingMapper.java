/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

import com.makinus.unitedsupplies.admin.data.forms.UnitMappingForm;
import com.makinus.unitedsupplies.common.data.entity.UnitMapping;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by abuabdul */
@Component
@Qualifier("UnitMappingMapper")
public class UnitMappingMapper
    implements EntityMapper<UnitMappingForm, UnitMapping>,
        EntityUpdateMapper<UnitMappingForm, UnitMapping>,
        EntityRemapper<UnitMapping, UnitMappingForm> {

  private final Logger LOG = LogManager.getLogger(UnitMappingMapper.class);

  @Override
  public UnitMapping map(UnitMappingForm unitMappingForm) throws UnitedSuppliesException {
    LOG.info("Map UnitMapping Form to UnitMapping Entity");
    UnitMapping unitMapping = new UnitMapping();
    unitMapping.setUnit(Long.valueOf(unitMappingForm.getUnit()));
    unitMapping.setCategory(Long.valueOf(unitMappingForm.getCategory()));
    unitMapping.setActive(
        unitMappingForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    unitMapping.setCreatedBy(getCurrentUser());
    unitMapping.setCreatedDate(getInstant());
    unitMapping.setUpdatedBy(getCurrentUser());
    unitMapping.setUpdatedDate(getInstant());
    unitMapping.setDeleted(YNStatus.NO.getStatus());
    return unitMapping;
  }

  @Override
  public UnitMapping map(UnitMappingForm unitMappingForm, UnitMapping unitMapping)
      throws UnitedSuppliesException {

    LOG.info("Map UnitMapping Form to Updated UnitMapping Entity");
    unitMapping.setUnit(Long.valueOf(unitMappingForm.getUnit()));
    // unitMapping.setCategory(Long.valueOf(unitMappingForm.getCategory()));
    unitMapping.setActive(
        unitMappingForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    unitMapping.setUpdatedBy(getCurrentUser());
    unitMapping.setUpdatedDate(getInstant());
    unitMapping.setDeleted(YNStatus.NO.getStatus());
    return unitMapping;
  }

  @Override
  public UnitMappingForm remap(UnitMapping unitMapping) {
    LOG.info("Map UnitMapping Entity to UnitMapping Form");
    UnitMappingForm unitMappingForm = new UnitMappingForm();
    unitMappingForm.setUnitMappingID(String.valueOf(unitMapping.getId()));
    unitMappingForm.setUnit(String.valueOf(unitMapping.getUnit()));
    unitMappingForm.setCategory(String.valueOf(unitMapping.getCategory()));
    unitMappingForm.setActive(unitMapping.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return unitMappingForm;
  }
}
