/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.type;

import com.makinus.unitedsupplies.common.data.dao.TypeRepository;
import com.makinus.unitedsupplies.common.data.entity.Type;
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
public class TypeServiceImpl implements TypeService {

    private final Logger LOG = LogManager.getLogger(TypeServiceImpl.class);

    private final TypeRepository typeRepository;

    public TypeServiceImpl(@Autowired TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public boolean isTypeExists(String type, Long category) {
        LOG.info("Check if UnitedSupplies Type is available from database");
        Optional<Type> findType = typeRepository.findAvailableCategoryForType(type, category);
        return findType.isPresent();
    }

    @Override
    public Type saveType(Type type) {
        LOG.info("Saving Type in the database");
        Type savedType = typeRepository.save(type);
        LOG.info("Saved Type in the database");
        return savedType;
    }

    @Override
    public List<Type> typeList() {
        LOG.info("List Types from database");
        return typeRepository.listAllTypes();
    }

    @Override
    public List<Type> typeListByCategory(Long categoryId) {
        LOG.info("List Types By Category from database");
        return typeRepository.listAllTypesByCategory(categoryId);
    }

    @Override
    public Type updateType(final Type type) {
        LOG.info("Update existing type in the catalog");
        return typeRepository.save(type);
    }

    @Override
    public Type findType(Long id) throws UnitedSuppliesException {
        Optional<Type> typeOptional = typeRepository.findById(id);
        if (typeOptional.isPresent()) {
            return typeOptional.get();
        }
        throw new UnitedSuppliesException(String.format("Type is not found with the id %d", id));
    }

    @Override
    public Type removeType(Long id) throws UnitedSuppliesException {
        Optional<Type> typeOptional = typeRepository.findById(id);
        if (typeOptional.isPresent()) {
            Type type = typeOptional.get();
            type.setDeleted(YNStatus.YES.getStatus());
            type.setUpdatedBy(getCurrentUser());
            type.setUpdatedDate(getInstant());
            return type;
        }
        throw new UnitedSuppliesException(String.format("Promotion is not found with the id %d", id));
    }
}
