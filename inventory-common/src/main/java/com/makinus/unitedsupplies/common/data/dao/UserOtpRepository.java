/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author sabique */
public interface UserOtpRepository extends JpaRepository<UserOTP, Long> {

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM USER_OTP u WHERE u.MOBILE = :mobile AND u.DELETED = 'F' AND u.CREATED_DATE >= NOW() - INTERVAL 10 MINUTE ORDER BY u.CREATED_DATE DESC LIMIT 1")
  Optional<UserOTP> findLatestOtpByMobile(@Param("mobile") String mobile);

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM USER_OTP u WHERE u.MOBILE = :mobile AND u.OTP_TYPE = :otpType AND u.DELETED = 'F' AND u.CREATED_DATE >= NOW() - INTERVAL 10 MINUTE ORDER BY u.CREATED_DATE DESC LIMIT 1")
  Optional<UserOTP> findLatestOtpByMobileAndByOtpType(
      @Param("mobile") String mobile, @Param("otpType") String otpType);
}
