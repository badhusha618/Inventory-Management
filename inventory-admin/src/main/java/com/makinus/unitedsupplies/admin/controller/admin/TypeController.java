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

import com.makinus.Inventory.admin.data.forms.TypeForm;
import com.makinus.Inventory.admin.data.mapping.TypeMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Type;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.type.TypeService;
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
public class TypeController {

    private final Logger LOG = LogManager.getLogger(TypeController.class);

    private static final String TYPE_PAGE = "dashboard/type/type";
    private static final String TYPE_EDIT_PAGE = "dashboard/type/type-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("TypeMapper")
    private TypeMapper typeMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/type.mk")
    public String type(ModelMap model) throws InventoryException {
        LOG.info("Open Type page - {}", this.getClass().getSimpleName());
        TypeForm typeForm = new TypeForm();
        typeForm.setActive(Boolean.TRUE);
        model.addAttribute("typeList", typeService.typeList());
        model.addAttribute("typeForm", typeForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return TYPE_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/type.mk")
    public String addNewType(@ModelAttribute("typeForm") TypeForm typeForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Type - {}", this.getClass().getSimpleName());
        Type savedType = typeService.saveType(typeMapper.map(typeForm));
        redirectAttrs.addFlashAttribute("typeName", typeForm.getType());
        LOG.debug("Type Details {}", savedType.toString());
        return "redirect:/type.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/type.mk")
    public String editTypePage(ModelMap model, HttpServletRequest request, @PathVariable("id") String typeId) throws InventoryException {
        LOG.info("Open Edit Type page - {}", this.getClass().getSimpleName());
        TypeForm savedType = typeMapper.remap(typeService.findType(Long.valueOf(typeId)));
        model.addAttribute("editTypeForm", savedType);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("typeFromDB", savedType.getType());
        return TYPE_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/type.mk", produces = "application/json")
    @ResponseBody
    public Boolean isTypeExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for brand exists");
        boolean isTypeExists = typeService.isTypeExists(request.getParameter("type").trim(), Long.parseLong(category));
        LOG.info("Tyoe is available? {}", isTypeExists);
        return !isTypeExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/type.mk")
    public String updateType(@ModelAttribute("editTypeForm") TypeForm typeForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Type page - {}", this.getClass().getSimpleName());
        Type updateType = typeService.findType(Long.valueOf(typeForm.getTypeID()));
        Type savedType = typeService.updateType(typeMapper.map(typeForm, updateType));
        redirectAttrs.addFlashAttribute("typeName", typeForm.getType());
        redirectAttrs.addFlashAttribute("editTypeFlag", Boolean.TRUE);
        LOG.debug("Updated Type Details {}", savedType.toString());
        return "redirect:/type.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/type.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeType(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Type from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Type removedType = typeService.removeType(Long.valueOf(id));
            LOG.info("Type is removed? {}", (removedType != null && removedType.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
