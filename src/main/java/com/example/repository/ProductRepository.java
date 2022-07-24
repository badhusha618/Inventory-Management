package com.example.repository;

import com.example.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Bad_sha 24/07/22
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
