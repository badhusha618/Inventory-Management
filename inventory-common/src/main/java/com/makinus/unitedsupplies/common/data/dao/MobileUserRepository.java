/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Bad_sha
 */
public interface MobileUserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.deleted = 'F'")
  List<User> findAllActiveUsers();

  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  Optional<User> findByMobile(String mobile);

  Optional<User> findByMobileAndActiveAndDeleted(String mobile, String active, String deleted);
}
