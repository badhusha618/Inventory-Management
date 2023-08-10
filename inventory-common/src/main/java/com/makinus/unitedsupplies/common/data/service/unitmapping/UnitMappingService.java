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
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface UnitMappingService {

    boolean isUnitMappingExists(final Long unit, final Long category);

    UnitMapping saveUnitMapping(final UnitMapping unitMapping);

    List<UnitMapping> unitMappingList();

    List<UnitMapping> unitMappingListByCategory(Long categoryId);

    UnitMapping updateUnitMapping(final UnitMapping unitMapping);

    UnitMapping findUnitMapping(Long id) throws UnitedSuppliesException;

    UnitMapping removeUnitMapping(Long id) throws UnitedSuppliesException;
}
