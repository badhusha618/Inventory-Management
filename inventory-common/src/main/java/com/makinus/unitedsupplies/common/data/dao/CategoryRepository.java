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

import com.makinus.unitedsupplies.common.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author abuabdul
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.categoryName = :categoryName and c.deleted = 'F'")
    Category findActiveCategory(@Param("categoryName") String categoryName);

    @Query("select c from Category c where c.deleted = 'F' order by updatedDate desc")
    List<Category> listAllCategories();

    @Query("select c from Category c where c.deleted = 'F' and (c.parentCategory = null or c.parentCategory= '')")
    List<Category> listAllParentCategories();

    @Query("select c from Category c where c.deleted = 'F' and c.active = 'T' and (c.parentCategory = null or c.parentCategory= '')")
    List<Category> listAllActiveParentCategories();

    @Query("select c from Category c where c.deleted = 'F' and c.active = 'T' ")
    List<Category> listAllActiveCategories();

    @Query("select c from Category c where c.parentCategory = :parentCategory and c.deleted = 'F' and c.active = 'T' ")
    List<Category> listCategoriesByParentCategory(@Param("parentCategory") Long parentCategory);

    Optional<Category> findById(Long id);

}
