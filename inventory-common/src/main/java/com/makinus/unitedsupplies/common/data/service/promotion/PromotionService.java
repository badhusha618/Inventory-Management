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
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.List;

/** Created by abuabdul */
public interface PromotionService {

  Promotion savePromotion(final Promotion promotion);

  Promotion updateSalePromotion(final Promotion promotion) throws UnitedSuppliesException;

  List<Promotion> promotionsList();

  List<Promotion> activePromotionsList();

  Promotion removePromotion(Long id) throws UnitedSuppliesException;

  Promotion findSalePromotion(Long id) throws UnitedSuppliesException;
}
