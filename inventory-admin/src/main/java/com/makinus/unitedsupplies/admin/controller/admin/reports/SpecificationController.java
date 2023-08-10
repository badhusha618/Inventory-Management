/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.admin.reports;

import com.makinus.Inventory.admin.data.forms.SpecificationForm;
import com.makinus.Inventory.admin.data.mapping.SpecificationMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Specification;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.specification.SpecificationService;
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
 * @ammar
 */
@Controller
public class SpecificationController {

    private final Logger LOG = LogManager.getLogger(SpecificationController.class);

    private static final String SPECIFICATION_PAGE = "dashboard/specification/specification";
    private static final String SPECIFICATION_EDIT_PAGE = "dashboard/specification/specification-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("SpecificationMapper")
    private SpecificationMapper specificationMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/specification.mk")
    public String specification(ModelMap model) throws InventoryException {
        LOG.info("Open Specification page");
        SpecificationForm specificationForm = new SpecificationForm();
        specificationForm.setActive(Boolean.TRUE);
        model.addAttribute("specificationList", specificationService.specificationList());
        model.addAttribute("specificationForm", specificationForm);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return SPECIFICATION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/specification.mk")
    public String addNewSpecification(@ModelAttribute("specificationForm") SpecificationForm specificationForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Specification");
        Specification savedSpecification = specificationService.saveSpecification(specificationMapper.map(specificationForm));
        redirectAttrs.addFlashAttribute("specificationName", specificationForm.getSpecification());
        LOG.debug("Specification Details {}", savedSpecification.toString());
        return "redirect:/specification.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/specification.mk")
    public String editSpecificationPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String specificationId) throws InventoryException {
        LOG.info("Open Edit Specification page");
        SpecificationForm savedSpecification = specificationMapper.remap(specificationService.findSpecification(Long.valueOf(specificationId)));
        model.addAttribute("editSpecificationForm", savedSpecification);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        return SPECIFICATION_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/specification.mk")
    public String updateSpecification(@ModelAttribute("editSpecificationForm") SpecificationForm specificationForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Specification page");
        Specification updateSpecification = specificationService.findSpecification(Long.valueOf(specificationForm.getSpecificationID()));
        Specification savedSpecification = specificationService.updateSpecification(specificationMapper.map(specificationForm, updateSpecification));
        redirectAttrs.addFlashAttribute("specificationName", specificationForm.getSpecification());
        redirectAttrs.addFlashAttribute("editSpecificationFlag", Boolean.TRUE);
        LOG.debug("Updated Specification Details {}", savedSpecification.toString());
        return "redirect:/specification.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/specification.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeSpecification(@PathVariable String id) {
        LOG.info("Remove Specification from Database");
        Map<String, Boolean> map = new HashMap<>();
        try {
            Specification removedSpecification = specificationService.removeSpecification(Long.valueOf(id));
            LOG.info("Specification is removed? {}", (removedSpecification != null && removedSpecification.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
