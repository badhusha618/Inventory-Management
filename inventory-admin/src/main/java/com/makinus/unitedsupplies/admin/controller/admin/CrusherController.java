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

import com.makinus.Inventory.admin.data.forms.CrusherForm;
import com.makinus.Inventory.admin.data.mapping.CrusherMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Crusher;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.crusher.CrusherService;
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
public class CrusherController {

    private final Logger LOG = LogManager.getLogger(CrusherController.class);

    private static final String CRUSHER_PAGE = "dashboard/crusher/crusher";
    private static final String CRUSHER_EDIT_PAGE = "dashboard/crusher/crusher-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CrusherService crusherService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("CrusherMapper")
    private CrusherMapper crusherMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream()
                .collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/crusher.mk")
    public String crusher(ModelMap model) throws InventoryException {
        LOG.info("Open Crusher page - {}", this.getClass().getSimpleName());
        CrusherForm crusherForm = new CrusherForm();
        crusherForm.setActive(Boolean.TRUE);
        model.addAttribute("crusherList", crusherService.crusherList());
        model.addAttribute("crusherForm", crusherForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return CRUSHER_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/crusher.mk")
    public String addNewCrusher(@ModelAttribute("crusherForm") CrusherForm crusherForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Crusher - {}", this.getClass().getSimpleName());
        Crusher savedCrusher = crusherService.saveCrusher(crusherMapper.map(crusherForm));
        redirectAttrs.addFlashAttribute("crusherName", crusherForm.getCrusher());
        LOG.debug("Crusher Details {}", savedCrusher.toString());
        return "redirect:/crusher.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/crusher.mk")
    public String editCrusherPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String crusherId) throws InventoryException {
        LOG.info("Open Edit Crusher page - {}", this.getClass().getSimpleName());
        CrusherForm savedCrusher = crusherMapper.remap(crusherService.findCrusher(Long.valueOf(crusherId)));
        model.addAttribute("editCrusherForm", savedCrusher);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("crusherFromDB", savedCrusher.getCrusher());
        return CRUSHER_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/crusher.mk")
    public String updateCrusher(@ModelAttribute("editCrusherForm") CrusherForm crusherForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Crusher page - {}", this.getClass().getSimpleName());
        Crusher updateCrusher = crusherService.findCrusher(Long.valueOf(crusherForm.getCrusherID()));
        Crusher savedCrusher = crusherService.updateCrusher(crusherMapper.map(crusherForm, updateCrusher));
        redirectAttrs.addFlashAttribute("crusherName", crusherForm.getCrusher());
        redirectAttrs.addFlashAttribute("editCrusherFlag", Boolean.TRUE);
        LOG.debug("Updated Crusher Details {}", savedCrusher.toString());
        return "redirect:/crusher.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/crusher.mk", produces = "application/json")
    @ResponseBody
    public Boolean isCrusherExits(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for crusher exists");
        boolean isCrusherExits = crusherService.isCrusherExists(request.getParameter("crusher").trim(), Long.parseLong(category));
        LOG.info("Crusher is available? {}", isCrusherExits);
        return !isCrusherExits;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/crusher.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeCrusher(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Crusher from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Crusher removedCrusher = crusherService.removeCrusher(Long.valueOf(id));
            LOG.info("Crusher is removed? {}", (removedCrusher != null && removedCrusher.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
