/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.color;

import com.makinus.unitedsupplies.common.data.entity.Color;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface ColorService {

    boolean isColorExists(final String color, final Long category);

    Color saveColor(final Color color);

    List<Color> colorList();

    List<Color> colorListByCategory(Long categoryId);

    Color updateColor(final Color color);

    Color findColor(Long id) throws UnitedSuppliesException;

    Color removeColor(Long id) throws UnitedSuppliesException;
}
