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

import com.makinus.unitedsupplies.common.data.entity.ProductVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface ProductVendorRepository extends JpaRepository<ProductVendor, Long> {

    @Query("select p from ProductVendor p")
    List<ProductVendor> listAllProductVendors();

    @Query("select p from ProductVendor p where p.productId = :prodId and p.deleted = 'F'")
    List<ProductVendor> findByProdId(@Param("prodId") Long prodId);

    @Query("select p from ProductVendor p where p.productId in :productIds and p.deleted = 'F'")
    List<ProductVendor> findByProdIds(@Param("productIds") List<Long> productIds);

    @Query("select max(p.saleRate) from ProductVendor p where p.productId in :productIds and p.deleted = 'F'")
    Double findMaxSaleRateForProducts(@Param("productIds") List<Long> productIds);

    @Query("select p.productId from ProductVendor p where p.saleRate < :maxSaleRate and p.saleRate > :minSaleRate and p.defaultVendor = 'T' and p.deleted = 'F'")
    List<Long> findProductIdsBySaleRatesRange(@Param("maxSaleRate") BigDecimal maxSaleRate, @Param("minSaleRate") BigDecimal minSaleRate);

    @Query("select p from ProductVendor p where p.productId = :prodId and p.defaultVendor = 'T' and p.deleted = 'F'")
    Optional<ProductVendor> findDefaultByProdId(@Param("prodId") Long prodId);

    Optional<ProductVendor> findById(Long id);
}
