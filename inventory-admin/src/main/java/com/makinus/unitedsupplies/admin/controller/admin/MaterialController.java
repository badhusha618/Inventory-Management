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

import com.makinus.Inventory.admin.data.forms.MaterialForm;
import com.makinus.Inventory.admin.data.mapping.MaterialMapper;
import com.makinus.Inventory.common.data.entity.Category;
import com.makinus.Inventory.common.data.entity.Material;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.material.MaterialService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
public class MaterialController {

    private final Logger LOG = LogManager.getLogger(MaterialController.class);

    private static final String MATERIAL_PAGE = "dashboard/material/material";
    private static final String MATERIAL_EDIT_PAGE = "dashboard/material/material-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream()
                .collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @Autowired
    @Qualifier("MaterialMapper")
    private MaterialMapper materialMapper;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/material.mk")
    public String material(ModelMap model) throws InventoryException {
        LOG.info("Open Material page - {}", this.getClass().getSimpleName());
        MaterialForm materialForm = new MaterialForm();
        materialForm.setActive(Boolean.TRUE);
        model.addAttribute("materialList", materialService.materialList());
        model.addAttribute("materialForm", materialForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute(
                "categoryMap",
                categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return MATERIAL_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/material.mk")
    public String addNewMaterial(
            @ModelAttribute("materialForm") MaterialForm materialForm, RedirectAttributes redirectAttrs)
            throws InventoryException {
        LOG.info("Open Add new Material - {}", this.getClass().getSimpleName());
        Material savedMaterial = materialService.saveMaterial(materialMapper.map(materialForm));
        redirectAttrs.addFlashAttribute("materialName", materialForm.getMaterial());
        LOG.debug("Material Details {}", savedMaterial.toString());
        return "redirect:/material.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/material.mk", produces = "application/json")
    @ResponseBody
    public Boolean isMaterialExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the category for material exists");
        boolean isMaterialExists = materialService.isMaterialExists(request.getParameter("material").trim(), Long.parseLong(category));
        LOG.info("Material is available? {}", isMaterialExists);
        return !isMaterialExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/material.mk")
    public String editMaterialPage(
            ModelMap model, HttpServletRequest request, @PathVariable("id") String materialId)
            throws InventoryException {
        LOG.info("Open Edit Material page - {}", this.getClass().getSimpleName());
        MaterialForm savedMaterial =
                materialMapper.remap(materialService.findMaterial(Long.valueOf(materialId)));
        model.addAttribute("editMaterialForm", savedMaterial);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("materialFromDB", savedMaterial.getMaterial());
        return MATERIAL_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/material.mk")
    public String updateMaterial(
            @ModelAttribute("editMaterialForm") MaterialForm materialForm,
            BindingResult result,
            ModelMap model,
            RedirectAttributes redirectAttrs)
            throws InventoryException {
        LOG.info("Update Material page - {}", this.getClass().getSimpleName());
        Material updateMaterial = materialService.findMaterial(Long.valueOf(materialForm.getMaterialID()));
        Material savedMaterial =
                materialService.updateMaterial(materialMapper.map(materialForm, updateMaterial));
        redirectAttrs.addFlashAttribute("materialName", materialForm.getMaterial());
        redirectAttrs.addFlashAttribute("editMaterialFlag", Boolean.TRUE);
        LOG.debug("Updated Material Details {}", savedMaterial.toString());
        return "redirect:/material.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/material.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeMaterial(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Material from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Material removedMaterial = materialService.removeMaterial(Long.valueOf(id));
            LOG.info(
                    "Material is removed? {}",
                    (removedMaterial != null
                            && removedMaterial.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
