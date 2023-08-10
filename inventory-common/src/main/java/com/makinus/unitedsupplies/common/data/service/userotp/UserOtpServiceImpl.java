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

import static java.lang.String.format;

import com.makinus.unitedsupplies.common.data.dao.UserOtpRepository;
import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by sabique */
@Service
@Transactional
public class UserOtpServiceImpl implements UserOtpService {

  private final Logger LOG = LogManager.getLogger(UserOtpServiceImpl.class);

  private final UserOtpRepository userOtpRepository;

  public UserOtpServiceImpl(@Autowired UserOtpRepository userOtpRepository) {
    this.userOtpRepository = userOtpRepository;
  }

  @Override
  public UserOTP saveUserOtp(UserOTP userOtp) {
    LOG.info("Saving OTP in the database");
    UserOTP savedUserOTP = userOtpRepository.save(userOtp);
    LOG.info("Saved OTP Generated in the database");
    return savedUserOTP;
  }

  @Override
  public Optional<UserOTP> findLatestOtpByMobile(String mobile) {
    LOG.info("Get Latest OTP by Mobile from database");
    return userOtpRepository.findLatestOtpByMobile(mobile);
  }

  @Override
  public Optional<UserOTP> findLatestOtpByMobileAndByOtpType(String mobile, String otpType) {
    LOG.info("Get Latest OTP by Mobile from database");
    return userOtpRepository.findLatestOtpByMobileAndByOtpType(mobile, otpType);
  }

  @Override
  public UserOTP removeUserOTP(String mobile, String otpType) throws UnitedSuppliesException {
    Optional<UserOTP> userOTPOptional =
        userOtpRepository.findLatestOtpByMobileAndByOtpType(mobile, otpType);
    if (userOTPOptional.isPresent()) {
      UserOTP userOTP = userOTPOptional.get();
      userOTP.setDeleted(YNStatus.YES.getStatus());
      return userOTP;
    }
    throw new UnitedSuppliesException(format("UserOTP is not found with the mobile %s", mobile));
  }
}
