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

import com.makinus.unitedsupplies.admin.data.forms.MaterialForm;
import com.makinus.unitedsupplies.common.data.entity.Material;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/** Created by ammar */
@Component
@Qualifier("MaterialMapper")
public class MaterialMapper
    implements EntityMapper<MaterialForm, Material>,
        EntityUpdateMapper<MaterialForm, Material>,
        EntityRemapper<Material, MaterialForm> {

  private final Logger LOG = LogManager.getLogger(MaterialMapper.class);

  @Override
  public Material map(MaterialForm materialForm) throws UnitedSuppliesException {
    LOG.info("Map Material Form to Material Entity");
    Material material = new Material();
    material.setMaterial(materialForm.getMaterial());
    material.setCategory(Long.valueOf(materialForm.getCategory()));
    material.setActive(materialForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    material.setCreatedBy(getCurrentUser());
    material.setCreatedDate(getInstant());
    material.setUpdatedBy(getCurrentUser());
    material.setUpdatedDate(getInstant());
    material.setDeleted(YNStatus.NO.getStatus());
    return material;
  }

  @Override
  public Material map(MaterialForm materialForm, Material material) throws UnitedSuppliesException {

    LOG.info("Map Material Form to Updated Material Entity");
    material.setMaterial(materialForm.getMaterial());
    // material.setCategory(Long.valueOf(materialForm.getCategory()));
    material.setActive(materialForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    material.setUpdatedBy(getCurrentUser());
    material.setUpdatedDate(getInstant());
    material.setDeleted(YNStatus.NO.getStatus());
    return material;
  }

  @Override
  public MaterialForm remap(Material material) throws UnitedSuppliesException {
    LOG.info("Map Material Entity to Material Form");
    MaterialForm materialForm = new MaterialForm();
    materialForm.setMaterialID(String.valueOf(material.getId()));
    materialForm.setMaterial(material.getMaterial());
    materialForm.setCategory(String.valueOf(material.getCategory()));
    materialForm.setActive(material.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return materialForm;
  }
}
