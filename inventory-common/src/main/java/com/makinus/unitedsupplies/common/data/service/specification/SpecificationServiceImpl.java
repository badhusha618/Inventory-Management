/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.specification;


import com.makinus.unitedsupplies.common.data.dao.SpecificationRepository;
import com.makinus.unitedsupplies.common.data.entity.Specification;
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
public class SpecificationServiceImpl implements SpecificationService {

    private final Logger LOG = LogManager.getLogger(SpecificationServiceImpl.class);

    private final SpecificationRepository specificationRepository;

    public SpecificationServiceImpl(@Autowired SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Override
    public Specification saveSpecification(Specification specification) {
        LOG.info("Saving Specification in the database");
        Specification savedSpecification = specificationRepository.save(specification);
        LOG.info("Saved Specification in the database");
        return savedSpecification;
    }

    @Override
    public List<Specification> specificationList() {
        LOG.info("List Specification from database");
        return specificationRepository.listAllSpecifications();
    }

    @Override
    public List<Specification> specificationListByCategory(Long categoryId) {
        LOG.info("List Specification By Category from database");
        return specificationRepository.listAllSpecificationsByCategory(categoryId);
    }

    @Override
    public Specification updateSpecification(final Specification specification) {
        LOG.info("Update existing specification in the catalog");
        return specificationRepository.save(specification);
    }

    @Override
    public Specification findSpecification(Long id) throws InventoryException {
        Optional<Specification> specificationOptional = specificationRepository.findById(id);
        if (specificationOptional.isPresent()) {
            return specificationOptional.get();
        }
        throw new InventoryException(String.format("Specification is not found with the id %d", id));
    }

    @Override
    public Specification removeSpecification(Long id) throws InventoryException {
        Optional<Specification> specificationOptional = specificationRepository.findById(id);
        if (specificationOptional.isPresent()) {
            Specification specification = specificationOptional.get();
            specification.setDeleted(YNStatus.YES.getStatus());
            specification.setUpdatedBy(getCurrentUser());
            specification.setUpdatedDate(getInstant());
            return specification;
        }
        throw new InventoryException(String.format("Promotion is not found with the id %d", id));
    }
}
