package com.example.repository;

import com.example.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Bad_sha 24/07/22
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {


}
