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
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface BrandService {

    boolean isBrandExists(final String brandName, final Long category);

    Brand saveBrand(final Brand brand) throws UnitedSuppliesException;

    List<Brand> brandList() throws UnitedSuppliesException;

    List<Brand> brandListByCategory(Long categoryId);

    Brand updateBrand(final Brand brand) throws UnitedSuppliesException;

    Brand findBrand(Long id) throws UnitedSuppliesException;

    Brand removeBrand(Long id) throws UnitedSuppliesException;
}
