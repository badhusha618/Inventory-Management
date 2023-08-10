/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.dao;

import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author abuabdul */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

  @Query("select muser from AdminUser muser where muser.username = :username")
  AdminUser findByUsername(@Param("username") String username);

  @Query(
      "select muser from AdminUser muser where muser.username = :username and muser.deleted = 'F'")
  AdminUser findActiveUser(@Param("username") String username);

  @Query(
      "select muser from AdminUser muser where muser.role != 'S' and muser.deleted = 'F' order by updatedDate desc")
  List<AdminUser> findAllActiveUsers();

  Optional<AdminUser> findById(Long id);
}
