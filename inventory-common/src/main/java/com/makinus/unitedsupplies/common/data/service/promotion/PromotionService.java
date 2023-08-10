/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.promotion;

import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/** Created by Bad_sha */
public interface PromotionService {

  Promotion savePromotion(final Promotion promotion);

  Promotion updateSalePromotion(final Promotion promotion) throws InventoryException;

  List<Promotion> promotionsList();

  List<Promotion> activePromotionsList();

  Promotion removePromotion(Long id) throws InventoryException;

  Promotion findSalePromotion(Long id) throws InventoryException;
}
