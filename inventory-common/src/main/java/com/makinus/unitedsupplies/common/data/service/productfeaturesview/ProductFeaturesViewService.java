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

import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface ProductFeaturesViewService {

    List<ProductFeaturesView> productFeaturesViewList();

    List<ProductFeaturesView> listProductFeaturesViewByProdIds(List<Long> productIds);

    ProductFeaturesView findProductFeaturesView(Long id) throws UnitedSuppliesException;
}
