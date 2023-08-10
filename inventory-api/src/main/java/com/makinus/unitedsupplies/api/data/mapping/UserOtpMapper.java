/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by sabique */
@Component
@Qualifier("UserOtpMapper")
public class UserOtpMapper
    implements EntityWithExtraValueMapper<User, Tuple<String, String>, UserOTP> {

  private final Logger LOG = LogManager.getLogger(this.getClass());

  @Override
  public UserOTP mapExtra(User user, Tuple<String, String> otpAndType) {
    LOG.info("Map UserOTPRequest to User Entity");
    UserOTP userOtp = new UserOTP();
    userOtp.setUserId(user.getId());
    userOtp.setMobile(user.getMobile());
    userOtp.setOtp(otpAndType.getA());
    userOtp.setOtpType(otpAndType.getB());
    userOtp.setCreatedBy(user.getMobile());
    userOtp.setCreatedDate(AppUtils.getInstant());
    userOtp.setDeleted(YNStatus.NO.getStatus());
    return userOtp;
  }
}
