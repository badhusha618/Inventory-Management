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
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface CrusherService {

    boolean isCrusherExists(final String crusher, final Long category);

    Crusher saveCrusher(final Crusher crusher);

    List<Crusher> crusherList();

    List<Crusher> crusherListByCategory(Long categoryId);

    Crusher updateCrusher(final Crusher crusher);

    Crusher findCrusher(Long id) throws InventoryException;

    Crusher removeCrusher(Long id) throws InventoryException;
}
