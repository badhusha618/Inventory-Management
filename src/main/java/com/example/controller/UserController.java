/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.controller;

import com.example.data.reftype.ApiResponseStatus;
import com.example.data.reftype.YNStatus;
import com.example.data.request.AdminUserRequest;
import com.example.data.response.AdminUserResponse;
import com.example.entity.AdminUser;
import com.example.exception.BazaarException;
import com.example.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.utils.AdminUtils.SYSTEM_USER;
import static com.example.utils.AdminUtils.getInstant;


/**
 * Created by Bad_sha
 */
@RestController
@RequestMapping(value = "/secure/user")
@Api(value = "User API Controller")
public class UserController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

//    @Value("${bazaar.user.default.password}")
//    private String defaultPassword;

    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @ApiOperation("To list all User")
    @GetMapping
    public Iterable<AdminUser> listAllUser() throws BazaarException {
        LOG.info("List All Users {}", this.getClass().getSimpleName());
        return userService.listActiveUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @ApiOperation("To add a User")
    @PostMapping
    public ResponseEntity<AdminUserResponse> addUser(@ApiParam("Add User Request") @RequestBody AdminUserRequest request) {
        LOG.info("Add User Request {}", this.getClass().getSimpleName());
        if (userService.findUserByMobile(request.getMobile()).isPresent()) {
            return new ResponseEntity<>(AdminUserResponse.builder().status(String.valueOf(ApiResponseStatus.EXISTS.getStatus())).build(), HttpStatus.OK);
        }
        AdminUser savedAdminUser = userService.saveUser(adminSaveMap(request));
        return new ResponseEntity<>(mapResponse(savedAdminUser), HttpStatus.OK);
    }

    private AdminUserResponse mapResponse(AdminUser savedAdminUser) {
       return AdminUserResponse.builder().id(String.valueOf(savedAdminUser.getId())).status(String.valueOf(ApiResponseStatus.SUCCESS.getStatus()))
                .userName(savedAdminUser.getUsername()).role(savedAdminUser.getRole()).build();
    }

    private AdminUser adminSaveMap(AdminUserRequest request) {
        return AdminUser.builder().username(request.getUserName()).email(request.getEmail()).fullName(request.getFullName())
                .mobile(request.getMobile()).password(request.getPassword()).role(request.getRole()).deleted(YNStatus.NO.getStatus())
                .active(String.valueOf(YNStatus.YES.getStatus())).createdBy(SYSTEM_USER).createdDate(getInstant()).updatedBy(SYSTEM_USER).updatedDate(getInstant()).build();
    }
//
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
//    @ApiOperation("Get User Detail")
//    @GetMapping("/{id}")
//    public ResponseEntity<AdminUserResponse> getUserById(@ApiParam("User id") @PathVariable("id") Long id) throws BazzarException {
//        LOG.info("Get User details by Id");
//        AdminUser savedAdminUser = userService.findUserById(id);
//        return new ResponseEntity<>(new AdminUserResponse(), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
//    @ApiOperation("To Update a User")
//    @PutMapping("/{id}")
//    public ResponseEntity<AdminUserResponse> updateUser(@ApiParam("User id") @PathVariable("id") Long id, @ApiParam("Update User Request") @RequestBody AdminUserRequest request) throws BazzarException {
//        LOG.info("Add User Request {}", this.getClass().getSimpleName());
//        AdminUser adminUser = userService.findUserById(id);
//        AdminUser savedUser = userService.saveUser(adminUserMapper.map(request, adminUser));
//        return new ResponseEntity<>(new AdminUserResponse(), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_EDITOR')")
//    @ApiOperation("Change User Password")
//    @PutMapping("/change-pwd")
//    public ResponseEntity updatePassword(@ApiParam("Add User Request") @RequestBody ChangePasswordRequest request) throws BazzarException {
//        LOG.info("Update User Current Password");
//        Map<String, String> map = new HashMap<>();
//        BazzarUserDetail adminUserDetail = (BazzarUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        AdminUser activeUser = userService.findUserById(adminUserDetail.getUserId());
//        System.out.println(activeUser.getPassword());
//        if (!passwordEncoder.matches(request.getOldPassword(), activeUser.getPassword())) {
//            LOG.warn("User Current Password does not match");
//            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//        }
//        userService.updatePassword(activeUser, request);
//        return new ResponseEntity<>(new AdminUserResponse(), HttpStatus.OK);
//    }

    /*@ApiOperation("Update Role to a User")
    @PutMapping("/{id}/update-role")
    public ResponseEntity setUserRole(@ApiParam("Id") @PathVariable Long id,
                                      @ApiParam("User Request") @RequestBody AdminUserRequest adminUserRequest) {
        LOG.info("Update User Role - UserController");
        Map<String, String> map = new HashMap<>();
        try {
            userService.updateUserRole(id, adminUserRequest);
        } catch (Exception e) {
            LOG.info("Exception occurs on updating role", e.getMessage());
            map.put(STATUS, ApiResponseStatus.FAILURE.getStatus());
            map.put(MESSAGE, e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }

    @ApiOperation("Update Role to a User")
    @PutMapping("/{id}/update-name")
    public ResponseEntity setUserFullName(@ApiParam("Id") @PathVariable Long id,
                                          @ApiParam("User Request") @RequestBody AdminUserRequest adminUserRequest) {
        LOG.info("Update User Full Name - UserController");
        Map<String, String> map = new HashMap<>();
        try {
            userService.updateFullName(id, adminUserRequest);
        } catch (Exception e) {
            LOG.info("Exception occurs on updating role", e.getMessage());
            map.put(STATUS, ApiResponseStatus.FAILURE.getStatus());
            map.put(MESSAGE, e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }*/

//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
//    @ApiOperation("Update Role to a User")
//    @PutMapping("/{id}/reset-pwd")
//    public ResponseEntity setUserFullName(@ApiParam("Id") @PathVariable Long id) {
//        LOG.info("Reset User Password - UserController");
//        Map<String, String> map = new HashMap<>();
//        try {
//            userService.resetPassword((id), passwordEncoder.encode(defaultPassword));
//        } catch (Exception e) {
//            LOG.info("Exception occurs on resetting password", e.getMessage());
//            map.put(STATUS, ApiResponseStatus.FAILURE.getStatus());
//            map.put(MESSAGE, e.getMessage());
//            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
//    @ApiOperation("Delete User by User Id")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<AdminUserResponse> deleteUser(@ApiParam("User Id") @PathVariable("id") Long userId) {
//        AdminUserResponse response = new AdminUserResponse();
//        try {
//            AdminUser adminUser = userService.removeUser(userId);
//            response.setId(adminUser.getId().toString());
//            response.setStatus(ApiResponseStatus.SUCCESS.getStatus());
//        } catch (Exception e) {
//            LOG.info("Exception occurs on delete user", e.getMessage());
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    //TODO remove if we dont need.
    /*@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @ApiOperation("To check a user name is available")
    @PostMapping(value = "/available")
    public ResponseEntity<Base> isExistingUser(HttpServletRequest request) {
        LOG.info("Checking User Name {}", this.getClass().getSimpleName());
        if (userService.validateUsername(request.getParameter("mobile").trim()).isPresent()) {
            LOG.info("User is Not available");
            return new ResponseEntity<>(new Base(EXISTS.getStatus()), HttpStatus.NOT_FOUND);
        }
        LOG.info("User is available");
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }*/

}