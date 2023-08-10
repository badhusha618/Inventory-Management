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

import com.makinus.Inventory.admin.data.forms.UnitForm;
import com.makinus.Inventory.admin.data.mapping.UnitMapper;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.Unit;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.unit.UnitService;
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
public class UnitController {

    private final Logger LOG = LogManager.getLogger(UnitController.class);

    private static final String UNIT_PAGE = "dashboard/unit/unit";
    private static final String UNIT_EDIT_PAGE = "dashboard/unit/unit-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("UnitMapper")
    private UnitMapper unitMapper;

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
    @GetMapping(value = "/units.mk")
    public String unit(ModelMap model) throws InventoryException {
        LOG.info("Open Unit page - {}", this.getClass().getSimpleName());
        UnitForm unitForm = new UnitForm();
        unitForm.setActive(Boolean.TRUE);
        model.addAttribute("unitList", unitService.unitList());
        model.addAttribute("unitForm", unitForm);
        return UNIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/unit.mk")
    public String addNewUnit(@ModelAttribute("unitForm") UnitForm unitForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Unit - {}", this.getClass().getSimpleName());
        Unit savedUnit = unitService.saveUnit(unitMapper.map(unitForm));
        redirectAttrs.addFlashAttribute("unitName", unitForm.getUnitName());
        LOG.debug("Unit Details {}", savedUnit.toString());
        return "redirect:/units.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/unit.mk")
    public String editUnitPage(ModelMap model, @PathVariable("id") String unitId) throws InventoryException {
        LOG.info("Open Edit Unit page - {}", this.getClass().getSimpleName());
        UnitForm savedUnit = unitMapper.remap(unitService.findUnit(Long.valueOf(unitId)));
        model.addAttribute("editUnitForm", savedUnit);
        return UNIT_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/unit.mk")
    public String updateUnit(@ModelAttribute("editUnitForm") UnitForm unitForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Unit page - {}", this.getClass().getSimpleName());
        Unit updateUnit = unitService.findUnit(Long.valueOf(unitForm.getUnitID()));
        Unit savedUnit = unitService.updateUnit(unitMapper.map(unitForm, updateUnit));
        redirectAttrs.addFlashAttribute("unitName", unitForm.getUnitName());
        redirectAttrs.addFlashAttribute("editUnitFlag", Boolean.TRUE);
        LOG.debug("Updated Unit Details {}", savedUnit.toString());
        return "redirect:/units.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/unit/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingUnit(HttpServletRequest request) throws InventoryException {
        LOG.info("Checking if the unit exists - {}", this.getClass().getSimpleName());
        boolean isUnitAvailable = unitService.isUnitAvailable(request.getParameter("unitCode").trim());
        LOG.info("UnitCode is available? {}", isUnitAvailable);
        return !isUnitAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/unit.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeUnit(@PathVariable String id) {
        LOG.info("Remove Unit from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Unit removedUnit = unitService.removeUnit(Long.valueOf(id));
            LOG.info("Unit is removed? {}", (removedUnit != null && removedUnit.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
