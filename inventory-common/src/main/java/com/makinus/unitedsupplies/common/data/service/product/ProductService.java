/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.product;

import com.makinus.unitedsupplies.common.data.form.ProductFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface ProductService {
    boolean isProductAvailable(final String username) throws UnitedSuppliesException;

    Product addNewProduct(final Product product);

    List<Product> productList();

    List<Product> productListByIds(List<Long> productIds);

    List<Product> productListBySubCategory(Long parentCategory);

    List<Product> productListByCategory(Long parentCategory);

    List<Product> searchProductsByName(String searchQuery);

    Product updateProduct(final Product product);

    Product removeProduct(Long id) throws UnitedSuppliesException;

    Product findProduct(Long id) throws UnitedSuppliesException;

    Product findProductWithImages(Long id) throws UnitedSuppliesException;

    List<Long> findProductIdsByCategory(Long categoryId);

    List<String> getTransGroupListByProductList(List<Long> productList);

    List<Product> filterProduct(ProductFilterForm productFilterForm) throws UnitedSuppliesException;
}
