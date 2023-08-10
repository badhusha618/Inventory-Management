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

import com.makinus.unitedsupplies.common.data.entity.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author sabique */
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query("select a from Address a where a.userId = :userId and a.deleted = 'F'")
  List<Address> listAllAddressByUserId(@Param("userId") Long userId);

  @Query("select count(a) from Address a where a.userId = :userId and a.category = :category and a.deleted = 'F'")
  Integer addressCountByUserAndCategory(@Param("userId") Long userId, @Param("category") String category);

  @Query("select a from Address a where a.userId = :userId and a.category = :category and a.deleted = 'F'")
  List<Address> listAllAddressByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);

  @Query("select a from Address a where a.ref = :ref and a.deleted = 'F'")
  Optional<Address> findAddressByRef(@Param("ref") Long ref);

  @Query("select a from Address a where a.userId = :userId and a.category = :category and a.isDefault = 'T' and a.deleted = 'F'")
  Optional<Address> findDefaultAddressByUserAndCategory(@Param("userId") Long userId, @Param("category") String category);

}
