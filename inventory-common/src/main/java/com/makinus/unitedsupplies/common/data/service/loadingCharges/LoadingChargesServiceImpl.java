/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.loadingCharges;

import com.makinus.unitedsupplies.common.data.dao.LoadingChargesRepository;
import com.makinus.unitedsupplies.common.data.entity.LoadingCharges;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.exception.InventoryException;
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
import static java.lang.String.format;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class LoadingChargesServiceImpl implements LoadingChargesService {

    private final Logger LOG = LogManager.getLogger(LoadingChargesServiceImpl.class);

    private final LoadingChargesRepository loadingChargesRepository;

    public LoadingChargesServiceImpl(@Autowired LoadingChargesRepository loadingChargesRepository) {
        this.loadingChargesRepository = loadingChargesRepository;
    }

    @Override
    public boolean isQuantityExists(Integer quantity, Long productId) {
        LOG.info("Check if UnitedSupplies LoadingCharges is available from database");
        Optional<LoadingCharges> findLoadingCharges = loadingChargesRepository.findAvailableQuantityForProduct(quantity, productId);
        return findLoadingCharges.isPresent();
    }

    @Override
    public List<LoadingCharges> loadingChargesList() {
        LOG.info("List all LoadingCharges from database");
        return loadingChargesRepository.listAllLoadingCharges();
    }

    @Override
    public List<LoadingCharges> loadingChargesListByProduct(List<Long> productIds) {
        LOG.info("List all LoadingCharges by Product Ids from database");
        return loadingChargesRepository.listAllLoadingChargesByProducts(productIds);
    }

    @Override
    public Optional<Tuple<BigDecimal, Integer>> availableQuantityListByProduct(Integer quantity, Long productId) {
        LOG.info("List all Available Quantity by Product Id from database");
        return loadingChargesRepository.listAvailableQuantityForProduct(quantity, productId);
    }

    @Override
    public List<LoadingCharges> addNewLoadingCharges(List<LoadingCharges> loadingCharges) {
        LOG.info("List all Loading Charges by Product Ids from database");
        return loadingChargesRepository.saveAll(loadingCharges);
    }

    @Override
    public LoadingCharges findLoadingCharges(Long id) throws InventoryException {
        Optional<LoadingCharges> loadingChargesOptional = loadingChargesRepository.findById(id);
        if (loadingChargesOptional.isPresent()) {
            LoadingCharges loadingCharges = loadingChargesOptional.get();
            return loadingCharges;
        }
        throw new InventoryException(format("loading-charges is not found with the id %d", id));
    }

    @Override
    public LoadingCharges updateLoadingCharges(LoadingCharges loadingCharges) {
        LOG.info("Update existing LoadingCharges in the catalog");
        return loadingChargesRepository.save(loadingCharges);
    }

    @Override
    public LoadingCharges removeLoadingCharges(Long id) throws InventoryException {
        Optional<LoadingCharges> loadingChargesOptional = loadingChargesRepository.findById(id);
        if (loadingChargesOptional.isPresent()) {
            LoadingCharges loadingCharges = loadingChargesOptional.get();
            loadingCharges.setDeleted(YNStatus.YES.getStatus());
            loadingCharges.setUpdatedBy(getCurrentUser());
            loadingCharges.setUpdatedDate(getInstant());
            return loadingCharges;
        }
        throw new InventoryException(format("LoadingCharges is not found with the id %s", id));
    }
}
