package com.example.repository;

import com.example.entity.Category;
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
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("select c from Category c where c.deleted = 'F' order by updatedDate desc")
    List<Category> listAllCategories();

    @Query("select c from Category c where c.deleted = 'F' and c.active = 'T' ")
    List<Category> listAllActiveCategories();

    @Query("select c from Category c where c.parentCategory = :parentCategory and c.deleted = 'F' and c.active = 'T' ")
    List<Category> listCategoriesByParentCategory(@Param("parentCategory") Long parentCategory);

    Optional<Category> findById(Long id);

    @Query("select c from Category c where c.categoryCode = :categoryCode and c.deleted = 'F'")
    Category findActiveCategory(@Param("categoryCode") String categoryCode);
}
