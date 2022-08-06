package com.example.service.user;

import com.example.data.reftype.UserRole;
import com.example.data.reftype.YNStatus;
import com.example.data.request.AdminUserRequest;
import com.example.data.request.ChangePasswordRequest;
import com.example.entity.AdminUser;
import com.example.exception.BazaarException;
import com.example.repository.AdminUserRepository;
import com.example.utils.AdminUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.utils.AdminUtils.SYSTEM_USER;
import static com.example.utils.AdminUtils.getInstant;
import static java.lang.String.format;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private AdminUserRepository adminUserRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public AdminUser saveUser(AdminUser user) {
        return adminUserRepository.save(user);
    }

    @Override
    public Optional<AdminUser> validateUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    @Override
    public AdminUser findUserById(Long id) throws BazaarException {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(id);
        return adminUserOptional.orElseThrow(() -> new BazaarException(format("User is not found with the id %d", id)));
    }

    @Override
    public AdminUser updateUserRole(Long id, AdminUserRequest adminUserRequest) throws BazaarException {
        LOG.info("Update user role");
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(id);
        if (adminUserOptional.isPresent()) {
            AdminUser adminUser = adminUserOptional.get();
            adminUser.setRole(UserRole.statusMatch(adminUserRequest.getRole()).getRole());
            adminUser.setUpdatedBy(SYSTEM_USER);
            adminUser.setUpdatedDate(getInstant());
            return adminUser;
        }
        throw new BazaarException(format("User is not found with the id %d", id));
    }

    @Override
    public AdminUser updateFullName(Long id, AdminUserRequest adminUserRequest) throws BazaarException {
        LOG.info("Update user full name");
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(id);
        if (adminUserOptional.isPresent()) {
            AdminUser adminUser = adminUserOptional.get();
            adminUser.setFullName(adminUserRequest.getFullName());
            adminUser.setUpdatedBy(AdminUtils.getCurrentUser());
            adminUser.setUpdatedDate(getInstant());
            return adminUser;
        }
        throw new BazaarException(format("User is not found with the id %d", id));
    }

    @Override
    public AdminUser updatePassword(AdminUser adminUser, ChangePasswordRequest request) {
        LOG.info("Update user password");
       // adminUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminUser.setUpdatedBy(AdminUtils.getCurrentUser());
        adminUser.setUpdatedDate(getInstant());
        return adminUser;
    }

    @Override
    public AdminUser resetPassword(Long id, String pwdDigest) throws BazaarException {
        LOG.info("Reset user password");
        Optional<AdminUser> userOptional = adminUserRepository.findById(id);
        if (userOptional.isPresent()) {
            AdminUser adminUser = userOptional.get();
            adminUser.setPassword(pwdDigest);
            adminUser.setUpdatedBy(AdminUtils.getCurrentUser());
            adminUser.setUpdatedDate(getInstant());
            return adminUser;
        }
        throw new BazaarException(format("User is not found with the id %d", id));
    }


    @Override
    public AdminUser findUserByUsername(String username) throws BazaarException {
        LOG.info("Getting Admin User Details from username");
        Optional<AdminUser> optionalAdminUser = adminUserRepository.findByUsername(username);
        return optionalAdminUser.orElseThrow(() -> new BazaarException(format("User is not found with the username %s", username)));
    }

    @Override
    public Optional<AdminUser> findUserByMobile(String mobile) {
        LOG.info("Getting Admin User Details from mobile");
        return adminUserRepository.findByMobileAndActiveAndDeleted(mobile, YNStatus.YES.getStatus(), YNStatus.NO.getStatus());
    }

    @Override
    public List<AdminUser> listActiveUsers() {
        LOG.info("List all active users from database");
        return adminUserRepository.listActiveAdminUser();
    }

    @Override
    public List<AdminUser> adminUserList() {
        LOG.info("List all active users without pagination from database");
        return adminUserRepository.listActiveAdminUserWithoutPageable();
    }

    @Override
    public AdminUser removeUser(Long id) throws BazaarException {
        LOG.info("Remove User Entity");
        Optional<AdminUser> adminUserOptional = adminUserRepository.findById(id);
        if (adminUserOptional.isPresent()) {
            AdminUser adminuser = adminUserOptional.get();
            adminuser.setDeleted(YNStatus.YES.getStatus());
            adminuser.setUpdatedBy(AdminUtils.getCurrentUser());
            adminuser.setUpdatedDate(getInstant());
            return adminuser;
        }
        throw new BazaarException(format("Admin is not found with the id %d", id));
    }
}
