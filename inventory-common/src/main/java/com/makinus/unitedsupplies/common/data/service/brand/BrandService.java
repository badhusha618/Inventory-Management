/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.brand;

import com.makinus.unitedsupplies.common.data.entity.Brand;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface BrandService {

    boolean isBrandExists(final String brandName, final Long category);

    Brand saveBrand(final Brand brand) throws InventoryException;

    List<Brand> brandList() throws InventoryException;

    List<Brand> brandListByCategory(Long categoryId);

    Brand updateBrand(final Brand brand) throws InventoryException;

    Brand findBrand(Long id) throws InventoryException;

    Brand removeBrand(Long id) throws InventoryException;
}
