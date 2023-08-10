/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.loadingCharges;

import com.makinus.unitedsupplies.common.data.entity.LoadingCharges;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by abuabdul
 */
public interface LoadingChargesService {

    boolean isQuantityExists(final Integer quantity, final Long productId);

    List<LoadingCharges> loadingChargesList() throws UnitedSuppliesException;

    List<LoadingCharges> loadingChargesListByProduct(List<Long> productIds);

    Optional<Tuple<BigDecimal, Integer>> availableQuantityListByProduct(Integer quantity, Long productId);

    List<LoadingCharges> addNewLoadingCharges(List<LoadingCharges> loadingCharges);

    LoadingCharges findLoadingCharges(Long id) throws UnitedSuppliesException;

    LoadingCharges updateLoadingCharges(final LoadingCharges loadingCharges);

    LoadingCharges removeLoadingCharges(Long id) throws UnitedSuppliesException;
}
