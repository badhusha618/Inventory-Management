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

import com.makinus.unitedsupplies.common.data.entity.Unit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Bad_sha
 */
public interface UnitRepository extends JpaRepository<Unit, Long> {

  @Query("select u from Unit u where u.deleted = 'F' order by updatedDate desc")
  List<Unit> listAllUnits();

  @Query("select u from Unit u where u.unitCode = :unitCode and u.deleted = 'F'")
  Unit findActiveUnit(@Param("unitCode") String unitCode);

  @Query("select u from Unit u where u.deleted = 'F' and u.active = 'T'")
  List<Unit> listAllActiveUnits();

  @Query("select u from Unit u where u.id in :ids and u.deleted = 'F'")
  List<Unit> listAllUnitsByIds(@Param("ids") List<Long> ids);

  Optional<Unit> findById(Long id);
}
