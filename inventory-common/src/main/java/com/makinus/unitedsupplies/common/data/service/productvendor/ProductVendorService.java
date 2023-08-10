/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productvendor;

import com.makinus.unitedsupplies.common.data.entity.ProductVendor;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by abuabdul
 **/
public interface ProductVendorService {

    ProductVendor saveProductVendor(final ProductVendor ProductVendor);

    List<ProductVendor> saveProductVendorList(List<ProductVendor> ProductVendors);

    List<ProductVendor> updateProductVendorList(List<ProductVendor> ProductVendors);

    List<ProductVendor> productVendorList();

    Double findMaxSaleRateForProducts(List<Long> productIds);

    List<Long> findMaxSaleRateForProducts(BigDecimal maxSaleRate, BigDecimal minSaleRate);

    List<ProductVendor> productVendorListByProductIds(List<Long> prodIds);

    List<ProductVendor> productVendorListByProduct(Long prodId);

    ProductVendor updateProductVendor(final ProductVendor ProductVendor);

    ProductVendor findProductVendor(Long id) throws UnitedSuppliesException;

    ProductVendor findDefaultProductVendor(Long prodId) throws UnitedSuppliesException;

    ProductVendor removeProductVendor(Long id) throws UnitedSuppliesException;
}
