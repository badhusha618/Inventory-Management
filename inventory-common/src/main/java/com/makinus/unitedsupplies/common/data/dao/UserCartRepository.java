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

import com.makinus.unitedsupplies.common.data.entity.UserCart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author sabique */
public interface UserCartRepository extends JpaRepository<UserCart, Long> {

  @Query("select u from UserCart u where u.userId = :userId and u.deleted = 'F'")
  List<UserCart> listAllCartByUserId(@Param("userId") Long userId);

  @Query(
      "select u from UserCart u where u.userId = :userId and u.productId = :productId and u.deleted = 'F'")
  Optional<UserCart> findCartByUserIdAndProdId(
      @Param("userId") Long userId, @Param("productId") Long productId);
}
