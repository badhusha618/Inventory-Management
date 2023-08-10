/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.filter;

import com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterRequest;
import com.makinus.unitedsupplies.common.data.entity.Product;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface FilterService {

    List<Product> searchProduct(ProductFilterRequest productFilterRequest, List<Long> productIds, String categoryId);

    List<Product> searchProductsByName(String productName);

    List<Product> productListByIds(List<Long> productIds);

}
