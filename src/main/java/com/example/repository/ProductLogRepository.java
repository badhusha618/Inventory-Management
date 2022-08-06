package com.example.repository;

import com.example.entity.ProductLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
/**
 * Created by Bad_sha 24/07/22
 */

@Repository
public interface ProductLogRepository extends JpaRepository<ProductLogEntity,Integer>{
}
