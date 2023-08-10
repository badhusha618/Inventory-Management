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

import com.makinus.Inventory.admin.data.forms.SpecificationForm;
import com.makinus.Inventory.common.data.entity.Specification;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

/** Created by Bad_sha */
@Component
@Qualifier("SpecificationMapper")
public class SpecificationMapper
    implements EntityMapper<SpecificationForm, Specification>,
        EntityUpdateMapper<SpecificationForm, Specification>,
        EntityRemapper<Specification, SpecificationForm> {

  private final Logger LOG = LogManager.getLogger(SpecificationMapper.class);

  @Override
  public Specification map(SpecificationForm specificationForm) throws InventoryException {
    LOG.info("Map Specification Form to Specification Entity");
    Specification specification = new Specification();
    specification.setSpecification(specificationForm.getSpecification());
    specification.setCategory(Long.valueOf(specificationForm.getCategory()));
    specification.setActive(specificationForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    specification.setCreatedBy(getCurrentUser());
    specification.setCreatedDate(getInstant());
    specification.setUpdatedBy(getCurrentUser());
    specification.setUpdatedDate(getInstant());
    specification.setDeleted(YNStatus.NO.getStatus());
    return specification;
  }

  @Override
  public Specification map(SpecificationForm specificationForm, Specification specification) throws InventoryException {

    LOG.info("Map Specification Form to Updated Specification Entity");
    specification.setSpecification(specificationForm.getSpecification());
    // specification.setCategory(Long.valueOf(specificationForm.getCategory()));
    specification.setActive(specificationForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    specification.setUpdatedBy(getCurrentUser());
    specification.setUpdatedDate(getInstant());
    specification.setDeleted(YNStatus.NO.getStatus());
    return specification;
  }

  @Override
  public SpecificationForm remap(Specification specification) throws InventoryException {
    LOG.info("Map Specification Entity to Specification Form");
    SpecificationForm specificationForm = new SpecificationForm();
    specificationForm.setSpecificationID(String.valueOf(specification.getId()));
    specificationForm.setSpecification(specification.getSpecification());
    specificationForm.setCategory(String.valueOf(specification.getCategory()));
    specificationForm.setActive(specification.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return specificationForm;
  }
}
