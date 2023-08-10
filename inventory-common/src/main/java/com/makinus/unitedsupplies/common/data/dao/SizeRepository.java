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

import com.makinus.unitedsupplies.common.data.entity.Size;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Bad_sha
 */
public interface SizeRepository extends JpaRepository<Size, Long> {

  @Query("select b from Size b where b.deleted = 'F' order by updatedDate desc")
  List<Size> listAllSizes();

  @Query("select b from Size b where b.category = :categoryId and b.deleted = 'F'")
  List<Size> listAllSizesByCategory(@Param("categoryId") Long categoryId);

  Optional<Size> findById(Long id);
}
