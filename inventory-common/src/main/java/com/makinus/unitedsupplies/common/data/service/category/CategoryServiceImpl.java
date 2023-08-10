/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.category;

import com.makinus.unitedsupplies.common.data.dao.CategoryRepository;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Paths.get;

/**
 * Created by abuabdul
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger LOG = LogManager.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final ImageWriter imageWriter;

    public CategoryServiceImpl(
            @Autowired CategoryRepository categoryRepository, ImageWriter imageWriter) {
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
    public boolean isCategoryAvailable(String categoryName) {
        LOG.info("Check if UnitedSupplies category is available from database");
        Category category = categoryRepository.findActiveCategory(categoryName);
        return category != null;
    }

    @Override
    public List<Category> categoryList() {
        LOG.info("List Categories from database");
        return categoryRepository.listAllCategories();
    }

    @Override
    public List<Category> parentCategoryList() {
        LOG.info("List Parent Categories from database");
        return categoryRepository.listAllParentCategories();
    }

    @Override
    public List<Category> activeParentCategoryList() {
        LOG.info("List Active Parent Categories from database");
        return categoryRepository.listAllActiveParentCategories();
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
    public Category removeCategory(Long id) throws UnitedSuppliesException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setDeleted(YNStatus.YES.getStatus());
            category.setUpdatedBy(AppUtils.getCurrentUser());
            category.setUpdatedDate(AppUtils.getInstant());
            return category;
        }
        throw new UnitedSuppliesException(format("Promotion is not found with the id %d", id));
    }

    @Override
    public Category updateCategory(Category category) {
        LOG.info("Update existing category in the catalog");
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategory(Long id) throws UnitedSuppliesException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
//            category.setImage(imageWriter.readImage(get(category.getImagePath())));
            return category;
        }
        throw new UnitedSuppliesException(format("Category is not found with the id %d", id));
    }

}
