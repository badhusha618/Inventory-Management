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

import com.makinus.unitedsupplies.common.data.entity.ProductChargeHistory;

import java.util.List;

/**
 * Created by Shahul
 **/
public interface ProductChargeHistoryService {

    ProductChargeHistory saveProductChargeHistory(final ProductChargeHistory productChargeHistory);

    List<ProductChargeHistory> saveProductChargeHistoryList(List<ProductChargeHistory> ProductChargeHistory);

    List<ProductChargeHistory> productChargeHistoryList(Long prodId, Long vendorId);

    List<ProductChargeHistory> getProductChargeHistory(Long prodId);
}
