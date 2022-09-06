/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/** Created by ibrahim */
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {

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

  @Column(name = "ROLE")
  private String role;

  @Column(name = "IMAGE_PATH")
  private String imagePath;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "CREATED_DATE")
  private Date createdDate;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "UPDATED_DATE")
  private Date updatedDate;

  @Column(name = "RESET_PWD")
  private String resetPwd;

  @Column(name = "ONBOARDED")
  private String onboard;

  @Column(name = "OTP")
  private Short otp;

  @Column(name = "TOKEN")
  private String token;

  @Column(name = "ACTIVE")
  private String active;

  @Column(name = "DELETED")
  private String deleted;

  @Transient
  private byte[] fileByte;

  @Transient
  private String fileName;

  @Transient
  private String createdDateAsFolderName;

}
