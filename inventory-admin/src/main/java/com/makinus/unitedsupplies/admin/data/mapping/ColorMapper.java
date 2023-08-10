/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.ColorForm;
import com.makinus.unitedsupplies.common.data.entity.Color;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/** Created by abuabdul */
@Component
@Qualifier("ColorMapper")
public class ColorMapper
        implements EntityMapper<ColorForm, Color>,
        EntityUpdateMapper<ColorForm, Color>,
        EntityRemapper<Color, ColorForm> {

    private final Logger LOG = LogManager.getLogger(ColorMapper.class);

    @Override
    public Color map(ColorForm colorForm) throws UnitedSuppliesException {
        LOG.info("Map Color Form to Color Entity");
        Color color = new Color();
        color.setColor(colorForm.getColor());
        color.setCategory(Long.valueOf(colorForm.getCategory()));
        color.setActive(colorForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        color.setCreatedBy(getCurrentUser());
        color.setCreatedDate(getInstant());
        color.setUpdatedBy(getCurrentUser());
        color.setUpdatedDate(getInstant());
        color.setDeleted(YNStatus.NO.getStatus());
        return color;
    }

    @Override
    public Color map(ColorForm colorForm, Color color) throws UnitedSuppliesException {

        LOG.info("Map Color Form to Updated Color Entity");
        color.setColor(colorForm.getColor());
        color.setActive(colorForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
        color.setUpdatedBy(getCurrentUser());
        color.setUpdatedDate(getInstant());
        color.setDeleted(YNStatus.NO.getStatus());
        return color;
    }

    @Override
    public ColorForm remap(Color color) {
        LOG.info("Map Color Entity to Color Form");
        ColorForm colorForm = new ColorForm();
        colorForm.setColorID(String.valueOf(color.getId()));
        colorForm.setColor(color.getColor());
        colorForm.setCategory(String.valueOf(color.getCategory()));
        colorForm.setActive(color.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        return colorForm;
    }
}
