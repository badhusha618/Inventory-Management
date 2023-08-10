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

import com.makinus.unitedsupplies.common.data.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author abuabdul
 */
public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("select c from Crusher c where c.deleted = 'F' order by updatedDate desc")
    List<Color> listAllCrushers();

    @Query("select c from Color c where c.deleted = 'F' order by updatedDate desc")
    List<Color> listAllColors();

    @Query("select c from Color c where c.category = :categoryId and c.deleted = 'F'")
    List<Color> listAllColorsByCategory(@Param("categoryId") Long categoryId);

    Optional<Color> findById(Long id);

    @Query("select c from Color c where c.category = :category and c.color = :color and c.deleted = 'F'")
    Optional<Color> findAvailableCategoryForColor(@Param("color") String color, @Param("category") Long category);


}
