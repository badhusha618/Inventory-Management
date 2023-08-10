/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.crusher;

import com.makinus.unitedsupplies.common.data.dao.CrusherRepository;
import com.makinus.unitedsupplies.common.data.entity.Crusher;
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
public class CrusherServiceImpl implements CrusherService {

    private final Logger LOG = LogManager.getLogger(CrusherServiceImpl.class);

    private final CrusherRepository crusherRepository;

    public CrusherServiceImpl(@Autowired CrusherRepository crusherRepository) {
        this.crusherRepository = crusherRepository;
    }

    @Override
    public boolean isCrusherExists(String crusher, Long category) {
        LOG.info("Check if UnitedSupplies Crusher is available from database");
        Optional<Crusher> findCrusher = crusherRepository.findAvailableCategoryForCrusher(crusher, category);
        return findCrusher.isPresent();
    }

    @Override
    public Crusher saveCrusher(Crusher crusher) {
        LOG.info("Saving Crusher in the database");
        Crusher savedCrusher = crusherRepository.save(crusher);
        LOG.info("Saved Crusher in the database");
        return savedCrusher;
    }

    @Override
    public List<Crusher> crusherList() {
        LOG.info("List Crushers from database");
        return crusherRepository.listAllCrushers();
    }

    @Override
    public List<Crusher> crusherListByCategory(Long categoryId) {
        LOG.info("List Crushers By Category from database");
        return crusherRepository.listAllCrushersByCategory(categoryId);
    }

    @Override
    public Crusher updateCrusher(final Crusher crusher) {
        LOG.info("Update existing crusher in the catalog");
        return crusherRepository.save(crusher);
    }

    @Override
    public Crusher findCrusher(Long id) throws UnitedSuppliesException {
        Optional<Crusher> crusherOptional = crusherRepository.findById(id);
        if (crusherOptional.isPresent()) {
            return crusherOptional.get();
        }
        throw new UnitedSuppliesException(String.format("Crusher is not found with the id %d", id));
    }

    @Override
    public Crusher removeCrusher(Long id) throws UnitedSuppliesException {
        Optional<Crusher> crusherOptional = crusherRepository.findById(id);
        if (crusherOptional.isPresent()) {
            Crusher crusher = crusherOptional.get();
            crusher.setDeleted(YNStatus.YES.getStatus());
            crusher.setUpdatedBy(getCurrentUser());
            crusher.setUpdatedDate(getInstant());
            return crusher;
        }
        throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
    }
}
