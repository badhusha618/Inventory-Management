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

import com.makinus.unitedsupplies.common.data.entity.ProductChargeHistory;
import com.makinus.unitedsupplies.common.data.entity.ProductVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Shahul
 */
public interface ProductChargeHistoryRepository extends JpaRepository<ProductChargeHistory, Long> {

    @Query("select p from ProductChargeHistory p")
    List<ProductChargeHistory> listAllProductChargeHistory();

    @Query("select p from ProductChargeHistory p where p.productId = :prodId and p.vendorId = :vendorId ")
    List<ProductChargeHistory> findByVendorAndProd(@Param("prodId") Long prodId,@Param("vendorId") Long vendorId);

    @Query("select p from ProductChargeHistory p where p.productId = :prodId")
    List<ProductChargeHistory> findByProd(@Param("prodId") Long prodId);
}
