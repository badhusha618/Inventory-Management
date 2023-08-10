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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/** Created by abuabdul */
@Entity
@Table(name = "USERS")
@JsonIgnoreProperties({
  "password",
  "role",
  "resetPwd",
  "active",
  "createdBy",
  "createdDate",
  "updatedBy",
  "updatedDate",
  "deleted",
  "fileByte",
  "fileName",
  "createdDateAsFolderName"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Base {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "FULL_NAME")
  private String fullName;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "MOBILE")
  private String mobile;

  @ApiModelProperty(hidden = true)
  @Column(name = "ROLE")
  private String role;

  @Column(name = "IMAGE_PATH")
  private String imagePath;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_BY")
  private String createdBy;

  @ApiModelProperty(hidden = true)
  @Column(name = "CREATED_DATE")
  private Date createdDate;

  @ApiModelProperty(hidden = true)
  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @ApiModelProperty(hidden = true)
  @Column(name = "UPDATED_DATE")
  private Date updatedDate;

  @ApiModelProperty(hidden = true)
  @Deprecated
  @Column(name = "RESET_PWD")
  private String otpInvoked;

  @Column(name = "ACTIVE")
  private String active;

  @ApiModelProperty(hidden = true)
  @Column(name = "DELETED")
  private String deleted;

  @ApiModelProperty(hidden = true)
  @Transient
  private byte[] fileByte;

  @ApiModelProperty(hidden = true)
  @Transient
  private String fileName;

  @ApiModelProperty(hidden = true)
  @Transient
  private String createdDateAsFolderName;

  public User() {}

  public User(String status) {
    super(status);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
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

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getOtpInvoked() {
    return otpInvoked;
  }

  public void setOtpInvoked(String otpInvoked) {
    this.otpInvoked = otpInvoked;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }

  public byte[] getFileByte() {
    return fileByte;
  }

  public void setFileByte(byte[] fileByte) {
    this.fileByte = fileByte;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getCreatedDateAsFolderName() {
    return createdDateAsFolderName;
  }

  public void setCreatedDateAsFolderName(String createdDateAsFolderName) {
    this.createdDateAsFolderName = createdDateAsFolderName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(fullName, user.fullName)
        && Objects.equals(password, user.password)
        && Objects.equals(email, user.email)
        && Objects.equals(mobile, user.mobile)
        && Objects.equals(role, user.role)
        && Objects.equals(imagePath, user.imagePath)
        && Objects.equals(createdBy, user.createdBy)
        && Objects.equals(createdDate, user.createdDate)
        && Objects.equals(updatedBy, user.updatedBy)
        && Objects.equals(updatedDate, user.updatedDate)
        && Objects.equals(otpInvoked, user.otpInvoked)
        && Objects.equals(active, user.active)
        && Objects.equals(deleted, user.deleted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(),
        id,
        fullName,
        password,
        email,
        mobile,
        role,
        imagePath,
        createdBy,
        createdDate,
        updatedBy,
        updatedDate,
        otpInvoked,
        active,
        deleted);
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", fullName='"
        + fullName
        + '\''
        + ", password='"
        + password
        + '\''
        + ", email='"
        + email
        + '\''
        + ", mobile='"
        + mobile
        + '\''
        + ", role='"
        + role
        + '\''
        + ", imagePath='"
        + imagePath
        + '\''
        + ", createdBy='"
        + createdBy
        + '\''
        + ", createdDate="
        + createdDate
        + ", updatedBy='"
        + updatedBy
        + '\''
        + ", updatedDate="
        + updatedDate
        + ", resetPwd='"
        + otpInvoked
        + '\''
        + ", active='"
        + active
        + '\''
        + ", deleted='"
        + deleted
        + '\''
        + '}';
  }
}
