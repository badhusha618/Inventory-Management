/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.quality;

import com.makinus.unitedsupplies.common.data.dao.QualityRepository;
import com.makinus.unitedsupplies.common.data.entity.Quality;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
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
 * Created by abuabdul
 */
@Service
@Transactional
public class QualityServiceImpl implements QualityService {

    private final Logger LOG = LogManager.getLogger(QualityServiceImpl.class);

    private final QualityRepository qualityRepository;

    public QualityServiceImpl(@Autowired QualityRepository qualityRepository) {
        this.qualityRepository = qualityRepository;
    }

    @Override
    public boolean isQualityExists(String quality, Long category) {
        LOG.info("Check if UnitedSupplies Quality is available from database");
        Optional<Quality> findQuality = qualityRepository.findAvailableCategoryForQuality(quality, category);
        return findQuality.isPresent();
    }

    @Override
    public Quality saveQuality(Quality quality) {
        LOG.info("Saving Quality in the database");
        Quality savedQuality = qualityRepository.save(quality);
        LOG.info("Saved Quality in the database");
        return savedQuality;
    }

    @Override
    public List<Quality> qualityList() {
        LOG.info("List Quality from database");
        return qualityRepository.listAllQualities();
    }

    @Override
    public List<Quality> qualityListByCategory(Long categoryId) {
        LOG.info("List Quality By Category from database");
        return qualityRepository.listAllQualitiesByCategory(categoryId);
    }

    @Override
    public Quality updateQuality(final Quality quality) {
        LOG.info("Update existing quality in the catalog");
        return qualityRepository.save(quality);
    }

    @Override
    public Quality findQuality(Long id) throws UnitedSuppliesException {
        Optional<Quality> qualityOptional = qualityRepository.findById(id);
        if (qualityOptional.isPresent()) {
            return qualityOptional.get();
        }
        throw new UnitedSuppliesException(String.format("Quality is not found with the id %d", id));
    }

    @Override
    public Quality removeQuality(Long id) throws UnitedSuppliesException {
        Optional<Quality> qualityOptional = qualityRepository.findById(id);
        if (qualityOptional.isPresent()) {
            Quality quality = qualityOptional.get();
            quality.setDeleted(YNStatus.YES.getStatus());
            quality.setUpdatedBy(getCurrentUser());
            quality.setUpdatedDate(getInstant());
            return quality;
        }
        throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
    }
}
