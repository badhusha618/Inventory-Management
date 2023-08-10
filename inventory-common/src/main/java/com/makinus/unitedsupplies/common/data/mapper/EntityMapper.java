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

import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.text.ParseException;

/** Created by Bad_sha */
public interface EntityMapper<I, O> {

  O map(I input) throws InventoryException, ParseException;
}
