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

import com.makinus.unitedsupplies.common.data.entity.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface QualityRepository extends JpaRepository<Quality, Long> {

    @Query("select q from Quality q where q.deleted = 'F' order by updatedDate desc")
    List<Quality> listAllQualities();

    @Query("select q from Quality q where q.category = :categoryId and q.deleted = 'F'")
    List<Quality> listAllQualitiesByCategory(@Param("categoryId") Long categoryId);

    Optional<Quality> findById(Long id);

    @Query("select q from Quality q where q.category = :category and q.quality = :quality and q.deleted = 'F'")
    Optional<Quality> findAvailableCategoryForQuality(@Param("quality") String quality, @Param("category") Long category);
}
