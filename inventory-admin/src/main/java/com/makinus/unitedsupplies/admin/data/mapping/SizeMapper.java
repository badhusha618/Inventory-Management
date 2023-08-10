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

import com.makinus.Inventory.admin.data.forms.SizeForm;
import com.makinus.Inventory.common.data.entity.Size;
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
@Qualifier("SizeMapper")
public class SizeMapper
    implements EntityMapper<SizeForm, Size>,
        EntityUpdateMapper<SizeForm, Size>,
        EntityRemapper<Size, SizeForm> {

  private final Logger LOG = LogManager.getLogger(SizeMapper.class);

  @Override
  public Size map(SizeForm sizeForm) throws InventoryException {
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
  public Size map(SizeForm sizeForm, Size size) throws InventoryException {

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
  public SizeForm remap(Size size) throws InventoryException {
    LOG.info("Map Size Entity to Size Form");
    SizeForm sizeForm = new SizeForm();
    sizeForm.setSizeID(String.valueOf(size.getId()));
    sizeForm.setSize(size.getSize());
    sizeForm.setCategory(String.valueOf(size.getCategory()));
    sizeForm.setActive(size.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return sizeForm;
  }
}
