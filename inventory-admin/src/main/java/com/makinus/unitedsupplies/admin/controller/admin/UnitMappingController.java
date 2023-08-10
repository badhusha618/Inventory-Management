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

import com.makinus.Inventory.admin.data.forms.UnitMappingForm;
import com.makinus.Inventory.admin.data.mapping.UnitMappingMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Unit;
import com.makinus.Inventory.common.data.entity.UnitMapping;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.unit.UnitService;
import com.makinus.Inventory.common.data.service.unitmapping.UnitMappingService;
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

/**
 * @author Bad_sha
 */
@Controller
public class UnitMappingController {

    private final Logger LOG = LogManager.getLogger(UnitMappingController.class);

    private static final String UNIT_MAPPING_PAGE = "dashboard/unit-mapping/unit-mapping";
    private static final String UNIT_MAPPING_EDIT_PAGE = "dashboard/unit-mapping/unit-mapping-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitMappingService unitMappingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("UnitMappingMapper")
    private UnitMappingMapper unitMappingMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/unit-mappings.mk")
    public String unitMapping(ModelMap model) throws InventoryException {
        LOG.info("Open UnitMapping page - {}", this.getClass().getSimpleName());
        UnitMappingForm unitMappingForm = new UnitMappingForm();
        unitMappingForm.setActive(Boolean.TRUE);
        model.addAttribute("unitMappingList", unitMappingService.unitMappingList());
        model.addAttribute("unitMappingForm", unitMappingForm);

        List<Unit> units = unitService.listActiveUnits(); // Unit List
        // model.addAttribute("unitValues", fetchUnits(units));// Kay Value Pair for Add form
        model.addAttribute("unitValues", units); // Kay Value Pair for Add form
        model.addAttribute("unitMap", units.stream().collect(Collectors.toMap(Unit::getId, Unit::getUnitName))); // To List Unit Name in Table

        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName))); // To List Category Name in
        // Table
        return UNIT_MAPPING_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/unit-mapping.mk")
    public String addNewUnitMapping(@ModelAttribute("unitMappingForm") UnitMappingForm unitMappingForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new UnitMapping - {}", this.getClass().getSimpleName());
        UnitMapping savedUnitMapping = unitMappingService.saveUnitMapping(unitMappingMapper.map(unitMappingForm));
        redirectAttrs.addFlashAttribute("unitName", unitService.findUnit(Long.valueOf(unitMappingForm.getUnit())).getUnitName());
        LOG.debug("UnitMapping Details {}", savedUnitMapping.toString());
        return "redirect:/unit-mappings.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/unit-mappings.mk", produces = "application/json")
    @ResponseBody
    public Boolean isUnitMappingExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for unitMapping exists");
        boolean isUnitMappingExists = unitMappingService.isUnitMappingExists(Long.parseLong(request.getParameter("unit")), Long.parseLong(category));
        LOG.info("UnitMapping is available? {}", isUnitMappingExists);
        return !isUnitMappingExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/unit-mapping.mk")
    public String editUnitMappingPage(ModelMap model, @PathVariable("id") String unitMappingId) throws InventoryException {
        LOG.info("Open Edit UnitMapping page - {}", this.getClass().getSimpleName());
        UnitMappingForm savedUnitMapping = unitMappingMapper.remap(unitMappingService.findUnitMapping(Long.valueOf(unitMappingId)));
        model.addAttribute("editUnitMappingForm", savedUnitMapping);

        List<Unit> units = unitService.unitList();
        model.addAttribute("unitValues", units);

        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        model.addAttribute("unitFromDB", savedUnitMapping.getUnit());
        return UNIT_MAPPING_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/unit-mapping.mk")
    public String updateUnitMapping(@ModelAttribute("editUnitMappingForm") UnitMappingForm unitMappingForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update UnitMapping page - {}", this.getClass().getSimpleName());
        UnitMapping updateUnitMapping = unitMappingService.findUnitMapping(Long.valueOf(unitMappingForm.getUnitMappingID()));
        UnitMapping savedUnitMapping = unitMappingService.updateUnitMapping(unitMappingMapper.map(unitMappingForm, updateUnitMapping));
        redirectAttrs.addFlashAttribute("unitName", unitService.findUnit(Long.valueOf(unitMappingForm.getUnit())).getUnitName());
        redirectAttrs.addFlashAttribute("editUnitMappingFlag", Boolean.TRUE);
        LOG.debug("Updated UnitMapping Details {}", savedUnitMapping.toString());
        return "redirect:/unit-mappings.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/unit-mapping.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeUnitMapping(@PathVariable String id) {
        LOG.info("Remove UnitMapping from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            UnitMapping removedUnitMapping = unitMappingService.removeUnitMapping(Long.valueOf(id));
            LOG.info("UnitMapping is removed? {}", (removedUnitMapping != null && removedUnitMapping.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
