package com.example.repository;

import com.example.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Bad_sha 24/07/22
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("select p from Product p where p.deleted = 'F'")
    List<Product> findAllActiveProducts();

    Optional<Product> findById(Long id);

    @Query("select p from Product p where p.id in (:productIds) and p.deleted = 'F'")
    List<Product> findAllActiveProductsByIds(@Param("productIds") List<Long> productIds);

    @Query("select p from Product p where p.category = :category and p.deleted = 'F'")
    List<Product> findActiveProductsByCategory(@Param("category") Long category);
}
