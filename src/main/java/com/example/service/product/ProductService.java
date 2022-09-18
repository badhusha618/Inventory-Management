/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.service.product;

import com.example.entity.Product;
import com.example.exception.BazzarException;

import java.util.List;
import java.util.Optional;

/**
 * Created by BAD_SHA
 */
public interface ProductService {

    Product addNewProduct(final Product product);

    List<Product> productList();

    List<Product> productListByCategory(Long category);

    Product updateProduct(final Product product);

    Product removeProduct(Long id) throws BazzarException;

    Product findProduct(Long id) throws BazzarException;

    Optional<Product> findOptionalProduct(Long id) throws BazzarException;

    Product findProductWithImage(Long id) throws BazzarException;
    List<Product> productListByIds(List<Long> productIds);
}
