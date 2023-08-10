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

import com.makinus.Inventory.admin.data.forms.ColorForm;
import com.makinus.Inventory.admin.data.mapping.ColorMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Color;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.color.ColorService;
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
public class ColorController {

    private final Logger LOG = LogManager.getLogger(ColorController.class);

    private static final String COLOR_PAGE = "dashboard/color/color";
    private static final String COLOR_EDIT_PAGE = "dashboard/color/color-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("ColorMapper")
    private ColorMapper colorMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/color.mk")
    public String color(ModelMap model) throws InventoryException {
        LOG.info("Open Color page - {}", this.getClass().getSimpleName());
        ColorForm colorForm = new ColorForm();
        colorForm.setActive(Boolean.TRUE);
        model.addAttribute("colorList", colorService.colorList());
        model.addAttribute("colorForm", colorForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return COLOR_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/color.mk")
    public String addNewColor(@ModelAttribute("colorForm") ColorForm colorForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Color - {}", this.getClass().getSimpleName());
        Color savedColor = colorService.saveColor(colorMapper.map(colorForm));
        redirectAttrs.addFlashAttribute("colorName", colorForm.getColor());
        LOG.debug("Color Details {}", savedColor.toString());
        return "redirect:/color.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/color.mk", produces = "application/json")
    @ResponseBody
    public Boolean isColorExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for crusher exists");
        boolean isColorExists = colorService.isColorExists(request.getParameter("color").trim(), Long.parseLong(category));
        LOG.info("Crusher is available? {}", isColorExists);
        return !isColorExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/color.mk")
    public String editColorPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String colorId) throws InventoryException {
        LOG.info("Open Edit Color page - {}", this.getClass().getSimpleName());
        ColorForm savedColor = colorMapper.remap(colorService.findColor(Long.valueOf(colorId)));
        model.addAttribute("editColorForm", savedColor);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("colorFromDB", savedColor.getColor());
        return COLOR_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/color.mk")
    public String updateColor(@ModelAttribute("editColorForm") ColorForm colorForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Color page - {}", this.getClass().getSimpleName());
        Color updateColor = colorService.findColor(Long.valueOf(colorForm.getColorID()));
        Color savedColor = colorService.updateColor(colorMapper.map(colorForm, updateColor));
        redirectAttrs.addFlashAttribute("colorName", colorForm.getColor());
        redirectAttrs.addFlashAttribute("editColorFlag", Boolean.TRUE);
        LOG.debug("Updated Color Details {}", savedColor.toString());
        return "redirect:/color.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/color.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeColor(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Color from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Color removedColor = colorService.removeColor(Long.valueOf(id));
            LOG.info("Color is removed? {}", (removedColor != null && removedColor.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
