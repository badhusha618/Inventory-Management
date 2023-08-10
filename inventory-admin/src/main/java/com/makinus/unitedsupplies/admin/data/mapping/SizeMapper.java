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

import com.makinus.unitedsupplies.admin.data.forms.SizeForm;
import com.makinus.unitedsupplies.common.data.entity.Size;
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
@Qualifier("SizeMapper")
public class SizeMapper
    implements EntityMapper<SizeForm, Size>,
        EntityUpdateMapper<SizeForm, Size>,
        EntityRemapper<Size, SizeForm> {

  private final Logger LOG = LogManager.getLogger(SizeMapper.class);

  @Override
  public Size map(SizeForm sizeForm) throws UnitedSuppliesException {
    LOG.info("Map Size Form to Size Entity");
    Size size = new Size();
    size.setSize(sizeForm.getSize());
    size.setCategory(Long.valueOf(sizeForm.getCategory()));
    size.setActive(sizeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    size.setCreatedBy(getCurrentUser());
    size.setCreatedDate(getInstant());
    size.setUpdatedBy(getCurrentUser());
    size.setUpdatedDate(getInstant());
    size.setDeleted(YNStatus.NO.getStatus());
    return size;
  }

  @Override
  public Size map(SizeForm sizeForm, Size size) throws UnitedSuppliesException {

    LOG.info("Map Size Form to Updated Size Entity");
    size.setSize(sizeForm.getSize());
    // size.setCategory(Long.valueOf(sizeForm.getCategory()));
    size.setActive(sizeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    size.setUpdatedBy(getCurrentUser());
    size.setUpdatedDate(getInstant());
    size.setDeleted(YNStatus.NO.getStatus());
    return size;
  }

  @Override
  public SizeForm remap(Size size) throws UnitedSuppliesException {
    LOG.info("Map Size Entity to Size Form");
    SizeForm sizeForm = new SizeForm();
    sizeForm.setSizeID(String.valueOf(size.getId()));
    sizeForm.setSize(size.getSize());
    sizeForm.setCategory(String.valueOf(size.getCategory()));
    sizeForm.setActive(size.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return sizeForm;
  }
}
