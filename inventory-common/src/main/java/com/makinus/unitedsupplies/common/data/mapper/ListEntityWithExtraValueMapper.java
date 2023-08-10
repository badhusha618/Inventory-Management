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
import java.util.List;

/** Created by abuabdul */
public interface ListEntityWithExtraValueMapper<I, E, O> {

  List<O> map(I input, E extraValue) throws UnitedSuppliesException;
}
