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

import com.makinus.unitedsupplies.common.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.deleted = 'F' order by updatedDate desc")
    List<Product> findAllActiveProducts();

    @Query("select p from Product p where p.id in (:productIds) and p.deleted = 'F'")
    List<Product> findAllActiveProductsByIds(@Param("productIds") List<Long> productIds);

    Optional<Product> findById(Long id);

    @Query("select p from Product p where p.productCode = :productCode and p.deleted = 'F'")
    Product findActiveProduct(@Param("productCode") String productCode);

    @Query("select p from Product p where p.subCategory = :subCategory and p.deleted = 'F' and p.active = 'T'")
    List<Product> findActiveProductsBySubCategory(@Param("subCategory") Long subCategory);

    @Query("select p from Product p where p.parentCategory = :parentCategory and p.deleted = 'F' and p.active = 'T'")
    List<Product> findActiveProductsByCategory(@Param("parentCategory") Long parentCategory);

    @Query("select p from Product p where p.productName like %:searchQuery% and p.deleted = 'F'")
    List<Product> findProductsBySearchQuery(@Param("searchQuery") String searchQuery);

    @Query("select p.id from Product p where p.subCategory = :categoryId or p.parentCategory = :categoryId and p.deleted = 'F'")
    List<Long> findProductIdsByCategory(@Param("categoryId") Long categoryId);

    @Query("select p.transGroup from Product p where p.id in (:productIds) and p.deleted = 'F'")
    List<String> transportGroupListByProductList(@Param("productIds") List<Long> productIds);

}
