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

import com.makinus.unitedsupplies.admin.data.forms.CrusherForm;
import com.makinus.unitedsupplies.common.data.entity.Crusher;
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
@Qualifier("CrusherMapper")
public class CrusherMapper
    implements EntityMapper<CrusherForm, Crusher>,
        EntityUpdateMapper<CrusherForm, Crusher>,
        EntityRemapper<Crusher, CrusherForm> {

  private final Logger LOG = LogManager.getLogger(CrusherMapper.class);

  @Override
  public Crusher map(CrusherForm crusherForm) throws UnitedSuppliesException {
    LOG.info("Map Crusher Form to Crusher Entity");
    Crusher crusher = new Crusher();
    crusher.setCrusher(crusherForm.getCrusher());
    crusher.setCategory(Long.valueOf(crusherForm.getCategory()));
    crusher.setLocation(crusherForm.getLocation());
    crusher.setActive(crusherForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    crusher.setCreatedBy(getCurrentUser());
    crusher.setCreatedDate(getInstant());
    crusher.setUpdatedBy(getCurrentUser());
    crusher.setUpdatedDate(getInstant());
    crusher.setDeleted(YNStatus.NO.getStatus());
    return crusher;
  }

  @Override
  public Crusher map(CrusherForm crusherForm, Crusher crusher) throws UnitedSuppliesException {

    LOG.info("Map Crusher Form to Updated Crusher Entity");
    crusher.setCrusher(crusherForm.getCrusher());
    // crusher.setCategory(Long.valueOf(crusherForm.getCategory()));
    crusher.setLocation(crusherForm.getLocation());
    crusher.setActive(crusherForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    crusher.setUpdatedBy(getCurrentUser());
    crusher.setUpdatedDate(getInstant());
    crusher.setDeleted(YNStatus.NO.getStatus());
    return crusher;
  }

  @Override
  public CrusherForm remap(Crusher crusher) throws UnitedSuppliesException {
    LOG.info("Map Crusher Entity to Crusher Form");
    CrusherForm crusherForm = new CrusherForm();
    crusherForm.setCrusherID(String.valueOf(crusher.getId()));
    crusherForm.setCrusher(crusher.getCrusher());
    crusherForm.setCategory(String.valueOf(crusher.getCategory()));
    crusherForm.setLocation(crusher.getLocation());
    crusherForm.setActive(crusher.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return crusherForm;
  }
}
