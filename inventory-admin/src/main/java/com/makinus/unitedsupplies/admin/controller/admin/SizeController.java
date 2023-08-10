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

import com.makinus.unitedsupplies.admin.data.forms.SizeForm;
import com.makinus.unitedsupplies.admin.data.mapping.SizeMapper;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.Size;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.size.SizeService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
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

import static com.makinus.unitedsupplies.admin.utils.AdminUtils.subCategoryMap;

/**
 * @author abuabdul
 */
@Controller
public class SizeController {

    private final Logger LOG = LogManager.getLogger(SizeController.class);

    private static final String SIZE_PAGE = "dashboard/size/size";
    private static final String SIZE_EDIT_PAGE = "dashboard/size/size-edit";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("SizeMapper")
    private SizeMapper sizeMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/size.mk")
    public String size(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open Size page - {}", this.getClass().getSimpleName());
        SizeForm sizeForm = new SizeForm();
        sizeForm.setActive(Boolean.TRUE);
        model.addAttribute("sizeList", sizeService.sizeList());
        model.addAttribute("sizeForm", sizeForm);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryValues", categories); // parent category for Add form
        model.addAttribute("categoryMap", categories.stream().collect(Collectors.toMap(Category::getId, Category::getCategoryName)));
        return SIZE_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/size.mk")
    public String addNewSize(@ModelAttribute("sizeForm") SizeForm sizeForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Open Add new Size - {}", this.getClass().getSimpleName());
        Size savedSize = sizeService.saveSize(sizeMapper.map(sizeForm));
        redirectAttrs.addFlashAttribute("sizeName", sizeForm.getSize());
        LOG.debug("Size Details {}", savedSize.toString());
        return "redirect:/size.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/size.mk")
    public String editSizePage(ModelMap model, HttpServletRequest request, @PathVariable("id") String sizeId) throws UnitedSuppliesException {
        LOG.info("Open Edit Size page - {}", this.getClass().getSimpleName());
        SizeForm savedSize = sizeMapper.remap(sizeService.findSize(Long.valueOf(sizeId)));
        model.addAttribute("editSizeForm", savedSize);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categoryValues", categories);
        request.getSession().setAttribute("subCategories", subCategoryMap(categories));
        return SIZE_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/size.mk")
    public String updateSize(@ModelAttribute("editSizeForm") SizeForm sizeForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Update Size page - {}", this.getClass().getSimpleName());
        Size updateSize = sizeService.findSize(Long.valueOf(sizeForm.getSizeID()));
        Size savedSize = sizeService.updateSize(sizeMapper.map(sizeForm, updateSize));
        redirectAttrs.addFlashAttribute("sizeName", sizeForm.getSize());
        redirectAttrs.addFlashAttribute("editSizeFlag", Boolean.TRUE);
        LOG.debug("Updated Size Details {}", savedSize.toString());
        return "redirect:/size.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/size.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeSize(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Size from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Size removedSize = sizeService.removeSize(Long.valueOf(id));
            LOG.info("Size is removed? {}", (removedSize != null && removedSize.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }
}
