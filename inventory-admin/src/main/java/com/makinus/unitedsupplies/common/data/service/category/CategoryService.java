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

import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface CategoryService {

    boolean isCategoryAvailable(final String categoryName) throws InventoryException;

    Category saveCategory(final Category category) throws InventoryException;

    List<Category> categoryList() throws InventoryException;

    List<Category> parentCategoryList() throws InventoryException;

    List<Category> activeParentCategoryList() throws InventoryException;

    List<Category> categoryActiveList() throws InventoryException;

    List<Category> categoryListByParent(Long parentCategory) throws InventoryException;

    Category updateCategory(final Category category) throws InventoryException;

    Category findCategory(Long id) throws InventoryException;

    Category removeCategory(Long id) throws InventoryException;

}
