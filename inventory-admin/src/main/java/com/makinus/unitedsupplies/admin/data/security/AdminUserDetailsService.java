/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.security;

import static com.makinus.unitedsupplies.common.data.reftype.UserRole.statusMatch;

import com.makinus.unitedsupplies.admin.data.dao.AdminUserRepository;
import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Created by abuabdul */
@Service
@Transactional
public class AdminUserDetailsService implements UserDetailsService {

  private final Logger LOG = LogManager.getLogger(AdminUserDetailsService.class);

  @Autowired private AdminUserRepository adminUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LOG.info("Entering loadUserByUsername -  AdminUserDetailsService");
    AdminUser user = adminUserRepository.findActiveUser(username);
    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password");
    }
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(statusMatch(user.getRole()).getRoleName()));
    return new AdminUserDetail(user.getUsername(), user.getPassword(), authorities);
  }
}
