/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.filter.product;

import com.makinus.unitedsupplies.common.data.form.ProductFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * @author abuabdul
 */
public interface ProductFilterDAO {

    List<Product> searchProduct(ProductFilterRequest productFilterRequest, List<Long> productIds, String categoryId);

    List<Product> searchProductsByName(String productName);

    List<Product> productListByIds(List<Long> productIds);

    List<Product> filterProduct(ProductFilterForm productFilterForm) throws UnitedSuppliesException;
}
