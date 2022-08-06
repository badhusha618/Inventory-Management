package com.example.repository;

import com.example.entity.Stock;
import org.springframework.data.repository.CrudRepository;
/**
 * Created by Bad_sha 24/07/22
 */

public interface StockRepository extends CrudRepository<Stock,Integer> {
}
