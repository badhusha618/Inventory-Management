/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/** Created by sabique */
@Entity
@Table(name = "USER_OTP")
public class UserOTP extends Base {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "OTP")
  private String otp;

  @Column(name = "MOBILE")
  private String mobile;

  @Column(name = "OTP_TYPE")
  private String otpType;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "CREATED_DATE")
  private Date createdDate;

  @Column(name = "DELETED")
  private String deleted;

  public UserOTP() {}

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getOtpType() {
    return otpType;
  }

  public void setOtpType(String otpType) {
    this.otpType = otpType;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    UserOTP userOtp = (UserOTP) o;
    return Objects.equals(id, userOtp.id)
        && Objects.equals(userId, userOtp.userId)
        && Objects.equals(otp, userOtp.otp)
        && Objects.equals(mobile, userOtp.mobile)
        && Objects.equals(otpType, userOtp.otpType)
        && Objects.equals(createdBy, userOtp.createdBy)
        && Objects.equals(createdDate, userOtp.createdDate)
        && Objects.equals(deleted, userOtp.deleted);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        super.hashCode(), id, userId, otp, mobile, otpType, createdBy, createdDate, deleted);
  }

  @Override
  public String toString() {
    return "UserOTP{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", otp='"
        + otp
        + '\''
        + ", mobile='"
        + mobile
        + '\''
        + ", otpType='"
        + otpType
        + '\''
        + ", createdBy='"
        + createdBy
        + '\''
        + ", createdDate="
        + createdDate
        + ", deleted='"
        + deleted
        + '\''
        + '}';
  }
}
