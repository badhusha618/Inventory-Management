/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.specification;

import com.makinus.unitedsupplies.common.data.entity.Specification;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface SpecificationService {

    Specification saveSpecification(final Specification specification);

    List<Specification> specificationList();

    List<Specification> specificationListByCategory(Long categoryId);

    Specification updateSpecification(final Specification specification);

    Specification findSpecification(Long id) throws InventoryException;

    Specification removeSpecification(Long id) throws InventoryException;
}
