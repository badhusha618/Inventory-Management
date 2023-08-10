/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.material;

import com.makinus.unitedsupplies.common.data.entity.Material;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by ammar
 */
public interface MaterialService {

    boolean isMaterialExists(final String material, final Long category);

    Material saveMaterial(final Material material);

    List<Material> materialList();

    List<Material> materialListByCategory(Long categoryId);

    Material updateMaterial(final Material material);

    Material findMaterial(Long id) throws UnitedSuppliesException;

    Material removeMaterial(Long id) throws UnitedSuppliesException;
}
