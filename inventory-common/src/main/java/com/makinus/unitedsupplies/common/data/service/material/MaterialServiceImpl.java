/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.material;

import com.makinus.unitedsupplies.common.data.dao.MaterialRepository;
import com.makinus.unitedsupplies.common.data.entity.Material;
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
public class MaterialServiceImpl implements MaterialService {

    private final Logger LOG = LogManager.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(@Autowired MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public boolean isMaterialExists(String material, Long category) {
        LOG.info("Check if UnitedSupplies Material is available from database");
        Optional<Material> findMaterial = materialRepository.findAvailableCategoryForMaterial(material, category);
        return findMaterial.isPresent();
    }

    @Override
    public Material saveMaterial(Material material) {
        LOG.info("Saving Material in the database");
        Material savedMaterial = materialRepository.save(material);
        LOG.info("Saved Material in the database");
        return savedMaterial;
    }

    @Override
    public List<Material> materialList() {
        LOG.info("List Material from database");
        return materialRepository.listAllMaterials();
    }

    @Override
    public List<Material> materialListByCategory(Long categoryId) {
        LOG.info("List Material By Category from database");
        return materialRepository.listAllMaterialsByCategory(categoryId);
    }

    @Override
    public Material updateMaterial(final Material material) {
        LOG.info("Update existing material in the catalog");
        return materialRepository.save(material);
    }

    @Override
    public Material findMaterial(Long id) throws InventoryException {
        Optional<Material> materialOptional = materialRepository.findById(id);
        if (materialOptional.isPresent()) {
            return materialOptional.get();
        }
        throw new InventoryException(String.format("Material is not found with the id %d", id));
    }

    @Override
    public Material removeMaterial(Long id) throws InventoryException {
        Optional<Material> materialOptional = materialRepository.findById(id);
        if (materialOptional.isPresent()) {
            Material material = materialOptional.get();
            material.setDeleted(YNStatus.YES.getStatus());
            material.setUpdatedBy(getCurrentUser());
            material.setUpdatedDate(getInstant());
            return material;
        }
        throw new InventoryException(String.format("Promotion is not found with the id %d", id));
    }
}
