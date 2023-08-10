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

import com.makinus.Inventory.admin.data.forms.LoadingChargesForm;
import com.makinus.Inventory.admin.data.mapping.GradeMapper;
import com.makinus.Inventory.admin.data.mapping.LoadingChargesMapper;
import com.makinus.Inventory.common.data.entity.*;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.loadingCharges.LoadingChargesService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.product.ProductService;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.Inventory.admin.utils.AdminUtils.*;

/**
 * @author Bad_sha
 */
@Controller
public class LoadingChargesController {

    private final Logger LOG = LogManager.getLogger(LoadingChargesController.class);

    private static final String LIST_LOADING_CHARGES_PAGE = "dashboard/loading-charges/loading-charges-list";
    private static final String ADD_LOADING_CHARGES_PAGE = "dashboard/loading-charges/loading-charges-add";
    private static final String EDIT_LOADING_CHARGES_PAGE = "dashboard/loading-charges/loading-charges-edit";

    @Autowired
    private LoadingChargesService loadingChargesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UnitMappingService unitMappingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("GradeMapper")
    private GradeMapper gradeMapper;

    @Autowired
    @Qualifier("LoadingChargesMapper")
    private LoadingChargesMapper loadingChargesMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/list/loading-charges.mk")
    public String listLoadingCharges(ModelMap model) throws InventoryException {
        LOG.info("List Products page - {}", this.getClass().getSimpleName());
        model.addAttribute("loadingChargesList", loadingChargesService.loadingChargesList());
        model.addAttribute("productMap", productService.productList().stream().collect(Collectors.toMap(Product::getId, Function.identity())));
        model.addAttribute("categoryMap", categoryService.categoryList().stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName)));
        return LIST_LOADING_CHARGES_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/add/loading-charges.mk")
    public String addLoadingCharges(ModelMap model) throws InventoryException {
        LOG.info("Open Add Product page - {}", this.getClass().getSimpleName());
        model.addAttribute("loadingChargesForm", new LoadingChargesForm());
        List<Unit> units = unitService.unitList();
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        model.addAttribute("unitValues", unitMap(units, unitMappings));
        model.addAttribute("parentCategoryList", fetchParentCategories(categoryService.categoryList()));
        List<Product> productList = productService.productList();
        model.addAttribute("productCategoryTripletMap", productCategoryTripletMap(productList));
        model.addAttribute("productTupleMap", productTupleMap(productList));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName)));
        return ADD_LOADING_CHARGES_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/save/loading-charges.mk")
    public String addNewProduct(@ModelAttribute("loadProductForm") LoadingChargesForm loadingChargesForm, RedirectAttributes redirectAttributes) {
        LOG.info("Add New LoadingCharges in the data base controller - {}", this.getClass().getSimpleName());
        List<LoadingCharges> savedLoadingCharges = loadingChargesService.addNewLoadingCharges(loadingChargesMapper.map(loadingChargesForm));
        redirectAttributes.addFlashAttribute("productId", loadingChargesForm.getProductId());
        LOG.debug("Product Details {}", loadingChargesForm.toString());
        return "redirect:/list/loading-charges.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/loading-charges.mk")
    public String editTransportPage(ModelMap model, @PathVariable("id") String loadingChargesId) throws InventoryException {
        LOG.info("Open Edit Product page - {}", this.getClass().getSimpleName());
        LoadingChargesForm savedLoadingCharges = loadingChargesMapper.remap(loadingChargesService.findLoadingCharges(Long.valueOf(loadingChargesId)));
        model.addAttribute("editLoadingChargesForm", savedLoadingCharges);
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        model.addAttribute("unitValues", unitMap(unitService.unitList(), unitMappings));
        model.addAttribute("parentCategoryList", fetchParentCategories(categoryService.categoryList()));
        List<Product> productList = productService.productList();
        model.addAttribute("productCategoryTripletMap", productCategoryTripletMap(productList));
        model.addAttribute("productTupleMap", productTupleMap(productList));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName)));
        return EDIT_LOADING_CHARGES_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/loading-charges.mk")
    public String updateProduct(@ModelAttribute("editLoadingChargesForm") LoadingChargesForm loadingChargesForm, RedirectAttributes redirectAttributes) throws InventoryException {
        LOG.info("Update Product page - {}", this.getClass().getSimpleName());
        LoadingCharges updateLoadingCharges = loadingChargesService.findLoadingCharges(Long.valueOf(loadingChargesForm.getId()));
        LoadingCharges savedLoadingCharges = loadingChargesService.updateLoadingCharges(loadingChargesMapper.map(loadingChargesForm, updateLoadingCharges));
        redirectAttributes.addFlashAttribute("productId", savedLoadingCharges.getProductId());
        redirectAttributes.addFlashAttribute("editLoadingChargesFlag", Boolean.TRUE);
        LOG.debug("Updated LoadingCharges Details {}", savedLoadingCharges.toString());
        return "redirect:/list/loading-charges.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{id}/{index}/loading-charges.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingLoadingCharges(HttpServletRequest request, @PathVariable String id, @PathVariable String index) {
        LOG.info("Checking if the quantity for product exists");
        String quantityParam = "loadProductForm[" + index + "].quantity";
        boolean isLoadingChargesExist = loadingChargesService.isQuantityExists(Integer.valueOf(request.getParameter(quantityParam).trim()), Long.parseLong(id));
        LOG.info("LoadingCharges is available? {}", isLoadingChargesExist);
        return !isLoadingChargesExist;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/loading-charges.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeLoadingCharges(@PathVariable String id) {
        LOG.info("Remove Product from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            LoadingCharges removedLoadingCharges = loadingChargesService.removeLoadingCharges(Long.valueOf(id));
            LOG.info("Product is removed? {}", (removedLoadingCharges != null && removedLoadingCharges.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
