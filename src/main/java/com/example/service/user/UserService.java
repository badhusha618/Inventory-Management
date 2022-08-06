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
import com.example.exception.BazzarException;
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

    AdminUser findUserById(Long id) throws BazzarException;

    AdminUser updateUserRole(Long id, AdminUserRequest adminUserRequest) throws BazzarException;

    AdminUser updateFullName(Long id, AdminUserRequest adminUserRequest) throws BazzarException;

    AdminUser updatePassword(AdminUser adminUser, ChangePasswordRequest request) throws BazzarException;

    AdminUser resetPassword(Long id, String password) throws BazzarException;

    AdminUser removeUser(Long id) throws BazzarException;

    AdminUser findUserByUsername(String username) throws BazzarException;

    Optional<AdminUser> findUserByMobile(String mobile);

    Page<AdminUser> listActiveUsers(Pageable pageable);

    List<AdminUser> adminUserList() throws BazzarException;

}
