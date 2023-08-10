/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.CategoryForm;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("CategoryMapper")
public class CategoryMapper implements EntityMapper<CategoryForm, Category>, EntityUpdateMapper<CategoryForm, Category>, EntityRemapper<Category, CategoryForm> {

    private final Logger LOG = LogManager.getLogger(CategoryMapper.class);

    @Autowired
    private ImageWriter imageWriter;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public Category map(CategoryForm categoryForm) throws UnitedSuppliesException {
        LOG.info("Map Category Form to Category Entity");
        Category category = new Category();
        try {
            category.setCategoryName(categoryForm.getCategoryName());
            if (isNotEmpty(categoryForm.getParentCategory())) {
                category.setParentCategory(Long.valueOf(categoryForm.getParentCategory()));
            }
            category.setOriginalFileName(categoryForm.getCategoryImage().getOriginalFilename());
            category.setImage(categoryForm.getCategoryImage().getBytes());
            category.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
            category.setImagePath(getS3UrlFromAttachment(categoryForm.getCategoryImage(), CATEGORY_PREFIX, amazonS3Client));
            category.setCreatedBy(getCurrentUser());
            category.setCreatedDate(getInstant());
            category.setUpdatedBy(getCurrentUser());
            category.setUpdatedDate(getInstant());
            category.setActive(categoryForm.isActiveCategory() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            category.setDeleted(YNStatus.NO.getStatus());
        } catch (IOException e) {
            LOG.warn("CategoryMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return category;
    }

    @Override
    public Category map(CategoryForm categoryForm, Category category) throws UnitedSuppliesException {
        LOG.info("Map Category Form to Updated Category Entity");
        try {
            category.setCategoryName(categoryForm.getCategoryName());
            if (isNotEmpty(categoryForm.getParentCategory())) {
                category.setParentCategory(Long.valueOf(categoryForm.getParentCategory()));
            }
            if (categoryForm.getEditCategoryImage() != null && !categoryForm.getEditCategoryImage().isEmpty()) {
                category.setOriginalFileName(categoryForm.getEditCategoryImage().getOriginalFilename());
                category.setImage(categoryForm.getEditCategoryImage().getBytes());
                category.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                category.setImagePath(getS3UrlFromAttachment(categoryForm.getEditCategoryImage(), CATEGORY_PREFIX, amazonS3Client));
            }
            category.setUpdatedBy(getCurrentUser());
            category.setUpdatedDate(getInstant());
            category.setActive(categoryForm.isActiveCategory() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        } catch (IOException e) {
            LOG.warn("ProductMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return category;
    }

    @Deprecated
    private String imagePath(Category category) throws UnitedSuppliesException {
        return imageWriter.writeImage(category.getImage(), category.getCreatedDateAsFolderName(), String.valueOf(AppUtils.timestamp()));
    }

    @Override
    public CategoryForm remap(Category category) throws UnitedSuppliesException {
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setCategoryID(String.valueOf(category.getId()));
        categoryForm.setCategoryName(category.getCategoryName());
        if (category.getParentCategory() != null) {
            categoryForm.setParentCategory(String.valueOf(category.getParentCategory()));
        }
        categoryForm.setActiveCategory(category.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        return categoryForm;
    }
}
