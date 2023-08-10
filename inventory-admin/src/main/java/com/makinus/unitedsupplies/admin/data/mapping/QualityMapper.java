/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.mapping;

import com.makinus.Inventory.admin.data.forms.QualityForm;
import com.makinus.Inventory.common.data.entity.Quality;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

/**
 * @author Bad_sha
 */
@Component
@Qualifier("QualityMapper")
public class QualityMapper
    implements EntityMapper<QualityForm, Quality>,
        EntityUpdateMapper<QualityForm, Quality>,
        EntityRemapper<Quality, QualityForm> {

  private final Logger LOG = LogManager.getLogger(QualityMapper.class);

  @Override
  public Quality map(QualityForm qualityForm) throws InventoryException {
    LOG.info("Map Quality Form to Quality Entity");
    Quality quality = new Quality();
    quality.setQuality(qualityForm.getQuality());
    quality.setCategory(Long.valueOf(qualityForm.getCategory()));
    quality.setActive(qualityForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    quality.setCreatedBy(getCurrentUser());
    quality.setCreatedDate(getInstant());
    quality.setUpdatedBy(getCurrentUser());
    quality.setUpdatedDate(getInstant());
    quality.setDeleted(YNStatus.NO.getStatus());
    return quality;
  }

  @Override
  public Quality map(QualityForm qualityForm, Quality quality) throws InventoryException {

    LOG.info("Map Quality Form to Updated Quality Entity");
    quality.setQuality(qualityForm.getQuality());
    // quality.setCategory(Long.valueOf(qualityForm.getCategory()));
    quality.setActive(qualityForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    quality.setUpdatedBy(getCurrentUser());
    quality.setUpdatedDate(getInstant());
    quality.setDeleted(YNStatus.NO.getStatus());
    return quality;
  }

  @Override
  public QualityForm remap(Quality quality) throws InventoryException {
    LOG.info("Map Quality Entity to Quality Form");
    QualityForm qualityForm = new QualityForm();
    qualityForm.setQualityID(String.valueOf(quality.getId()));
    qualityForm.setQuality(quality.getQuality());
    qualityForm.setCategory(String.valueOf(quality.getCategory()));
    qualityForm.setActive(quality.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return qualityForm;
  }
}
