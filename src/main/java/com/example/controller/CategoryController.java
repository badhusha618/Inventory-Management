package com.example.controller;

import com.example.data.request.CategoryRequest;
import com.example.data.response.CategoryResponse;
import com.example.entity.Category;
import com.example.exception.BazzarException;
import com.example.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.utils.AdminUtils.SYSTEM_USER;
import static com.example.utils.AdminUtils.getInstant;

/**
 * Created by Bad_sha 24/07/22
 */


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @RequestMapping("/{id}")
    public Category searchCategory(@PathVariable Long id) throws BazzarException {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.saveCategory(mapCategory(categoryRequest));
        CategoryResponse categoryResponse = mapCategoryResponse(category);
        return new ResponseEntity<>(mapCategoryResponse(category), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) throws BazzarException {
        Category category = categoryService.findById(id);
        Category saveCategory = categoryService.updateCategory(categoryRequest,category);
        return new ResponseEntity<>(mapCategoryResponse(category), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws BazzarException {
        Category category = categoryService.findById(id);
        categoryService.deleteCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Category mapCategory(CategoryRequest categoryRequest) {
        return Category.builder().categoryName(categoryRequest.getCategoryName()).createdUser(SYSTEM_USER).createdDateTime(getInstant())
                .lastModifiedUser(SYSTEM_USER).lastModifiedDateTime(getInstant()).build();
    }

    private CategoryResponse mapCategoryResponse(Category category) {
       return CategoryResponse.builder().id(category.getId()).categoryName(category.getCategoryName()).build();
    }
}
