/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.crusher;

import com.makinus.unitedsupplies.common.data.entity.Crusher;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface CrusherService {

    boolean isCrusherExists(final String crusher, final Long category);

    Crusher saveCrusher(final Crusher crusher);

    List<Crusher> crusherList();

    List<Crusher> crusherListByCategory(Long categoryId);

    Crusher updateCrusher(final Crusher crusher);

    Crusher findCrusher(Long id) throws UnitedSuppliesException;

    Crusher removeCrusher(Long id) throws UnitedSuppliesException;
}
