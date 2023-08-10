/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("ProductFeaturesMapper")
public class ProductFeaturesMapper implements EntityUpdateMapper<ProductFeaturesView, Product> {

    private final Logger LOG = LogManager.getLogger(ProductFeaturesMapper.class);

    @Override
    public Product map(ProductFeaturesView productFeaturesView, Product product) {
        LOG.info("Map ProductFeaturesView to product Entity");
        product.setUnitCode(productFeaturesView.getUnitCode());
        product.setUnitName(productFeaturesView.getUnitName());
        product.setBrandName(productFeaturesView.getBrand());
        product.setCrusherName(productFeaturesView.getCrusher());
        product.setCrusherLocation(productFeaturesView.getCrusherLocation());
        product.setGradeName(productFeaturesView.getGrade());
        product.setQualityName(productFeaturesView.getQuality());
        product.setWeightName(productFeaturesView.getWeight());
        product.setTypeName(productFeaturesView.getType());
        product.setMaterialName(productFeaturesView.getMaterial());
        product.setColorName(productFeaturesView.getColor());
        product.setSaleRate(productFeaturesView.getSaleRate());
        product.setMrpRate(productFeaturesView.getMrpRate());
        return product;
    }
}
