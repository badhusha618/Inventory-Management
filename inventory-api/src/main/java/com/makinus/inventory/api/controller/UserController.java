/* *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 **/

package com.makinus.inventory.api.controller;

import com.makinus.inventory.api.controller.request.AddressRequest;
import com.makinus.inventory.api.controller.request.BillingAddressRequest;
import com.makinus.inventory.api.controller.request.ChangePwdRequest;
import com.makinus.inventory.api.controller.request.ProfileUpdateRequest;
import com.makinus.inventory.api.data.mapping.AddressMapper;
import com.makinus.inventory.api.data.mapping.BillingAddressMapper;
import com.makinus.inventory.api.data.mapping.MobileUserMapper;
import com.makinus.unitedsupplies.common.data.entity.Address;
import com.makinus.unitedsupplies.common.data.entity.Base;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.reftype.AddressCategory;
import com.makinus.unitedsupplies.common.data.reftype.ApiResponseStatus;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author Bad_sha
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "User API Controller")
public class UserController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Value("${us.app.base.url}")
    private String baseUrl;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private MobileUserMapper mobileUserMapper;

    @Autowired
    private BillingAddressMapper billingAddressMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("List of all address for User")
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> listAllAddress(@ApiParam("User Id") @PathVariable("userId") Long userId, @ApiParam("Category") @RequestParam String category) {
        LOG.info("List Address Details - UserController - {}", this.getClass().getSimpleName());
        List<Address> address = addressService.listAllAddressByUserIdAndCategory(userId, category);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Add Address details for User")
    @PostMapping("/{userId}/address/add")
    public ResponseEntity<Address> addAddressDetails(@ApiParam("User Id") @PathVariable("userId") Long userId, @RequestBody AddressRequest addressRequest) {
        LOG.info("Add Address Details - UserController - {}", this.getClass().getSimpleName());
        Address address = addressService.saveAddress(addressMapper.mapExtra(addressRequest, userId));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Update Address details for User")
    @PutMapping("/address/{ref}")
    public ResponseEntity<Address> updateAddressDetails(@ApiParam("Address Ref") @PathVariable Long ref, @ApiParam("Address Details") @RequestBody AddressRequest addressRequest) throws InventoryException {
        LOG.info("Update Address Details - UserController - {}", this.getClass().getSimpleName());
        Address address = addressService.findAddressByRef(ref);
        Address newAddress = new Address();
        BeanUtils.copyProperties(address, newAddress);
        addressService.removeAddress(address.getRef());
        newAddress.setId(null);
        newAddress.setUpdatedBy(getCurrentUser());
        newAddress.setUpdatedDate(getInstant());
        return new ResponseEntity<>(addressService.updateAddress(addressMapper.map(addressRequest, newAddress)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Update Address default status")
    @PutMapping("/address/default/{ref}")
    public ResponseEntity<Address> updateAddressDetails(@ApiParam("Address Ref") @PathVariable Long ref) throws InventoryException {
        LOG.info("Update Address default status - UserController - {}", this.getClass().getSimpleName());
        Address address = addressService.updateDefaultStatus(ref);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Remove Default flag in billing addresses")
    @DeleteMapping(value = "/address/default/{userId}")
    public ResponseEntity<List<Address>> removeDefaultFlagInAddresses(@ApiParam("User Id") @PathVariable("userId") Long userId)
            throws InventoryException {
        LOG.info("Remove default flag in billing address from Database - UserController - {}", this.getClass().getSimpleName());
        List<Address> addressList = addressService.removeDefaultFlagInAllAddress(userId, AddressCategory.BILLING_ADDRESS.getStatus());
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Remove Address details")
    @DeleteMapping(value = "/address/{ref}")
    public ResponseEntity<Base> removeAddressDetails(@ApiParam("Address Ref") @PathVariable Long ref) throws InventoryException {
        LOG.info("Remove Default addresses from Database - UserController");
        addressService.removeAddress(ref);
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Add Billing Address details for User")
    @PostMapping("/{userId}/billing/address/add")
    public ResponseEntity<Address> addBillingAddressDetails(@ApiParam("User Id") @PathVariable("userId") Long userId, @RequestBody BillingAddressRequest billingAddressRequest) {
        LOG.info("Add Billing Address Details - UserController - {}", this.getClass().getSimpleName());
        Address address = addressService.saveAddress(billingAddressMapper.mapExtra(billingAddressRequest, userId));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Update Billing Address details for User")
    @PutMapping("/billing/address/{ref}")
    public ResponseEntity<Address> updateBillingAddressDetails(@ApiParam("Address Ref") @PathVariable Long ref, @ApiParam("Billing Address Details") @RequestBody BillingAddressRequest billingAddressRequest) throws InventoryException {
        LOG.info("Update Billing Address Details - UserController - {}", this.getClass().getSimpleName());
        Address address = addressService.findAddressByRef(ref);
        Address newAddress = new Address();
        BeanUtils.copyProperties(address, newAddress);
        addressService.removeAddress(address.getRef());
        newAddress.setId(null);
        newAddress.setUpdatedBy(getCurrentUser());
        newAddress.setUpdatedDate(getInstant());
        return new ResponseEntity<>(addressService.updateAddress(billingAddressMapper.map(billingAddressRequest, newAddress)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Update User Profile")
    @PutMapping(value = "/{userId}/profile/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateUserProfile(@ApiParam("User Id") @PathVariable("userId") Long userId, @ApiParam("User Profile") ProfileUpdateRequest profileUpdate) throws InventoryException {
        LOG.info("Update User Profile details in the database - UserController - {}", this.getClass().getSimpleName());
        String currentUserMobile = getCurrentUser();
        if (!currentUserMobile.equalsIgnoreCase(profileUpdate.getMobile()) && mobileUserService.findUserByPhone(profileUpdate.getMobile()).isPresent()) {
            return new ResponseEntity<>(new User(ApiResponseStatus.EXISTS.getStatus()), HttpStatus.NOT_FOUND);
        }
        User user = mobileUserService.findById(userId);
        if (profileUpdate.getProfileImage() != null && !profileUpdate.getProfileImage().isEmpty() && StringUtils.isNotEmpty(user.getImagePath())) {
            String[] uriPaths = user.getImagePath().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        User updatedUser = mobileUserService.updateMobileUser(mobileUserMapper.map(profileUpdate, user));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Deprecated
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Get User Image to Display")
    @GetMapping("/{userId}/img")
    public void getProfileImage(HttpServletResponse response, @ApiParam("User Id") @PathVariable("userId") Long userId) throws InventoryException {
        try {
            LOG.info("Get image for user - {}", this.getClass().getSimpleName());
            User user = mobileUserService.findById(userId);
            if (user != null && StringUtils.isNotEmpty(user.getImagePath())) {
                Path path = Paths.get(user.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(user.getFileByte().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%d_%s\"", userId, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(user.getFileByte(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new InventoryException("IO Exception occurred while viewing user profile image " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("Request Change Password to a user")
    @PutMapping("/password/change")
    public ResponseEntity<Base> changePassword(@ApiParam("Change Password Request") @RequestBody ChangePwdRequest changePwdRequest) {
        LOG.info("Change Password Request - UserController - {}", this.getClass().getSimpleName());
        Optional<User> optionalUser = mobileUserService.findUserByPhone(changePwdRequest.getMobile());
        if (!optionalUser.isPresent()) {
            LOG.info("User is not found with the mobile number {}", changePwdRequest.getMobile());
            return new ResponseEntity<>(new Base(ApiResponseStatus.NOT_FOUND.getStatus()), HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(changePwdRequest.getNewPassword()));
        user.setOtpInvoked(YNStatus.NO.getStatus());
        user.setUpdatedBy(user.getMobile());
        user.setUpdatedDate(getInstant());
        mobileUserService.updateMobileUser(user);
        LOG.info("Password has been changed for {}", user.getMobile(), this.getClass().getSimpleName());
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }
}
