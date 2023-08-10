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

import com.makinus.unitedsupplies.common.data.dao.GradeRepository;
import com.makinus.unitedsupplies.common.data.entity.Grade;
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
public class GradeServiceImpl implements GradeService {

    private final Logger LOG = LogManager.getLogger(GradeServiceImpl.class);

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(@Autowired GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public boolean isGradeExists(String grade, Long category) {
        LOG.info("Check if UnitedSupplies Grade is available from database");
        Optional<Grade> findGrade = gradeRepository.findAvailableCategoryForGrade(grade, category);
        return findGrade.isPresent();
    }

    @Override
    public Grade saveGrade(Grade grade) {
        LOG.info("Saving Grade in the database");
        Grade savedGrade = gradeRepository.save(grade);
        LOG.info("Saved Grade in the database");
        return savedGrade;
    }

    @Override
    public List<Grade> gradeList() {
        LOG.info("List Grades from database");
        return gradeRepository.listAllGrades();
    }

    @Override
    public List<Grade> gradeListByCategory(Long categoryId) {
        LOG.info("List Grades By Category from database");
        return gradeRepository.listAllGradesByCategory(categoryId);
    }

    public Grade updateGrade(final Grade grade) {
        LOG.info("Update existing grade in the catalog");
        return gradeRepository.save(grade);
    }

    @Override
    public Grade findGrade(Long id) throws InventoryException {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if (gradeOptional.isPresent()) {
            return gradeOptional.get();
        }
        throw new InventoryException(String.format("Grade is not found with the id %d", id));
    }

    @Override
    public Grade removeGrade(Long id) throws InventoryException {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            grade.setDeleted(YNStatus.YES.getStatus());
            grade.setUpdatedBy(getCurrentUser());
            grade.setUpdatedDate(getInstant());
            return grade;
        }
        throw new InventoryException(String.format("Promotion is not found with the id %d", id));
    }
}
