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

import com.makinus.Inventory.admin.data.forms.QualityForm;
import com.makinus.Inventory.admin.data.mapping.QualityMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Quality;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.quality.QualityService;
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
public class QualityController {

    private final Logger LOG = LogManager.getLogger(QualityController.class);

    private static final String QUALITY_PAGE = "dashboard/quality/quality";
    private static final String QUALITY_EDIT_PAGE = "dashboard/quality/quality-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QualityService qualityService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("QualityMapper")
    private QualityMapper qualityMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/quality.mk")
    public String quality(ModelMap model) throws InventoryException {
        LOG.info("Open Quality page - {}", this.getClass().getSimpleName());
        QualityForm qualityForm = new QualityForm();
        qualityForm.setActive(Boolean.TRUE);
        model.addAttribute("qualityList", qualityService.qualityList());
        model.addAttribute("qualityForm", qualityForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return QUALITY_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/quality.mk")
    public String addNewQuality(@ModelAttribute("qualityForm") QualityForm qualityForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Quality - {}", this.getClass().getSimpleName());
        Quality savedQuality = qualityService.saveQuality(qualityMapper.map(qualityForm));
        redirectAttrs.addFlashAttribute("qualityName", qualityForm.getQuality());
        LOG.debug("Quality Details {}", savedQuality.toString());
        return "redirect:/quality.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/quality.mk", produces = "application/json")
    @ResponseBody
    public Boolean isQualityExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for quality exists");
        boolean isQualityExists = qualityService.isQualityExists(request.getParameter("quality").trim(), Long.parseLong(category));
        LOG.info("Quality is available? {}", isQualityExists);
        return !isQualityExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/quality.mk")
    public String editQualityPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String qualityId) throws InventoryException {
        LOG.info("Open Edit Quality page - {}", this.getClass().getSimpleName());
        QualityForm savedQuality = qualityMapper.remap(qualityService.findQuality(Long.valueOf(qualityId)));
        model.addAttribute("editQualityForm", savedQuality);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("qualityFromDB", savedQuality.getQuality());
        return QUALITY_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/quality.mk")
    public String updateQuality(@ModelAttribute("editQualityForm") QualityForm qualityForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Quality page - {}", this.getClass().getSimpleName());
        Quality updateQuality = qualityService.findQuality(Long.valueOf(qualityForm.getQualityID()));
        Quality savedQuality = qualityService.updateQuality(qualityMapper.map(qualityForm, updateQuality));
        redirectAttrs.addFlashAttribute("qualityName", qualityForm.getQuality());
        redirectAttrs.addFlashAttribute("editQualityFlag", Boolean.TRUE);
        LOG.debug("Updated Quality Details {}", savedQuality.toString());
        return "redirect:/quality.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/quality.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeQuality(@PathVariable String id) {
        LOG.info("Remove Quality from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Quality removedQuality = qualityService.removeQuality(Long.valueOf(id));
            LOG.info("Quality is removed? {}", (removedQuality != null && removedQuality.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
