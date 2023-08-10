/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.type;

import com.makinus.unitedsupplies.common.data.entity.Type;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface TypeService {

    boolean isTypeExists(final String type, final Long category);

    Type saveType(final Type type);

    List<Type> typeList();

    List<Type> typeListByCategory(Long categoryId);

    Type updateType(final Type type);

    Type findType(Long id) throws InventoryException;

    Type removeType(Long id) throws InventoryException;
}
