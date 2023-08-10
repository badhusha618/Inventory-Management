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

import com.makinus.unitedsupplies.common.data.entity.Promotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Bad_sha
 */
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

  @Query("select so from Promotion so where so.deleted = 'F' order by updatedDate desc")
  List<Promotion> listAllPromotions();

  @Query("select so from Promotion so where so.active = 'T' and so.deleted = 'F'")
  List<Promotion> listAllActivePromotions();

  Optional<Promotion> findById(Long id);
}
