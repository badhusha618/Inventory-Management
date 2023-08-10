/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import com.makinus.Inventory.admin.data.forms.BrandForm;
import com.makinus.Inventory.admin.data.mapping.BrandMapper;
import com.makinus.unitedsupplies.common.data.entity.Brand;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.brand.BrandService;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
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
public class BrandController {

    private final Logger LOG = LogManager.getLogger(BrandController.class);

    private static final String BRAND_PAGE = "dashboard/brand/brand";
    private static final String BRAND_EDIT_PAGE = "dashboard/brand/brand-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

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
    @Qualifier("BrandMapper")
    private BrandMapper brandMapper;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/brands.mk")
    public String brand(ModelMap model) throws InventoryException {
        LOG.info("Open Brand page - {}", this.getClass().getSimpleName());
        BrandForm brandForm = new BrandForm();
        brandForm.setActive(Boolean.TRUE);
        model.addAttribute("brandList", brandService.brandList());
        model.addAttribute("brandForm", brandForm);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories); // all category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return BRAND_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/brand.mk")
    public String addNewBrand(@ModelAttribute("brandForm") BrandForm brandForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add new Brand - {}", this.getClass().getSimpleName());
        Brand savedBrand = brandService.saveBrand(brandMapper.map(brandForm));
        redirectAttrs.addFlashAttribute("brandName", brandForm.getBrandName());
        LOG.debug("Brand Details {}", savedBrand.toString());
        return "redirect:/brands.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/brand.mk")
    public String editBrandPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String brandId) throws InventoryException {
        LOG.info("Open Edit Brand page - {}", this.getClass().getSimpleName());
        BrandForm savedBrand = brandMapper.remap(brandService.findBrand(Long.valueOf(brandId)));
        model.addAttribute("editBrandForm", savedBrand);
        List<Category> categories = categoryService.parentCategoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        model.addAttribute("brandNameFromDB", savedBrand.getBrandName());
        return BRAND_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{category}/brands.mk", produces = "application/json")
    @ResponseBody
    public Boolean isBrandExists(HttpServletRequest request, @PathVariable String category) {
        LOG.info("Checking if the brand for category exists");
        boolean isBrandExists = brandService.isBrandExists(request.getParameter("brandName").trim(), Long.parseLong(category));
        LOG.info("Brands is available? {}", isBrandExists);
        return !isBrandExists;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/brand.mk")
    public String updateBrand(@ModelAttribute("editBrandForm") BrandForm brandForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Brand page - {}", this.getClass().getSimpleName());
        Brand updateBrand = brandService.findBrand(Long.valueOf(brandForm.getBrandID()));
        Brand savedBrand = brandService.updateBrand(brandMapper.map(brandForm, updateBrand));
        redirectAttrs.addFlashAttribute("brandName", brandForm.getBrandName());
        redirectAttrs.addFlashAttribute("editBrandFlag", Boolean.TRUE);
        LOG.debug("Updated Brand Details {}", savedBrand.toString());
        return "redirect:/brands.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/brand.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeBrand(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Brand from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Brand removedBrand = brandService.removeBrand(Long.valueOf(id));
            LOG.info("Brand is removed? {}", (removedBrand != null && removedBrand.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
