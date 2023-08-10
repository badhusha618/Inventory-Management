/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.userotp;

import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.Optional;

/** Created by Bad_sha */
public interface UserOtpService {

  UserOTP saveUserOtp(final UserOTP userOtp);

  Optional<UserOTP> findLatestOtpByMobile(String mobile);

  Optional<UserOTP> findLatestOtpByMobileAndByOtpType(String mobile, String otpType);

  UserOTP removeUserOTP(String mobile, String otpType) throws InventoryException;
}
