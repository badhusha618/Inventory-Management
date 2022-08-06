/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.service.user;


import com.example.data.request.AdminUserRequest;
import com.example.data.request.ChangePasswordRequest;
import com.example.entity.AdminUser;
import com.example.exception.BazaarException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Bad_sha 
 */
public interface UserService {

    AdminUser saveUser(AdminUser user);

    Optional<AdminUser> validateUsername(String username);

    AdminUser findUserById(Long id) throws BazaarException;

    AdminUser updateUserRole(Long id, AdminUserRequest adminUserRequest) throws BazaarException;

    AdminUser updateFullName(Long id, AdminUserRequest adminUserRequest) throws BazaarException;

    AdminUser updatePassword(AdminUser adminUser, ChangePasswordRequest request) throws BazaarException;

    AdminUser resetPassword(Long id, String password) throws BazaarException;

    AdminUser removeUser(Long id) throws BazaarException;

    AdminUser findUserByUsername(String username) throws BazaarException;

    Optional<AdminUser> findUserByMobile(String mobile);

    List<AdminUser> listActiveUsers();

    List<AdminUser> adminUserList() throws BazaarException;

}
