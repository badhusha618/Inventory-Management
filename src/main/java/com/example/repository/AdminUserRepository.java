/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.repository;

import com.example.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 *  Created by Bad_sha
 */
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findById(Long id);

    Optional<AdminUser> findByUsername(String username);

    Optional<AdminUser> findByMobileAndActiveAndDeleted(String mobile, String active, String deleted);

    Optional<AdminUser> findByUsernameAndActiveAndDeleted(String username, String active, String deleted);

    @Query("select a from AdminUser a where a.deleted = 'F' and a.active = 'T'")
    List<AdminUser> listActiveAdminUser();

    @Query("select a from AdminUser a where a.deleted = 'F' and a.active = 'T' and a.role != 'S'")
    List<AdminUser> listActiveAdminUserWithoutPageable();

}
