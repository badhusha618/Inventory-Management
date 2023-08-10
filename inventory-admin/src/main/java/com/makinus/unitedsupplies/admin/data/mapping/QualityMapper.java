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

import com.makinus.unitedsupplies.admin.data.forms.QualityForm;
import com.makinus.unitedsupplies.common.data.entity.Quality;
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

/** Created by ammar */
@Component
@Qualifier("QualityMapper")
public class QualityMapper
    implements EntityMapper<QualityForm, Quality>,
        EntityUpdateMapper<QualityForm, Quality>,
        EntityRemapper<Quality, QualityForm> {

  private final Logger LOG = LogManager.getLogger(QualityMapper.class);

  @Override
  public Quality map(QualityForm qualityForm) throws UnitedSuppliesException {
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
  public Quality map(QualityForm qualityForm, Quality quality) throws UnitedSuppliesException {

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
  public QualityForm remap(Quality quality) throws UnitedSuppliesException {
    LOG.info("Map Quality Entity to Quality Form");
    QualityForm qualityForm = new QualityForm();
    qualityForm.setQualityID(String.valueOf(quality.getId()));
    qualityForm.setQuality(quality.getQuality());
    qualityForm.setCategory(String.valueOf(quality.getCategory()));
    qualityForm.setActive(quality.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return qualityForm;
  }
}
