/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.mapper;

import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

/** Created by abuabdul */
public interface EntityWithExtraValueMapper<I, E, O> {

  O mapExtra(I input, E extraValue) throws UnitedSuppliesException;
}