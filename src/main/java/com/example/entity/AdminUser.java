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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Bad_sha
 */
@Entity
@Table(name = "ADMIN_USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "password", "createdBy", "createdDate", "updatedBy", "updatedDate", "deleted" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminUser {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "EMAIL")
    private String email;
    @ApiModelProperty(hidden = true)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    private String role;
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
    @Column(name = "ACTIVE")
    private String active;
    @ApiModelProperty(hidden = true)
    @Column(name = "DELETED")
    private String deleted;

}