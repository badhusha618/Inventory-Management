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

import com.makinus.unitedsupplies.common.data.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/** @author ammar */
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

  @Query("select q from Specification q where q.deleted = 'F' order by updatedDate desc")
  List<Specification> listAllSpecifications();

  @Query("select q from Specification q where q.category = :categoryId and q.deleted = 'F'")
  List<Specification> listAllSpecificationsByCategory(@Param("categoryId") Long categoryId);

  Optional<Specification> findById(Long id);
}
