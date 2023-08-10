/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.mapping;

import static com.makinus.Inventory.common.utils.AppUtils.getCurrentUser;
import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

import com.makinus.Inventory.admin.data.forms.GradeForm;
import com.makinus.Inventory.common.data.entity.Grade;
import com.makinus.Inventory.common.data.mapper.EntityMapper;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
@Qualifier("GradeMapper")
public class GradeMapper
    implements EntityMapper<GradeForm, Grade>,
        EntityUpdateMapper<GradeForm, Grade>,
        EntityRemapper<Grade, GradeForm> {

  private final Logger LOG = LogManager.getLogger(GradeMapper.class);

  @Override
  public Grade map(GradeForm gradeForm) throws InventoryException {
    LOG.info("Map Grade Form to Grade Entity");
    Grade grade = new Grade();
    grade.setGrade(gradeForm.getGrade());
    grade.setCategory(Long.valueOf(gradeForm.getCategory()));
    grade.setActive(gradeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    grade.setCreatedBy(getCurrentUser());
    grade.setCreatedDate(getInstant());
    grade.setUpdatedBy(getCurrentUser());
    grade.setUpdatedDate(getInstant());
    grade.setDeleted(YNStatus.NO.getStatus());
    return grade;
  }

  @Override
  public Grade map(GradeForm gradeForm, Grade grade) throws InventoryException {

    LOG.info("Map Grade Form to Updated Grade Entity");
    grade.setGrade(gradeForm.getGrade());
    // grade.setCategory(Long.valueOf(gradeForm.getCategory()));
    grade.setActive(gradeForm.isActive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
    grade.setUpdatedBy(getCurrentUser());
    grade.setUpdatedDate(getInstant());
    grade.setDeleted(YNStatus.NO.getStatus());
    return grade;
  }

  @Override
  public GradeForm remap(Grade grade) throws InventoryException {
    LOG.info("Map Grade Entity to Grade Form");
    GradeForm gradeForm = new GradeForm();
    gradeForm.setGradeID(String.valueOf(grade.getId()));
    gradeForm.setGrade(grade.getGrade());
    gradeForm.setCategory(String.valueOf(grade.getCategory()));
    gradeForm.setActive(grade.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
    return gradeForm;
  }
}
