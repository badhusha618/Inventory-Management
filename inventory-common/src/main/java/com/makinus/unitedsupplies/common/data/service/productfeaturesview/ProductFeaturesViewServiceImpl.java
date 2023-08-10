/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productfeaturesview;

import com.makinus.unitedsupplies.common.data.dao.ProductFeaturesViewRepository;
import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by abuabdul
 */
@Service
@Transactional
public class ProductFeaturesViewServiceImpl implements ProductFeaturesViewService {

    private final Logger LOG = LogManager.getLogger(ProductFeaturesViewServiceImpl.class);

    private final ProductFeaturesViewRepository productFeaturesViewRepository;

    public ProductFeaturesViewServiceImpl(
            @Autowired ProductFeaturesViewRepository productFeaturesViewRepository) {
        this.productFeaturesViewRepository = productFeaturesViewRepository;
    }

    @Override
    public List<ProductFeaturesView> productFeaturesViewList() {
        LOG.info("List ProductFeaturesViews from database");
        return productFeaturesViewRepository.listAllProductFeaturesView();
    }

    @Override
    public List<ProductFeaturesView> listProductFeaturesViewByProdIds(List<Long> productIds) {
        LOG.info("List ProductFeaturesViews by product ids from database");
        return productFeaturesViewRepository.listAllProductFeaturesViewByProdIds(productIds);
    }

    @Override
    public ProductFeaturesView findProductFeaturesView(Long id) throws UnitedSuppliesException {
        Optional<ProductFeaturesView> productFeaturesViewOptional =
                productFeaturesViewRepository.findById(id);
        if (productFeaturesViewOptional.isPresent()) {
            return productFeaturesViewOptional.get();
        }
        throw new UnitedSuppliesException(
                String.format("ProductFeaturesView is not found with the id %d", id));
    }
}
