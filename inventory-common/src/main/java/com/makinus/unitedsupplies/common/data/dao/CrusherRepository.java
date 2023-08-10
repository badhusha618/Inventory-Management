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
import com.makinus.unitedsupplies.common.data.entity.Crusher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Bad_sha
 */
public interface CrusherRepository extends JpaRepository<Crusher, Long> {

  @Query("select c from Crusher c where c.deleted = 'F' order by updatedDate desc")
  List<Crusher> listAllCrushers();

  @Query("select c from Crusher c where c.category = :categoryId and c.deleted = 'F'")
  List<Crusher> listAllCrushersByCategory(@Param("categoryId") Long categoryId);

  Optional<Crusher> findById(Long id);

  @Query("select c from Crusher c where c.category = :category and c.crusher = :crusher and c.deleted = 'F'")
  Optional<Crusher> findAvailableCategoryForCrusher(@Param("crusher") String crusher, @Param("category") Long category);
}
