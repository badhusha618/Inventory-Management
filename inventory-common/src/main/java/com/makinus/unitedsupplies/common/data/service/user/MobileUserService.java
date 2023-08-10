/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.user;

import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;
import java.util.Optional;

/** Created by abuabdul */
public interface MobileUserService {

  User saveMobileUser(final User mobileUser);

  User updateMobileUser(User mobileUser);

  List<User> mobileUserList();

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserByPhone(String phone);

  User findById(Long id) throws UnitedSuppliesException;

  User findByMobile(String mobile) throws UnitedSuppliesException;

  User removeUser(Long id) throws UnitedSuppliesException;

  User otpInvoked(Long id) throws UnitedSuppliesException;
}
