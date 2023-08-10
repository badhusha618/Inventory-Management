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

import com.makinus.unitedsupplies.common.data.entity.Quality;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

/**
 * Created by abuabdul
 */
public interface QualityService {

    boolean isQualityExists(final String quality, final Long category);

    Quality saveQuality(final Quality quality);

    List<Quality> qualityList();

    List<Quality> qualityListByCategory(Long categoryId);

    Quality updateQuality(final Quality quality);

    Quality findQuality(Long id) throws UnitedSuppliesException;

    Quality removeQuality(Long id) throws UnitedSuppliesException;
}
