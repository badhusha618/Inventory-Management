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

import com.makinus.Inventory.admin.data.forms.TypeForm;
import com.makinus.Inventory.common.data.entity.Type;
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
@Qualifier("TypeMapper")
public class TypeMapper
    implements EntityMapper<TypeForm, Type>,
        EntityUpdateMapper<TypeForm, Type>,
        EntityRemapper<Type, TypeForm> {

  private final Logger LOG = LogManager.getLogger(TypeMapper.class);

  @Override
  public Type map(TypeForm typeForm) throws InventoryException {
    LOG.info("Map Type Form to Type Entity");
    Type type = new Type();
    type.setType(typeForm.getType());
    type.setCategory(Long.valueOf(typeForm.getCategory()));
    type.setActive(typeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    type.setCreatedBy(getCurrentUser());
    type.setCreatedDate(getInstant());
    type.setUpdatedBy(getCurrentUser());
    type.setUpdatedDate(getInstant());
    type.setDeleted(YNStatus.NO.getStatus());
    return type;
  }

  @Override
  public Type map(TypeForm typeForm, Type type) throws InventoryException {

    LOG.info("Map Type Form to Updated Type Entity");
    type.setType(typeForm.getType());
    // type.setCategory(Long.valueOf(typeForm.getCategory()));
    type.setActive(typeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    type.setUpdatedBy(getCurrentUser());
    type.setUpdatedDate(getInstant());
    type.setDeleted(YNStatus.NO.getStatus());
    return type;
  }

  @Override
  public TypeForm remap(Type type) throws InventoryException {
    LOG.info("Map Type Entity to Type Form");
    TypeForm typeForm = new TypeForm();
    typeForm.setTypeID(String.valueOf(type.getId()));
    typeForm.setType(type.getType());
    typeForm.setCategory(String.valueOf(type.getCategory()));
    typeForm.setActive(type.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return typeForm;
  }
}
