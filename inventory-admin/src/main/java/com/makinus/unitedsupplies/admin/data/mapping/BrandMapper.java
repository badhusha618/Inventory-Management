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


import com.makinus.Inventory.admin.data.forms.BrandForm;
import com.makinus.inventory.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.entity.Brand;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/** Created by Bad_sha */
@Component
@Qualifier("BrandMapper")
public class BrandMapper
    implements EntityMapper<BrandForm, Brand>,
        EntityUpdateMapper<BrandForm, Brand>,
        EntityRemapper<Brand, BrandForm> {

  private final Logger LOG = LogManager.getLogger(BrandMapper.class);

  @Override
  public Brand map(BrandForm brandForm) {
    LOG.info("Map Brand Form to Brand Entity");
    Brand brand = new Brand();
    brand.setBrandName(brandForm.getBrandName());
    brand.setCategory(Long.valueOf(brandForm.getCategory()));
    brand.setActive(brandForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    brand.setCreatedBy(getCurrentUser());
    brand.setCreatedDate(getInstant());
    brand.setUpdatedBy(getCurrentUser());
    brand.setUpdatedDate(getInstant());
    brand.setDeleted(YNStatus.NO.getStatus());
    return brand;
  }

  @Override
  public Brand map(BrandForm brandForm, Brand brand) {

    LOG.info("Map Brand Form to Updated Brand Entity");
    brand.setBrandName(brandForm.getBrandName());
    // brand.setCategory(Long.valueOf(brandForm.getCategory()));
    brand.setActive(brandForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    brand.setUpdatedBy(getCurrentUser());
    brand.setUpdatedDate(getInstant());
    brand.setDeleted(YNStatus.NO.getStatus());
    return brand;
  }

  @Override
  public BrandForm remap(Brand brand) {
    LOG.info("Map Brand Entity to Brand Form");
    BrandForm brandForm = new BrandForm();
    brandForm.setBrandID(String.valueOf(brand.getId()));
    brandForm.setBrandName(brand.getBrandName());
    brandForm.setCategory(String.valueOf(brand.getCategory()));
    brandForm.setActive(brand.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return brandForm;
  }
}
