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

import com.makinus.unitedsupplies.common.data.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select b from Brand b where b.category = :categoryId and b.deleted = 'F'")
    List<Brand> listAllBrandsByCategory(@Param("categoryId") Long category);

    @Query("select b from Brand b where b.deleted = 'F' order by updatedDate desc")
    List<Brand> listAllBrands();

    Optional<Brand> findById(Long id);

    @Query("select b from Brand b where b.category = :category and b.brandName = :brandName and b.deleted = 'F'")
    Optional<Brand> findAvailableCategoryForBrand(@Param("brandName") String brandName, @Param("category") Long category);
}
