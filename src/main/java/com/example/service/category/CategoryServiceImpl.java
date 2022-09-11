/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.service.category;


import com.amazonaws.util.DateUtils;
import com.example.data.reftype.YNStatus;
import com.example.entity.Category;
import com.example.exception.BazzarException;
import com.example.file.ImageWriter;
import com.example.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import static java.lang.String.format;

/**
 * Created by BAD_SHA
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageWriter imageWriter;

    public CategoryServiceImpl(@Autowired CategoryRepository categoryRepository, ImageWriter imageWriter) {
        this.categoryRepository = categoryRepository;
        this.imageWriter = imageWriter;
    }

    @Override
    public Category saveCategory(Category category) {
        LOG.info("Saving Category in the database");
        Category savedCategory = categoryRepository.save(category);
        LOG.info("Saved Category in the database");
        return savedCategory;
    }

    @Override
    public List<Category> categoryList() {
        LOG.info("List Categories from database");
        return categoryRepository.listAllCategories();
    }

    @Override
    public List<Category> categoryActiveList() {
        LOG.info("List Categories from database");
        return categoryRepository.listAllActiveCategories();
    }

    @Override
    public List<Category> categoryListByParent(Long parentCategory) {
        LOG.info("List Sub Categories by Parent from database");
        return categoryRepository.listCategoriesByParentCategory(parentCategory);
    }

    @Override
    public Category removeCategory(Long id) throws BazzarException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setDeleted(YNStatus.YES.getStatus());
//            category.setUpdatedBy(SYSTEM_);
//            category.setUpdatedDate();
            return category;
        }
        throw new BazzarException(format("Category is not found with the id %d", id));
    }

    @Override
    public Category updateCategory(Category category) {
        LOG.info("Update existing category in the catalog");
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategory(Long id) throws BazzarException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category;
        }
        throw new BazzarException(format("Category is not found with the id %d", id));
    }

    @Override
    public Category findCategoryWithImage(Long id) throws BazzarException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
//        if (categoryOptional.isPresent()) {
//            Category category = categoryOptional.get();
//            category.setFileByte(imageWriter.readBytes(get(category.getImagePath())));
//            return category;
//        }
        throw new BazzarException(format("Category is not found with the id %d", id));
    }

    @Override
    public boolean isCategoryAvailable(String categoryCode) {
        LOG.info("Check if code is available from database");
        Category existCode = categoryRepository.findActiveCategory(categoryCode);
        return existCode != null;
    }
}
