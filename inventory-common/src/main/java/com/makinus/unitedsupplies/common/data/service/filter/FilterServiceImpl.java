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

import com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterDAO;
import com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterRequest;
import com.makinus.unitedsupplies.common.data.dao.filter.vendor.VendorFilterDAO;
import com.makinus.unitedsupplies.common.data.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class FilterServiceImpl implements FilterService {

    private final Logger LOG = LogManager.getLogger(FilterServiceImpl.class);

    @Autowired
    private ProductFilterDAO productFilterDAO;

    @Autowired
    private VendorFilterDAO vendorFilterDAO;

    @Override
    public List<Product> searchProduct(ProductFilterRequest productFilterRequest, List<Long> productIds, String categoryId) {
        LOG.info("Search Product By Filter in the database");
        return productFilterDAO.searchProduct(productFilterRequest, productIds, categoryId);
    }

    @Override
    public List<Product> searchProductsByName(String productName) {
        LOG.info("Search Product By Name in the database");
        return productFilterDAO.searchProductsByName(productName);
    }

    @Override
    public List<Product> productListByIds(List<Long> productIds) {
        LOG.info("Search Product Ids in the database");
        return productFilterDAO.productListByIds(productIds);
    }
}
