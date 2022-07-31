package com.example.controller;

import com.example.data.request.CategoryRequest;
import com.example.data.response.CategoryResponse;
import com.example.entity.TheLogConverter;
import com.example.entity.Category;
import com.example.service.CategoryLogService;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @Autowired
    private CategoryLogService categoryLogService;

    @GetMapping
    public Iterable<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @RequestMapping("/{id}")
    public Optional<Category> searchCategory(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.saveCategory(mapCategory(categoryRequest));
        CategoryResponse categoryResponse = mapCategoryResponse(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,value ="/{id}")
    public void updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        categoryLogService.insert(TheLogConverter.categoryLogConverter(category));
    }

    @RequestMapping(method = RequestMethod.DELETE,value ="/{id}")
    public void deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategory(category);
        categoryLogService.insert(TheLogConverter.categoryLogConverter(category));
    }

    private Category mapCategory(CategoryRequest categoryRequest) {
       return Category.builder().categoryName(categoryRequest.getCategoryName()).createdUser(SYSTEM_USER).createdDateTime(getInstant()).lastModifiedUser(SYSTEM_USER).lastModifiedDateTime(getInstant()).build();
    }

    private CategoryResponse mapCategoryResponse(Category category) {
       return CategoryResponse.builder().id(category.getId()).categoryName(category.getCategoryName()).build();
    }
}
