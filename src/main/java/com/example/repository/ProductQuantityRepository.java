package com.example.repository;

import com.example.entity.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {

    Optional<ProductQuantity> findById(Long id);

    @Query("select p from ProductQuantity p where p.productId = :productId and p.deleted = 'F'")
    List<ProductQuantity> findQuantitiesByProduct(@Param("productId") Long productId);

    @Query("select p from ProductQuantity p where p.deleted = 'F'")
    List<ProductQuantity> findAllQuantities();

    @Modifying
    @Query("update ProductQuantity p set p.deleted = 'T' where p.id in :ids")
    void deleteProductQuantitiesByIds(@Param("ids") List<Long> ids);
}
