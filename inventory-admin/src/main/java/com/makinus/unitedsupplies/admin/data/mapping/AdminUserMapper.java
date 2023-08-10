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

import com.makinus.Inventory.admin.data.forms.MaterialUserForm;
import com.makinus.Inventory.admin.encrypt.MakinusCryptor;
import com.makinus.Inventory.common.data.entity.AdminUser;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
@Qualifier("AdminUserMapper")
public class AdminUserMapper
    implements EntityMapper<MaterialUserForm, AdminUser>,
        EntityUpdateMapper<MaterialUserForm, AdminUser> {

  private final Logger LOG = LogManager.getLogger(AdminUserMapper.class);

  @Autowired private MakinusCryptor makinusCryptor;

  @Override
  public AdminUser map(MaterialUserForm materialUserForm) throws InventoryException {
    LOG.info("Map MaterialUserForm Form to Inventory User Entity");
    AdminUser adminUser = new AdminUser();
    adminUser.setFullName(materialUserForm.getFullName());
    adminUser.setEmail(materialUserForm.getEmail());
    adminUser.setMobile(materialUserForm.getMobile());
    adminUser.setUsername(materialUserForm.getUsername());
    adminUser.setPassword(makinusCryptor.hashpw(materialUserForm.getPassword()));
    adminUser.setRole(materialUserForm.getRole());
    adminUser.setCreatedBy(getCurrentUser());
    adminUser.setCreatedDate(getInstant());
    adminUser.setUpdatedBy(getCurrentUser());
    adminUser.setUpdatedDate(getInstant());
    adminUser.setActive(YNStatus.YES.getStatus());
    adminUser.setDeleted(YNStatus.NO.getStatus());
    return adminUser;
  }

  @Override
  public AdminUser map(MaterialUserForm materialUserForm, AdminUser adminUser)
      throws InventoryException {
    LOG.info("Map Inventory User Form to Updated Inventory User Entity");
    adminUser.setPassword(makinusCryptor.hashpw(materialUserForm.getPassword()));
    adminUser.setUpdatedBy(getCurrentUser());
    adminUser.setUpdatedDate(getInstant());
    return adminUser;
  }
}
