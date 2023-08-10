/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.data.service.promotion.PromotionService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.util.FileCopyUtils.copy;

/**
 * Created by sabique
 */
@RestController
@RequestMapping(value = "/promotions")
@Api(value = "Promotion API Controller")
public class PromotionController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private PromotionService promotionService;

    @Value("${us.app.base.url}")
    protected String baseUrl;

    @ApiOperation("List all promotion for the customer")
    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        LOG.info("Get all promotions - {}", this.getClass().getSimpleName());
        List<Promotion> allPromotions = promotionService.activePromotionsList();
        allPromotions.forEach(p -> p.setImageAsString((p.getImagePath())));
        return new ResponseEntity<>(allPromotions, HttpStatus.OK);
    }

    @ApiOperation("Get promotion to display")
    @GetMapping("/{promotionId}")
    public ResponseEntity<Promotion> getPromotionByPromotionId(
            @ApiParam("Promotion Id") @PathVariable("promotionId") Long promotionId)
            throws UnitedSuppliesException {
        LOG.info("Get promotion for the id {} - {}", promotionId, this.getClass().getSimpleName());
        Promotion promotion = promotionService.findSalePromotion(promotionId);
        promotion.setImageAsString(promotion.getImagePath());
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @Deprecated
    @ApiOperation("Get Promotion Image to Display")
    @GetMapping("/{promotionId}/img")
    public void getPromotionImage(HttpServletResponse response, @ApiParam("Promotion Id") @PathVariable("promotionId") Long promotionId) throws UnitedSuppliesException {
        try {
            Promotion promotion = promotionService.findSalePromotion(promotionId);
            if (promotion != null && !promotion.getImagePath().isEmpty()) {
                Path path = Paths.get(promotion.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(promotion.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", promotionId, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(promotion.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new UnitedSuppliesException(
                    "IO Exception occurred while viewing promotion image " + e.getMessage());
        }
    }
}
