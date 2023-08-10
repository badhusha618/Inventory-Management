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

import com.makinus.unitedsupplies.common.data.entity.ProductSource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author abuabdul */
public interface ProductSourceRepository extends JpaRepository<ProductSource, Long> {

  @Query("select p from ProductSource p")
  List<ProductSource> listAllProductSources();

  @Query("select p from ProductSource p")
  List<ProductSource> listAllActiveProductSources();

  @Query("select p from ProductSource p where p.productId = :prodId and p.deleted = 'F'")
  List<ProductSource> findByProdId(@Param("prodId") Long prodId);

  @Query(
      "select p from ProductSource p where p.productId = :prodId and p.isDefault = 'T' and p.deleted = 'F'")
  Optional<ProductSource> findDefaultSourceByProdId(@Param("prodId") Long prodId);

  @Query("select p from ProductSource p where p.isDefault = 'T' and p.deleted = 'F'")
  List<ProductSource> findDefaultProductSources();

  Optional<ProductSource> findById(Long id);
}
