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
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface CategoryService {

    boolean isCategoryAvailable(final String categoryName) throws UnitedSuppliesException;

    Category saveCategory(final Category category) throws UnitedSuppliesException;

    List<Category> categoryList() throws UnitedSuppliesException;

    List<Category> parentCategoryList() throws UnitedSuppliesException;

    List<Category> activeParentCategoryList() throws UnitedSuppliesException;

    List<Category> categoryActiveList() throws UnitedSuppliesException;

    List<Category> categoryListByParent(Long parentCategory) throws UnitedSuppliesException;

    Category updateCategory(final Category category) throws UnitedSuppliesException;

    Category findCategory(Long id) throws UnitedSuppliesException;

    Category removeCategory(Long id) throws UnitedSuppliesException;

}
