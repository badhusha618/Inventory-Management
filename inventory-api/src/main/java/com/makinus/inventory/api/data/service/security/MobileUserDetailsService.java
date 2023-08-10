/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.data.service.security;

import static com.makinus.unitedsupplies.common.data.reftype.UserRole.statusMatch;
import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.NO;
import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.YES;

import com.makinus.unitedsupplies.common.data.dao.MobileUserRepository;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/** Created by Bad_sha */
@Service
@Transactional
public class MobileUserDetailsService implements UserDetailsService {

  private final Logger LOG = LogManager.getLogger(this.getClass());

  @Autowired private MobileUserRepository mobileUserRepository;

  @Override
  public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
    LOG.info("Entering loadUserByUsername -  MobileUserDetailsService");
    Optional<User> userOptional =
        mobileUserRepository.findByMobileAndActiveAndDeleted(
            mobile, YES.getStatus(), NO.getStatus());
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setOtpInvoked(YNStatus.NO.getStatus());
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(statusMatch(user.getRole()).getRoleName()));
      return new UnitedSuppliesUserDetail(
          user.getMobile(), user.getPassword(), authorities, user.getId());
    }
    throw new UsernameNotFoundException("Invalid mobile or password");
  }
}
