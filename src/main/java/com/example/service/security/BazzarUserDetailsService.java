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

import com.example.entity.AdminUser;
import com.example.repository.AdminUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.data.reftype.UserRole.statusMatch;
import static com.example.data.reftype.YNStatus.NO;
import static com.example.data.reftype.YNStatus.YES;

/**
 * Created by BAD_SHA
 */
@Service
@Transactional
public class BazzarUserDetailsService implements UserDetailsService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        LOG.info("Entering loadUserByUsername -  DarussalahUserDetailsService");
        Optional<AdminUser> memberOptional = adminUserRepository.findByMobileAndActiveAndDeleted(mobile, YES.getStatus(), NO.getStatus());
        if (memberOptional.isPresent()) {
            AdminUser adminUser = memberOptional.get();
            if (YES.getStatus().equalsIgnoreCase(adminUser.getActive()) && NO.getStatus().equalsIgnoreCase(adminUser.getDeleted())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(statusMatch(adminUser.getRole()).getRoleName()));
                return new BazzarUserDetail(adminUser.getFullName(), adminUser.getMobile(), adminUser.getRole(), adminUser.getMobile(), adminUser.getPassword(), authorities, adminUser.getId());
            }
            throw new UsernameNotFoundException("Invalid User");
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }
}