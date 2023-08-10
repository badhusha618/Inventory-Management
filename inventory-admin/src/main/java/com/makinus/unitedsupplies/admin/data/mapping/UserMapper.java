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

import com.makinus.Inventory.admin.controller.api.json.request.MobileUser;
import com.makinus.Inventory.admin.encrypt.MakinusCryptor;
import com.makinus.Inventory.common.data.entity.User;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
@Qualifier("UserMapper")
public class UserMapper
    implements EntityMapper<MobileUser, User>, EntityUpdateMapper<MobileUser, User> {

  private final Logger LOG = LogManager.getLogger(UserMapper.class);

  @Autowired private MakinusCryptor makinusCryptor;

  @Override
  public User map(MobileUser addUser) {
    LOG.info("Map Add User to User Entity");
    User user = new User();
    // user.setUsername(user.getUsername());
    // user.setPassword(makinusCryptor.hashpw(user.getPassword()));
    user.setFullName(addUser.getName());
    user.setEmail(addUser.getEmail());
    user.setMobile(addUser.getPhoneNo());
    user.setPassword(makinusCryptor.hashpw(addUser.getSecret()));
    user.setUpdatedBy(getCurrentUser());
    user.setUpdatedDate(getInstant());
    user.setDeleted(YNStatus.NO.getStatus());
    return user;
  }

  @Override
  public User map(MobileUser mobileUser, User user) {
    LOG.info("Map User Form to Updated USM User Entity");
    user.setPassword(makinusCryptor.hashpw(mobileUser.getSecret()));
    user.setUpdatedBy(getCurrentUser());
    user.setUpdatedDate(getInstant());
    return user;
  }
}
