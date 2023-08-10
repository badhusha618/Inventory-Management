/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.user;

import com.makinus.unitedsupplies.common.data.dao.MobileUserRepository;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;

/**
 * Created by abuabdul
 */
@Service
@Transactional
public class MobileUserServiceImpl implements MobileUserService {

    private final Logger LOG = LogManager.getLogger(MobileUserServiceImpl.class);

    private final MobileUserRepository mobileUserRepository;
    private final ImageWriter imageWriter;

    public MobileUserServiceImpl(
            @Autowired MobileUserRepository mobileUserRepository, @Autowired ImageWriter imageWriter) {
        this.mobileUserRepository = mobileUserRepository;
        this.imageWriter = imageWriter;
    }

    @Override
    public User saveMobileUser(User mobileUser) {
        LOG.info("Saving Mobile User in the database");
        User savedMobileUser = mobileUserRepository.save(mobileUser);
        LOG.info("Saved Mobile User in the database");
        return savedMobileUser;
    }

    @Override
    public User updateMobileUser(User mobileUser) {
        LOG.info("Update Mobile User in the database");
        return mobileUserRepository.save(mobileUser);
    }

    @Override
    public List<User> mobileUserList() {
        LOG.info("List Mobile Users from database");
        return mobileUserRepository.findAllActiveUsers();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        LOG.info("Get Mobile Users By Email from database");
        return mobileUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByPhone(String phone) {
        LOG.info("Get Mobile Users By Phone from database");
        return mobileUserRepository.findByMobile(phone);
    }

    @Override
    public User findById(Long id) throws UnitedSuppliesException {
        Optional<User> userOptional = mobileUserRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
//      if (StringUtils.isNotEmpty(user.getImagePath())) {
//        user.setFileByte(imageWriter.readImage(get(user.getImagePath())));
//      }
            return user;
        }
        throw new UnitedSuppliesException(format("Mobile User is not found with the id %d", id));
    }

    @Override
    public User findByMobile(String mobile) throws UnitedSuppliesException {
        Optional<User> userOptional = mobileUserRepository.findByMobile(mobile);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UnitedSuppliesException(
                format("Mobile User is not found with the username %s", mobile));
    }

    @Override
    public User removeUser(Long id) throws UnitedSuppliesException {
        Optional<User> userOptional = mobileUserRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleted(YNStatus.YES.getStatus());
            user.setUpdatedBy(getCurrentUser());
            user.setUpdatedDate(getInstant());
            return user;
        }
        throw new UnitedSuppliesException(format("Mobile User is not found with the id %d", id));
    }

    public User otpInvoked(Long id) throws UnitedSuppliesException {
        Optional<User> userOptional = mobileUserRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOtpInvoked(YNStatus.YES.getStatus());
            user.setUpdatedBy(user.getMobile());
            user.setUpdatedDate(getInstant());
            return user;
        }
        throw new UnitedSuppliesException(format("Mobile User is not found with the id %d", id));
    }
}
