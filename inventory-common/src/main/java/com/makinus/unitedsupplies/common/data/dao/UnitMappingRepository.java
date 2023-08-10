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

import com.makinus.unitedsupplies.common.data.entity.UnitMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface UnitMappingRepository extends JpaRepository<UnitMapping, Long> {

    @Query("select u from UnitMapping u where u.deleted = 'F' order by updatedDate desc")
    List<UnitMapping> listAllUnitMappings();

    Optional<UnitMapping> findById(Long id);

    @Query("select u from UnitMapping u where u.category = :categoryId and u.deleted = 'F'")
    List<UnitMapping> listAllUnitMappingByCategory(@Param("categoryId") Long categoryId);

    @Query("select u from UnitMapping u where u.category = :category and u.unit = :unit and u.deleted = 'F'")
    Optional<UnitMapping> findAvailableCategoryForUnitMapping(@Param("unit") Long unit, @Param("category") Long category);
}
