/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productvendor;

import com.makinus.unitedsupplies.common.data.dao.ProductVendorRepository;
import com.makinus.unitedsupplies.common.data.entity.ProductVendor;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by abuabdul
 **/
@Service
@Transactional
public class ProductVendorServiceImpl implements ProductVendorService {

    private final Logger LOG = LogManager.getLogger(ProductVendorServiceImpl.class);

    private final ProductVendorRepository productVendorRepository;

    public ProductVendorServiceImpl(@Autowired ProductVendorRepository productVendorRepository) {
        this.productVendorRepository = productVendorRepository;
    }

    @Override
    public ProductVendor saveProductVendor(ProductVendor productVendor) {
        LOG.info("Saving ProductVendor in the database");
        ProductVendor savedProductVendor = productVendorRepository.save(productVendor);
        LOG.info("Saved ProductVendor in the database");
        return savedProductVendor;
    }

    @Override
    public List<ProductVendor> saveProductVendorList(List<ProductVendor> productVendors) {
        LOG.info("Save ProductVendor List in the database");
        return productVendorRepository.saveAll(productVendors);
    }

    @Override
    public List<ProductVendor> updateProductVendorList(List<ProductVendor> productVendors) {
        LOG.info("Update ProductVendor List in the database");
        return productVendorRepository.saveAll(productVendors);
    }

    @Override
    public List<ProductVendor> productVendorList() {
        LOG.info("List ProductVendors from database");
        return productVendorRepository.listAllProductVendors();
    }

    @Override
    public Double findMaxSaleRateForProducts(List<Long> productIds) {
        LOG.info("Maximum sale among list of products from database");
        return productVendorRepository.findMaxSaleRateForProducts(productIds);
    }

    @Override
    public List<Long> findMaxSaleRateForProducts(BigDecimal maxSaleRate, BigDecimal minSaleRate) {
        LOG.info("Get product ids by sale rates range from database");
        return productVendorRepository.findProductIdsBySaleRatesRange(maxSaleRate, minSaleRate);
    }

    @Override
    public List<ProductVendor> productVendorListByProductIds(List<Long> prodIds){
        LOG.info("Get Product Vendor list By Product Ids from database");
        return productVendorRepository.findByProdIds(prodIds);
    }

    @Override
    public List<ProductVendor> productVendorListByProduct(Long prodId) {
        LOG.info("Get ProductVendor By Product Id from database");
        return productVendorRepository.findByProdId(prodId);
    }

    @Override
    public ProductVendor updateProductVendor(final ProductVendor productVendor) {
        LOG.info("Update existing ProductVendor in the database");
        return productVendorRepository.save(productVendor);
    }

    @Override
    public ProductVendor findProductVendor(Long id) throws UnitedSuppliesException {
        Optional<ProductVendor> productVendorOptional = productVendorRepository.findById(id);
        if (productVendorOptional.isPresent()) {
            return productVendorOptional.get();
        }
        throw new UnitedSuppliesException(
                String.format("ProductVendor is not found with the id %d", id));
    }

    @Override
    public ProductVendor findDefaultProductVendor(Long id) throws UnitedSuppliesException {
        Optional<ProductVendor> productVendorOptional = productVendorRepository.findDefaultByProdId(id);
        if (productVendorOptional.isPresent()) {
            return productVendorOptional.get();
        }
        throw new UnitedSuppliesException(
                String.format("ProductVendor is not found with the id %d", id));
    }

    @Override
    public ProductVendor removeProductVendor(Long id) throws UnitedSuppliesException {
        Optional<ProductVendor> productVendorOptional = productVendorRepository.findById(id);
        if (productVendorOptional.isPresent()) {
            ProductVendor productVendor = productVendorOptional.get();
            productVendor.setDeleted(YNStatus.YES.getStatus());
            productVendor.setUpdatedBy(getCurrentUser());
            productVendor.setUpdatedDate(getInstant());
            return productVendor;
        }
        throw new UnitedSuppliesException(
                String.format("ProductVendor is not found with the id %d", id));
    }
}
