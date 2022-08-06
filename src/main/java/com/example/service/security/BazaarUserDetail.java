/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by BAD_SHA
 */
public class BazaarUserDetail extends User {

    private static final long serialVersionUID = 23423L;

    private Long userId;
    private String fullName;
    private String mobile;
    private String role;

    public BazaarUserDetail(String fullName, String mobile, String role, String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);
        this.userId = userId;
        this.fullName = fullName;
        this.mobile = mobile;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRole() {
        return role;
    }
}
