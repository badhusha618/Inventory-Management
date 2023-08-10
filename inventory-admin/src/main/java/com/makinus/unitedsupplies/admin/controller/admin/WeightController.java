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

import com.makinus.Inventory.admin.data.forms.WeightForm;
import com.makinus.Inventory.admin.data.mapping.WeightMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Weight;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.weight.WeightService;
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
public class WeightController {

    private final Logger LOG = LogManager.getLogger(WeightController.class);

    private static final String WEIGHT_PAGE = "dashboard/weight/weight";
    private static final String WEIGHT_EDIT_PAGE = "dashboard/weight/weight-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("WeightMapper")
    private WeightMapper weightMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/weight.mk")
    public String weight(ModelMap model) throws InventoryException {
        LOG.info("Open Weight page - {}", this.getClass().getSimpleName());
        WeightForm weightForm = new WeightForm();
        weightForm.setActive(Boolean.TRUE);
        model.addAttribute("weightList", weightService.weightList());
        model.addAttribute("weightForm", weightForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return WEIGHT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/weight.mk")
    public String addNewWeight(@ModelAttribute("weightForm") WeightForm weightForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Weight - {}", this.getClass().getSimpleName());
        Weight savedWeight = weightService.saveWeight(weightMapper.map(weightForm));
        redirectAttrs.addFlashAttribute("weightName", weightForm.getWeight());
        LOG.debug("Weight Details {}", savedWeight.toString());
        return "redirect:/weight.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/weight.mk", produces = "application/json")
    @ResponseBody
    public Boolean isWeightExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for weight exists");
        boolean isWeightExists = weightService.isWeightExists(request.getParameter("weight").trim(), Long.parseLong(category));
        LOG.info("Weight is available? {}", isWeightExists);
        return !isWeightExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/weight.mk")
    public String editWeightPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String weightId) throws InventoryException {
        LOG.info("Open Edit Weight page - {}", this.getClass().getSimpleName());
        WeightForm savedWeight = weightMapper.remap(weightService.findWeight(Long.valueOf(weightId)));
        model.addAttribute("editWeightForm", savedWeight);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("weightFromDB", savedWeight.getWeight());
        return WEIGHT_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/weight.mk")
    public String updateWeight(@ModelAttribute("editWeightForm") WeightForm weightForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Weight page - {}", this.getClass().getSimpleName());
        Weight updateWeight = weightService.findWeight(Long.valueOf(weightForm.getWeightID()));
        Weight savedWeight = weightService.updateWeight(weightMapper.map(weightForm, updateWeight));
        redirectAttrs.addFlashAttribute("weightName", weightForm.getWeight());
        redirectAttrs.addFlashAttribute("editWeightlag", Boolean.TRUE);
        LOG.debug("Updated Weight Details {}", savedWeight.toString());
        return "redirect:/weight.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/weight.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeWeight(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Weight from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Weight removedWeight = weightService.removeWeight(Long.valueOf(id));
            LOG.info("Weight is removed? {}", (removedWeight != null && removedWeight.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
