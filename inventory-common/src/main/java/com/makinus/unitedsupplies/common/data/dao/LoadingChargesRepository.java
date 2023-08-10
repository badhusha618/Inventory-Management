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

import com.makinus.unitedsupplies.common.data.entity.LoadingCharges;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author abuabdul
 */
public interface LoadingChargesRepository extends JpaRepository<LoadingCharges, Long> {

    @Query("select lc from LoadingCharges lc where lc.deleted = 'F' order by updatedDate desc")
    List<LoadingCharges> listAllLoadingCharges();

    @Query("select lc from LoadingCharges lc where lc.deleted = 'F' and lc.productId in :productIds")
    List<LoadingCharges> listAllLoadingChargesByProducts(@Param("productIds") List<Long> productIds);

    @Query("select new com.makinus.unitedsupplies.common.data.service.Tuple(u.charges, min(u.quantity)) from LoadingCharges u where u.quantity >= :quantity and u.productId = :productId and u.deleted = 'F' group by u.charges")
    Optional<Tuple<BigDecimal, Integer>> listAvailableQuantityForProduct(@Param("quantity") Integer quantity, @Param("productId") Long productId);

    @Query("select u from LoadingCharges u where u.quantity = :quantity and u.productId = :productId and u.deleted = 'F'")
    Optional<LoadingCharges> findAvailableQuantityForProduct(@Param("quantity") Integer quantity, @Param("productId") Long productId);

    Optional<LoadingCharges> findById(Long id);
}
