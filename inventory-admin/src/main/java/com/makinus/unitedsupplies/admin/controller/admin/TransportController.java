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

import com.makinus.Inventory.admin.data.forms.TransportForm;
import com.makinus.Inventory.admin.data.mapping.TransportMapper;
import com.makinus.Inventory.common.data.entity.*;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.Tuple;
import com.makinus.Inventory.common.data.service.category.CategoryService;
import com.makinus.Inventory.common.data.service.order.OrderService;
import com.makinus.Inventory.common.data.service.product.ProductService;
import com.makinus.Inventory.common.data.service.transport.TransportService;
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
public class TransportController {

    private final Logger LOG = LogManager.getLogger(TransportController.class);

    private static final String LIST_TRANSPORT_PAGE = "dashboard/transport/transport-list";
    private static final String ADD_TRANSPORT_PAGE = "dashboard/transport/transport-add";
    private static final String EDIT_TRANSPORT_PAGE = "dashboard/transport/transport-edit";

    @Autowired
    private TransportService transportService;

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
    @Qualifier("TransportMapper")
    private TransportMapper transportMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/list/shipment.mk")
    public String listTranpsort(ModelMap model) throws InventoryException {
        LOG.info("List Products page - {}", this.getClass().getSimpleName());
        model.addAttribute("transportList", transportService.transportList());
        model.addAttribute("productMap", productService.productList().stream()
                .collect(Collectors.toMap(Product::getId, Function.identity())));
        model.addAttribute("categoryMap", categoryService.categoryList().stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(Unit::getId, Unit::getUnitName)));
        return LIST_TRANSPORT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/add/shipment.mk")
    public String addTransport(ModelMap model) throws InventoryException {
        LOG.info("Open Add Product page - {}", this.getClass().getSimpleName());
        model.addAttribute("transportForm", new TransportForm());
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        model.addAttribute("unitValues", unitMap(unitService.unitList(), unitMappings));
        model.addAttribute("parentCategoryList", fetchParentCategories(categoryService.categoryList()));
        model.addAttribute("transportList",transportService.transportGroupList());
        model.addAttribute("transportUnitMap",transportService.transportGroupList().stream().collect(Collectors.toMap(Transport::getId, Transport::getUnitId)));
        List<Product> productList = productService.productList();
        model.addAttribute("productCategoryTripletMap", productCategoryTripletMap(productList));
        model.addAttribute("productTupleMap", productTupleMap(productList));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName)));
        return ADD_TRANSPORT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/save/shipment.mk")
    public String addNewProduct(@ModelAttribute("tranProductForm") TransportForm transportForm, RedirectAttributes redirectAttributes) {
        LOG.info("Add New Transport in the data base controller - {}", this.getClass().getSimpleName());
        List<Transport> savedTransports = transportService.addNewTransports(transportMapper.map(transportForm));
        redirectAttributes.addFlashAttribute("tranGroup", transportForm.getTransGroup());
        LOG.debug("Transport Details {}", transportForm.toString());
        return "redirect:/list/shipment.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/shipment.mk")
    public String editTransportPage(ModelMap model, @PathVariable("id") String transportId) throws InventoryException {
        LOG.info("Open Edit Product page - {}", this.getClass().getSimpleName());
        TransportForm savedTransport = transportMapper.remap(transportService.findTransport(Long.valueOf(transportId)));
        model.addAttribute("editTransportForm", savedTransport);
        List<UnitMapping> unitMappings = unitMappingService.unitMappingList();
        model.addAttribute("unitValues", unitMap(unitService.unitList(), unitMappings));
        model.addAttribute("parentCategoryList", fetchParentCategories(categoryService.categoryList()));
        List<Product> productList = productService.productList();
        model.addAttribute("productCategoryTripletMap", productCategoryTripletMap(productList));
        model.addAttribute("productTupleMap", productTupleMap(productList));
        model.addAttribute("unitMap", unitService.unitList().stream().collect(Collectors.toMap(u -> u.getId().toString(), Unit::getUnitName)));
        return EDIT_TRANSPORT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/shipment.mk")
    public String updateProduct(@ModelAttribute("editTransportForm") TransportForm transportForm, RedirectAttributes redirectAttributes) throws InventoryException {
        LOG.info("Update Product page - {}", this.getClass().getSimpleName());
        Transport updateTransport = transportService.findTransport(Long.valueOf(transportForm.getId()));
        Transport savedTransport = transportService.updateTransport(transportMapper.map(transportForm, updateTransport));
        redirectAttributes.addFlashAttribute("groupName", savedTransport.getTransGroup());
        redirectAttributes.addFlashAttribute("editTransportFlag", Boolean.TRUE);
        LOG.debug("Updated Transport Details {}", savedTransport.toString());
        return "redirect:/list/shipment.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/shipment.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeTransport(@PathVariable String id) {
        LOG.info("Remove Product from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Transport removedTransport = transportService.removeTransport(Long.valueOf(id));
            LOG.info("Product is removed? {}", (removedTransport != null && removedTransport.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/{transGroup}/{index}/{distance}/transport-charges.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingLoadingCharges(HttpServletRequest request, @PathVariable String transGroup, @PathVariable String index,@PathVariable String distance)
    {
        LOG.info("Checking if the quantity for product exists");
        String quantityParam = "tranProductForms[" + index + "].quantity";
        boolean isTransportExists = transportService.isQuantityAndDistanceExists(Integer.valueOf(request.getParameter(quantityParam).trim()), Integer.valueOf(distance), transGroup);
        LOG.info("TransportCharges is available? {}", isTransportExists);
        return !isTransportExists;
    }
}

    /*@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/available/transport-charges.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingLoadingCharges(HttpServletRequest request) throws InventoryException {
        LOG.info("Checking if the quantity for product exists");
        String quantity = request.getParameter("quantity").trim();
        String distance = request.getParameter("distance").trim();
        String productId = request.getParameter("productId").trim();
        boolean isTransportExists = Boolean.FALSE;
        if (StringUtils.isNotEmpty(quantity) && StringUtils.isNotEmpty(distance) && StringUtils.isNotEmpty(productId)) {
            isTransportExists = transportService.isQuantityAndDistanceExists(Integer.valueOf(quantity), Integer.valueOf(distance), Long.parseLong(productId));
        }
        LOG.info("Transport Charges is available? {}", isTransportExists);
        return !isTransportExists;*//*
    }
}*/
