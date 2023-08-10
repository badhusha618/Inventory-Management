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

import com.makinus.unitedsupplies.admin.data.forms.PromotionForm;
import com.makinus.unitedsupplies.admin.data.mapping.PromotionMapper;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.data.service.promotion.PromotionService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author abuabdul
 */
@Controller
public class PromotionController {

    private final Logger LOG = LogManager.getLogger(PromotionController.class);

    private static final String PROMOTION_PAGE = "dashboard/promotion/promotion";
    private static final String ADD_PROMOTION_PAGE = "dashboard/promotion/promotion-add";
    private static final String EDIT_PROMOTION_PAGE = "dashboard/promotion/promotion-edit";

    @Autowired
    @Qualifier("PromotionMapper")
    private PromotionMapper promotionMapper;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/promotions.mk")
    public String promotion(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open Promotions page - {}", this.getClass().getSimpleName());
        model.addAttribute("promotionList", promotionService.promotionsList());
        return PROMOTION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/new/promotion.mk")
    public String newPromotion(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open Promotion add form page - {}", this.getClass().getSimpleName());
        PromotionForm promotionForm = new PromotionForm();
        promotionForm.setActivePromotion(Boolean.TRUE);
        model.addAttribute("promotionForm", promotionForm);
        return ADD_PROMOTION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/add/promotion.mk")
    public String addSalePromotion(@ModelAttribute("promotionForm") PromotionForm promotionForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Add New Promotion on admin panel dashboard - {}", this.getClass().getSimpleName());
        Promotion savedPromotion = promotionService.savePromotion(promotionMapper.map(promotionForm));
        redirectAttrs.addFlashAttribute("promotionName", savedPromotion.getPromotionName());
        LOG.debug("Promotion Details {}", savedPromotion.toString());
        return "redirect:/promotions.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/promotion/{id}/edit.mk")
    public String editSalePromotion(ModelMap model, @PathVariable("id") Long promotionId) throws UnitedSuppliesException {
        LOG.info("Edit Promotion on admin panel dashboard - {}", this.getClass().getSimpleName());
        PromotionForm savedPromotionForm = promotionMapper.remap(promotionService.findSalePromotion(promotionId));
        model.addAttribute("editPromotionForm", savedPromotionForm);
        return EDIT_PROMOTION_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/promotion.mk")
    public String updateSalePromotion(@ModelAttribute("editPromotionForm") PromotionForm promotionForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Update Promotion on admin panel dashboard - {}", this, getClass().getSimpleName());
        Promotion updatePromotion = promotionService.findSalePromotion(Long.valueOf(promotionForm.getPromotionID()));
        if (updatePromotion.getImagePath() != null && promotionForm.getEditPromotionImage() != null && !promotionForm.getEditPromotionImage().isEmpty()) {
            String[] uriPaths = updatePromotion.getImagePath().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        Promotion savedPromotion = promotionService.updateSalePromotion(promotionMapper.map(promotionForm, updatePromotion));
        redirectAttrs.addFlashAttribute("promotionName", savedPromotion.getPromotionName());
        redirectAttrs.addFlashAttribute("editPromotionFlag", Boolean.TRUE);
        LOG.debug("Updated Promotion Details {}", savedPromotion.toString());
        return "redirect:/promotions.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/promotion/{id}/remove.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removePromotion(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Promotion from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Promotion removedPromotion = promotionService.removePromotion(Long.valueOf(id));
            LOG.info("Promotion is removed? {}", (removedPromotion != null && removedPromotion.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            if (removedPromotion.getImagePath() != null) {
                String[] uriPaths = removedPromotion.getImagePath().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @Deprecated
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/promotion/{id}/view.mk")
    public void viewPromotionImage(HttpServletResponse response, @PathVariable String id) throws UnitedSuppliesException {
        LOG.info("View Promotion from dashboard - {}", this.getClass().getSimpleName());
        try {
            Promotion promotion = promotionService.findSalePromotion(Long.valueOf(id));
            if (promotion != null && isNotEmpty(promotion.getImagePath())) {
                Path path = Paths.get(promotion.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(promotion.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(promotion.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new UnitedSuppliesException("IO Exception occurred while viewing Promotion " + e.getMessage());
        }
    }
}
