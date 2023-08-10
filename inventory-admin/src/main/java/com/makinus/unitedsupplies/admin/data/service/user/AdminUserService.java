/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.service.user;

import com.makinus.Inventory.common.data.entity.AdminUser;
import com.makinus.Inventory.common.data.reftype.UserRole;
import com.makinus.Inventory.common.exception.InventoryException;
import java.util.List;

/** Created by Bad_sha */
public interface AdminUserService {

  boolean isMaterialUserAvailable(final String username) throws InventoryException;

  AdminUser addOrUpdateMaterialUser(final AdminUser user) throws InventoryException;

  List<AdminUser> materialUserList() throws InventoryException;

  AdminUser updateMaterialUserRole(final Long id, UserRole role) throws InventoryException;

  AdminUser removeUser(Long id) throws InventoryException;

  AdminUser findActiveUser(String username) throws InventoryException;

  AdminUser resetPassword(Long id, String pwdDigest) throws InventoryException;

  AdminUser updateFullName(Long id, String name) throws InventoryException;

  AdminUser updateMobile(Long id, String mobile) throws InventoryException;

  AdminUser updateEmail(Long id, String email) throws InventoryException;
}
