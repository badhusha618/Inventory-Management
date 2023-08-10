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

import com.makinus.unitedsupplies.common.data.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query("select b from Type b where b.deleted = 'F' order by updatedDate desc")
    List<Type> listAllTypes();

    @Query("select b from Type b where b.category = :categoryId and b.deleted = 'F'")
    List<Type> listAllTypesByCategory(@Param("categoryId") Long categoryId);

    Optional<Type> findById(Long id);

    @Query("select b from Type b where b.category = :category and b.type = :type and b.deleted = 'F'")
    Optional<Type> findAvailableCategoryForType(@Param("type") String type, @Param("category") Long category);
}
