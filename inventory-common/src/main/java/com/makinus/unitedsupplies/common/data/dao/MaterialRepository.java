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

import com.makinus.unitedsupplies.common.data.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("select q from Material q where q.deleted = 'F' order by updatedDate desc")
    List<Material> listAllMaterials();

    @Query("select q from Material q where q.category = :categoryId and q.deleted = 'F'")
    List<Material> listAllMaterialsByCategory(@Param("categoryId") Long categoryId);

    Optional<Material> findById(Long id);

    @Query("select q from Material q where q.category = :category and q.material = :material and q.deleted = 'F'")
    Optional<Material> findAvailableCategoryForMaterial(@Param("material") String color, @Param("category") Long category);
}
