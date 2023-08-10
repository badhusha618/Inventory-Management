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

import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import com.makinus.unitedsupplies.common.data.reftype.UserRole;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;

/** Created by abuabdul */
public interface AdminUserService {

  boolean isMaterialUserAvailable(final String username) throws UnitedSuppliesException;

  AdminUser addOrUpdateMaterialUser(final AdminUser user) throws UnitedSuppliesException;

  List<AdminUser> materialUserList() throws UnitedSuppliesException;

  AdminUser updateMaterialUserRole(final Long id, UserRole role) throws UnitedSuppliesException;

  AdminUser removeUser(Long id) throws UnitedSuppliesException;

  AdminUser findActiveUser(String username) throws UnitedSuppliesException;

  AdminUser resetPassword(Long id, String pwdDigest) throws UnitedSuppliesException;

  AdminUser updateFullName(Long id, String name) throws UnitedSuppliesException;

  AdminUser updateMobile(Long id, String mobile) throws UnitedSuppliesException;

  AdminUser updateEmail(Long id, String email) throws UnitedSuppliesException;
}
