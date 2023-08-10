/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.service.user;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static java.lang.String.format;

import com.makinus.unitedsupplies.admin.data.dao.AdminUserRepository;
import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import com.makinus.unitedsupplies.common.data.reftype.UserRole;
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
public class AdminUserServiceImpl implements AdminUserService {

  private final Logger LOG = LogManager.getLogger(AdminUserServiceImpl.class);

  private final AdminUserRepository adminUserRepository;

  public AdminUserServiceImpl(@Autowired AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }

  @Override
  public AdminUser addOrUpdateMaterialUser(AdminUser user) {
    LOG.info("Update UnitedSupplies User in the database");
    return adminUserRepository.save(user);
  }

  @Override
  public List<AdminUser> materialUserList() {
    LOG.info("List UnitedSupplies Users from database");
    return adminUserRepository.findAllActiveUsers();
  }

  @Override
  public boolean isMaterialUserAvailable(String username) {
    LOG.info("Check if UnitedSupplies User is available from database");
    AdminUser user = adminUserRepository.findActiveUser(username);
    return user != null;
  }

  @Override
  public AdminUser removeUser(Long id) throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setDeleted(YNStatus.YES.getStatus());
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }

  @Override
  public AdminUser findActiveUser(String username) throws UnitedSuppliesException {
    AdminUser user = adminUserRepository.findActiveUser(username);
    if (user == null) {
      throw new UnitedSuppliesException(format("User is not found with the username %s", username));
    }
    return user;
  }

  @Override
  public AdminUser resetPassword(Long id, String pwdDigest) throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setPassword(pwdDigest);
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }

  @Override
  public AdminUser updateMaterialUserRole(Long id, UserRole newRole)
      throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setRole(newRole.getStatus());
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }

  @Override
  public AdminUser updateFullName(Long id, String name) throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setFullName(name);
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }

  @Override
  public AdminUser updateMobile(Long id, String mobile) throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setMobile(mobile);
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }

  @Override
  public AdminUser updateEmail(Long id, String email) throws UnitedSuppliesException {
    Optional<AdminUser> userOptional = adminUserRepository.findById(id);
    if (userOptional.isPresent()) {
      AdminUser user = userOptional.get();
      user.setEmail(email);
      user.setUpdatedBy(getCurrentUser());
      user.setUpdatedDate(getInstant());
      return user;
    }
    throw new UnitedSuppliesException(format("User is not found with the id %d", id));
  }
}
