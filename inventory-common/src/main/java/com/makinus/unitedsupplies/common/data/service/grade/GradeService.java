/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.grade;

import com.makinus.unitedsupplies.common.data.entity.Grade;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface GradeService {

    boolean isGradeExists(final String grade, final Long category);

    Grade saveGrade(final Grade grade);

    List<Grade> gradeList();

    List<Grade> gradeListByCategory(Long categoryId);

    Grade updateGrade(final Grade grade);

    Grade findGrade(Long id) throws InventoryException;

    Grade removeGrade(Long id) throws InventoryException;
}
