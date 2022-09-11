package com.example.repository;

import com.example.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("select p from ProductImage p where p.deleted = 'F' order by updatedDate desc")
    List<ProductImage> listAllProductImages();

    Optional<ProductImage> findById(Long id);

    @Query("select p from ProductImage p where p.product.id = :productId and p.deleted = 'F'")
    List<ProductImage> findImagesByProduct(@Param("productId") Long productId);

    @Modifying
    @Query("update ProductImage p set p.deleted = 'Y' where p.id in :ids")
    void deleteProductImagesByIds(@Param("ids") List<Long> ids);
}
