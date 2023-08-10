/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.unitmapping;

import com.makinus.unitedsupplies.common.data.entity.UnitMapping;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface UnitMappingService {

    boolean isUnitMappingExists(final Long unit, final Long category);

    UnitMapping saveUnitMapping(final UnitMapping unitMapping);

    List<UnitMapping> unitMappingList();

    List<UnitMapping> unitMappingListByCategory(Long categoryId);

    UnitMapping updateUnitMapping(final UnitMapping unitMapping);

    UnitMapping findUnitMapping(Long id) throws InventoryException;

    UnitMapping removeUnitMapping(Long id) throws InventoryException;
}
