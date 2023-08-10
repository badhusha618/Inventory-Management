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

import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface ProductFeaturesViewRepository extends JpaRepository<ProductFeaturesView, Long> {

    @Query("select p from ProductFeaturesView p")
    List<ProductFeaturesView> listAllProductFeaturesView();

    @Query("select p from ProductFeaturesView p where p.id in :productIds")
    List<ProductFeaturesView> listAllProductFeaturesViewByProdIds(@Param("productIds") List<Long> productIds);

    Optional<ProductFeaturesView> findById(Long id);
}
