/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.api;

import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.entity.Promotion;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.promotion.PromotionService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author abuabdul
 */
@RestController
@RequestMapping(value = "/img")
@Api(value = "Image API Controller")
public class ApiImageRestController {

    private final Logger LOG = LogManager.getLogger(ApiImageRestController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;

    @GetMapping(value = "/category.mk")
    @ApiOperation(value = "To get category image", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved category image"),
                    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    public void categoryImage(HttpServletResponse response, @RequestParam("id") String id)
            throws UnitedSuppliesException {
        LOG.info("View Category Image");
        try {
            Category category = categoryService.findCategory(Long.valueOf(id));
            if (category != null && isNotEmpty(category.getImagePath())) {
                Path path = Paths.get(category.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(category.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(category.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new UnitedSuppliesException("IO Exception occurred while viewing category image " + e.getMessage());
        }
    }

    @GetMapping(value = "/promotions.mk")
    @ApiOperation(value = "To get promotion image", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved promotions image"),
                    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    public void promotionImage(HttpServletResponse response, @RequestParam("id") String id) throws UnitedSuppliesException {
        LOG.info("View Promotion Image");
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
            throw new UnitedSuppliesException("IO Exception occurred while viewing Promotion image " + e.getMessage());
        }
    }

    @GetMapping(value = "/products.mk")
    @ApiOperation(value = "To get product image", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved product image"),
                    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    public void productImage(HttpServletResponse response, @RequestParam("id") String id) throws UnitedSuppliesException {
        LOG.info("View Product Image");
    /*  try {
        Product product = productService.findProduct(Long.valueOf(id));
        if (product != null && isNotEmpty(product.getImagePath())) {
            Path path = Paths.get(product.getImagePath());
            response.setContentType(Files.probeContentType(path));
            response.setContentLength(product.getImage().length);
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
            response.setCharacterEncoding("UTF-8");
            copy(product.getImage(), response.getOutputStream());
        }
    } catch (IOException e) {
        throw new UnitedSuppliesException("IO Exception occurred while viewing product image " + e.getMessage());
    } */

    }

  /*@GetMapping(value = "/sliders.mk")
  @ApiOperation(value = "To get slider image", response = void.class)
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved slider image"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
  public void sliderImage(HttpServletResponse response, @RequestParam("id") String id) throws UnitedSuppliesException {
      LOG.info("View Slider Image");
      try {
          HomeSlider homeSlider = homeSliderService.findHomeSlider(Long.valueOf(id));
          if (homeSlider != null && isNotEmpty(homeSlider.getImagePath())) {
              Path path = Paths.get(homeSlider.getImagePath());
              response.setContentType(Files.probeContentType(path));
              response.setContentLength(homeSlider.getImage().length);
              response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
              response.setCharacterEncoding("UTF-8");
              copy(homeSlider.getImage(), response.getOutputStream());
          }
      } catch (IOException e) {
          throw new UnitedSuppliesException("IO Exception occurred while viewing home slider " + e.getMessage());
      }
  }*/
}
