/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.admin;

import com.makinus.Inventory.admin.data.forms.GradeForm;
import com.makinus.Inventory.admin.data.mapping.GradeMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Grade;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.grade.GradeService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.makinus.Inventory.admin.utils.AdminUtils.subCategoryMap;

/**
 * @author Bad_sha
 */
@Controller
public class GradeController {

    private final Logger LOG = LogManager.getLogger(GradeController.class);

    private static final String GRADE_PAGE = "dashboard/grade/grade";
    private static final String GRADE_EDIT_PAGE = "dashboard/grade/grade-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("GradeMapper")
    private GradeMapper gradeMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/grade.mk")
    public String grade(ModelMap model) throws InventoryException {
        LOG.info("Open Grade page - {}", this.getClass().getSimpleName());
        GradeForm gradeForm = new GradeForm();
        gradeForm.setActive(Boolean.TRUE);
        model.addAttribute("gradeList", gradeService.gradeList());
        model.addAttribute("gradeForm", gradeForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return GRADE_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/grade.mk")
    public String addNewGrade(@ModelAttribute("gradeForm") GradeForm gradeForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Grade - {}", this.getClass().getSimpleName());
        Grade savedGrade = gradeService.saveGrade(gradeMapper.map(gradeForm));
        redirectAttrs.addFlashAttribute("gradeName", gradeForm.getGrade());
        LOG.debug("Grade Details {}", savedGrade.toString());
        return "redirect:/grade.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/grade.mk")
    public String editGradePage(ModelMap model, HttpServletRequest request, @PathVariable("id") String gradeId) throws InventoryException {
        LOG.info("Open Edit Grade page - {}", this.getClass().getSimpleName());
        GradeForm savedGrade = gradeMapper.remap(gradeService.findGrade(Long.valueOf(gradeId)));
        model.addAttribute("editGradeForm", savedGrade);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("gradeFromDB", savedGrade.getGrade());
        return GRADE_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/grade.mk", produces = "application/json")
    @ResponseBody
    public Boolean isGradeExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for grade exists");
        boolean isGradeExists = gradeService.isGradeExists(request.getParameter("grade").trim(), Long.parseLong(category));
        LOG.info("Grade is available? {}", isGradeExists);
        return !isGradeExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/grade.mk")
    public String updateGrade(@ModelAttribute("editGradeForm") GradeForm gradeForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Grade page - {}", this.getClass().getSimpleName());
        Grade updateGrade = gradeService.findGrade(Long.valueOf(gradeForm.getGradeID()));
        Grade savedGrade = gradeService.updateGrade(gradeMapper.map(gradeForm, updateGrade));
        redirectAttrs.addFlashAttribute("gradeName", gradeForm.getGrade());
        redirectAttrs.addFlashAttribute("editGradeFlag", Boolean.TRUE);
        LOG.debug("Updated Grade Details {}", savedGrade.toString());
        return "redirect:/grade.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/grade.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeGrade(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Grade from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Grade removedGrade = gradeService.removeGrade(Long.valueOf(id));
            LOG.info("Grade is removed? {}", (removedGrade != null && removedGrade.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
