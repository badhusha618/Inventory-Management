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

import com.example.entity.Category;
import com.example.exception.BazzarException;
import java.util.List;

/**
 * Created by BAD_SHA
 */
public interface CategoryService {

    Category saveCategory(final Category category);

    List<Category> categoryList();

    List<Category> categoryActiveList();

    List<Category> categoryListByParent(Long parentCategory);

    boolean isCategoryAvailable(final String categoryCode);

    Category updateCategory(final Category category);

    Category findCategory(Long id) throws BazzarException;

    Category findCategoryWithImage(Long id) throws BazzarException;

    Category removeCategory(Long id) throws BazzarException;
}
