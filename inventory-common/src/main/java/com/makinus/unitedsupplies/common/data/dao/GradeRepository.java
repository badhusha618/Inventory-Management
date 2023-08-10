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

import com.makinus.unitedsupplies.common.data.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("select g from Grade g where g.deleted = 'F' order by updatedDate desc")
    List<Grade> listAllGrades();

    @Query("select g from Grade g where g.category = :categoryId and g.deleted = 'F'")
    List<Grade> listAllGradesByCategory(@Param("categoryId") Long categoryId);

    Optional<Grade> findById(Long id);

    @Query("select g from Grade g where g.category = :category and g.grade = :grade and g.deleted = 'F'")
    Optional<Grade> findAvailableCategoryForGrade(@Param("grade") String grade, @Param("category") Long category);


}
