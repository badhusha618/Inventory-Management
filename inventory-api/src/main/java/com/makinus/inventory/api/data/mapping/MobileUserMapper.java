/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.data.mapping;

import com.makinus.inventory.api.controller.request.ProfileRequest;
import com.makinus.inventory.api.controller.request.ProfileUpdateRequest;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.UserRole;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import com.makinus.unitedsupplies.common.file.Writer;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by nizamabdul
 */
@Component
@Qualifier("MobileUserMapper")
public class MobileUserMapper
        implements EntityMapper<ProfileRequest, User>, EntityUpdateMapper<ProfileUpdateRequest, User> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Writer writer;
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public User map(ProfileRequest request) {
        LOG.info("Map SignUpRequest to User Entity");
        User mobileUser = new User();
        mobileUser.setFullName(request.getFullName());
        mobileUser.setMobile(request.getMobile());
        mobileUser.setEmail(request.getEmail());
        mobileUser.setPassword(passwordEncoder.encode(request.getPassword()));
        mobileUser.setRole(UserRole.USER.getStatus());
        mobileUser.setCreatedBy(request.getMobile());
        mobileUser.setCreatedDate(AppUtils.getInstant());
        mobileUser.setUpdatedBy(request.getMobile());
        mobileUser.setUpdatedDate(AppUtils.getInstant());
        mobileUser.setOtpInvoked(YNStatus.NO.getStatus());
        mobileUser.setActive(YNStatus.YES.getStatus());
        mobileUser.setDeleted(YNStatus.NO.getStatus());
        return mobileUser;
    }

    @Override
    public User map(ProfileUpdateRequest profileUpdate, User mobileUser)
            throws InventoryException {
        LOG.info("Map ProfileUpdate to User Entity");
        try {
            mobileUser.setFullName(profileUpdate.getFullName());
            mobileUser.setMobile(profileUpdate.getMobile());
            mobileUser.setEmail(profileUpdate.getEmail());
            if (profileUpdate.getProfileImage() != null && !profileUpdate.getProfileImage().isEmpty()) {
                mobileUser.setFileName(profileUpdate.getProfileImage().getOriginalFilename());
                mobileUser.setFileByte(profileUpdate.getProfileImage().getBytes());
                mobileUser.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replace("/", ""));
                mobileUser.setImagePath(getS3UrlFromAttachment(profileUpdate.getProfileImage(), PROFILE_PREFIX, amazonS3Client));
            }
            mobileUser.setUpdatedBy(profileUpdate.getMobile());
            mobileUser.setUpdatedDate(AppUtils.getInstant());
            return mobileUser;
        } catch (Exception e) {
            LOG.warn("TextArticle Mapper throws exception {}", e.getMessage());
            throw new InventoryException(e.getMessage());
        }
    }

    private String imagePath(User mobileUser) throws InventoryException {
        return writer.writeBytes(
                mobileUser.getFileByte(),
                mobileUser.getCreatedDateAsFolderName(),
                String.valueOf(AppUtils.timestamp()));
    }
}
