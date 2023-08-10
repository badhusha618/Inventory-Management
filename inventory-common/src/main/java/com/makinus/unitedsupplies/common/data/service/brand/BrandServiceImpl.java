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

import com.makinus.unitedsupplies.common.data.dao.BrandRepository;
import com.makinus.unitedsupplies.common.data.entity.Brand;
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
public class BrandServiceImpl implements BrandService {

    private final Logger LOG = LogManager.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    public BrandServiceImpl(@Autowired BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public boolean isBrandExists(String brandName, Long category) {
        LOG.info("Check if UnitedSupplies Brand is available from database");
        Optional<Brand> findBrand = brandRepository.findAvailableCategoryForBrand(brandName, category);
        return findBrand.isPresent();
    }

    @Override
    public Brand saveBrand(Brand brand) {
        LOG.info("Saving Brand in the database");
        Brand savedBrand = brandRepository.save(brand);
        LOG.info("Saved Brand in the database");
        return savedBrand;
    }

    @Override
    public List<Brand> brandList() {
        LOG.info("List Brands from database");
        return brandRepository.listAllBrands();
    }

    @Override
    public List<Brand> brandListByCategory(Long categoryId) {
        LOG.info("List Brands by Category from database");
        return brandRepository.listAllBrandsByCategory(categoryId);
    }

    @Override
    public Brand updateBrand(final Brand brand) {
        LOG.info("Update existing brand in the catalog");
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrand(Long id) throws UnitedSuppliesException {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            return brandOptional.get();
        }
        throw new UnitedSuppliesException(String.format("Brand is not found with the id %d", id));
    }

    public Brand removeBrand(Long id) throws UnitedSuppliesException {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brand.setDeleted(YNStatus.YES.getStatus());
            brand.setUpdatedBy(getCurrentUser());
            brand.setUpdatedDate(getInstant());
            return brand;
        }
        throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
    }
}
