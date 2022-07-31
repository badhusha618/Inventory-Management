package com.example.service;

import com.example.data.request.CategoryRequest;
import com.example.entity.Category;
import com.example.exception.BazzarException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Bad_sha 24/07/22
 */


@Transactional
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category findById(Long id) throws BazzarException {
        Optional<Category> categoryOptional =categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category;
        }
        throw new BazzarException(format("Video not found with the id %d", id));
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(CategoryRequest categoryRequest ,Category category) {
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return category;
    }

    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    };

    public void deleteCategory(Category category) {

        categoryRepository.delete(category);
    }


}
