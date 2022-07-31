package com.example.service;

import com.example.data.request.CategoryRequest;
import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Bad_sha 24/07/22
 */


@Transactional
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Category category) {

        categoryRepository.save(category);
    }

    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    };

    public void deleteCategory(Category category) {

        categoryRepository.delete(category);
    }


}
