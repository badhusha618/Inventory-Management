/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productchargehistory;

import com.makinus.unitedsupplies.common.data.dao.ProductChargeHistoryRepository;
import com.makinus.unitedsupplies.common.data.entity.ProductChargeHistory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by abuabdul
 **/
@Service
@Transactional
public class ProductChargeHistoryServiceImpl implements ProductChargeHistoryService {

    private final Logger LOG = LogManager.getLogger(ProductChargeHistoryServiceImpl.class);

    private final ProductChargeHistoryRepository productChargeHistoryRepository;

    public ProductChargeHistoryServiceImpl(@Autowired ProductChargeHistoryRepository productChargeHistoryRepository) {
        this.productChargeHistoryRepository = productChargeHistoryRepository;
    }

    @Override
    public ProductChargeHistory saveProductChargeHistory(ProductChargeHistory productChargeHistory) {
        LOG.info("Saving ProductChargeHistory in the database");
        ProductChargeHistory savedProductChargeHistory = productChargeHistoryRepository.save(productChargeHistory);
        LOG.info("Saved ProductChargeHistory in the database");
        return savedProductChargeHistory;
    }

    @Override
    public List<ProductChargeHistory> saveProductChargeHistoryList(List<ProductChargeHistory> productChargeHistories) {
        LOG.info("Save ProductChargeHistory List in the database");
        return productChargeHistoryRepository.saveAll(productChargeHistories);
    }

    @Override
    public List<ProductChargeHistory> productChargeHistoryList(Long prodId, Long vendorId) {
        LOG.info("Get ProductChargeHistory By Product Id Vendor Id from database");
        return productChargeHistoryRepository.findByVendorAndProd(prodId, vendorId);
    }

    @Override
    public List<ProductChargeHistory> getProductChargeHistory(Long prodId) {
        LOG.info("Get ProductChargeHistory By Product Id from database");
        return productChargeHistoryRepository.findByProd(prodId);
    }
}
