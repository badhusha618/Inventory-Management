package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author kingson
 */
public interface WeightRepository extends JpaRepository<Weight, Long> {

    @Query("select w from Weight w where w.deleted = 'F' order by updatedDate desc")
    List<Weight> listOfWeight();

    @Query("select w from Weight w where w.category = :categoryId and w.deleted = 'F'")
    List<Weight> listOfWeightByCategory(@Param("categoryId") Long categoryId);

    Optional<Weight> findById(Long id);

    @Query("select w from Weight w where w.category = :category and w.weight = :weight and w.deleted = 'F'")
    Optional<Weight> findAvailableCategoryForWeight(@Param("weight") String weight, @Param("category") Long category);


}