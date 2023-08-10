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

import com.makinus.unitedsupplies.common.data.dao.ColorRepository;
import com.makinus.unitedsupplies.common.data.entity.Color;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    private final Logger LOG = LogManager.getLogger(ColorServiceImpl.class);

    private final ColorRepository colorRepository;

    public ColorServiceImpl(@Autowired ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public boolean isColorExists(String color, Long category) {
        LOG.info("Check if UnitedSupplies Color is available from database");
        Optional<Color> findColor = colorRepository.findAvailableCategoryForColor(color, category);
        return findColor.isPresent();
    }

    @Override
    public Color saveColor(Color color) {
        LOG.info("Saving Color in the database");
        Color savedColor = colorRepository.save(color);
        LOG.info("Saved Color in the database");
        return savedColor;
    }

    @Override
    public List<Color> colorList() {
        LOG.info("List Color from database");
        return colorRepository.listAllColors();
    }

    @Override
    public List<Color> colorListByCategory(Long categoryId) {
        LOG.info("List Color By Category from database");
        return colorRepository.listAllColorsByCategory(categoryId);
    }

    @Override
    public Color updateColor(final Color color) {
        LOG.info("Update existing color in the catalog");
        return colorRepository.save(color);
    }

    @Override
    public Color findColor(Long id) throws InventoryException {
        Optional<Color> colorOptional = colorRepository.findById(id);
        if (colorOptional.isPresent()) {
            return colorOptional.get();
        }
        throw new InventoryException(String.format("Color is not found with the id %d", id));
    }

    @Override
    public Color removeColor(Long id) throws InventoryException {
        Optional<Color> colorOptional = colorRepository.findById(id);
        if (colorOptional.isPresent()) {
            Color color = colorOptional.get();
            color.setDeleted(YNStatus.YES.getStatus());
            color.setUpdatedBy(getCurrentUser());
            color.setUpdatedDate(getInstant());
            return color;
        }
        throw new InventoryException(String.format("Promotion is not found with the id %d", id));
    }
}
