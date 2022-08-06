package com.example.repository;

import com.example.entity.Pricing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Bad_sha 24/07/22
 */
@Repository
public interface PricingRepository extends CrudRepository<Pricing, Integer> {


}
