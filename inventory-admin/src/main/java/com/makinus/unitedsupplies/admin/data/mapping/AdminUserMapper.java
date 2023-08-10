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

import com.makinus.unitedsupplies.admin.data.forms.MaterialUserForm;
import com.makinus.unitedsupplies.admin.encrypt.MakinusCryptor;
import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by abuabdul */
@Component
@Qualifier("AdminUserMapper")
public class AdminUserMapper
    implements EntityMapper<MaterialUserForm, AdminUser>,
        EntityUpdateMapper<MaterialUserForm, AdminUser> {

  private final Logger LOG = LogManager.getLogger(AdminUserMapper.class);

  @Autowired private MakinusCryptor makinusCryptor;

  @Override
  public AdminUser map(MaterialUserForm materialUserForm) throws UnitedSuppliesException {
    LOG.info("Map MaterialUserForm Form to UnitedSupplies User Entity");
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
      throws UnitedSuppliesException {
    LOG.info("Map UnitedSupplies User Form to Updated UnitedSupplies User Entity");
    adminUser.setPassword(makinusCryptor.hashpw(materialUserForm.getPassword()));
    adminUser.setUpdatedBy(getCurrentUser());
    adminUser.setUpdatedDate(getInstant());
    return adminUser;
  }
}
