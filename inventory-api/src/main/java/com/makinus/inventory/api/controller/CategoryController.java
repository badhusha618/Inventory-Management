/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller;

import com.makinus.inventory.api.data.mapping.ProductFeaturesMapper;
import com.makinus.inventory.api.data.service.security.UnitedSuppliesUserDetail;
import com.makinus.unitedsupplies.common.data.entity.Category;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import com.makinus.unitedsupplies.common.data.entity.UserCart;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productfeaturesview.ProductFeaturesViewService;
import com.makinus.unitedsupplies.common.data.service.usercart.UserCartService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.inventory.api.utils.ApiUtils.imagePaths;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author Bad_sha
 */
@RestController
@RequestMapping(value = "/categories")
@Api(value = "Base API Controller")
public class CategoryController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private ProductFeaturesMapper productFeaturesMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private ProductFeaturesViewService productFeaturesViewService;

    @Autowired
    private ImageWriter imageWriter;

    @Value("${us.app.base.url}")
    protected String baseUrl;

    @ApiOperation("List all parent categories for the customer")
    @GetMapping
    public ResponseEntity<List<Category>> getAllParentCategories() throws InventoryException {
        LOG.info("List all parent categories - {}", this.getClass().getSimpleName());
        List<Category> allCategories = categoryService.activeParentCategoryList();
        allCategories.forEach(category -> category.setImageUrl(category.getImagePath()));
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @ApiOperation("List sub categories by parent category")
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<Category>> getSubCategoriesByParentCategory(@ApiParam("Category Id") @PathVariable("categoryId") Long categoryId) throws InventoryException {
        LOG.info("List all sub categories for parent category - {}", this.getClass().getSimpleName());
        final Map<Long, UserCart> productUserCart = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UnitedSuppliesUserDetail userDetail = (UnitedSuppliesUserDetail) authentication.getPrincipal();
            productUserCart.putAll(userCartService.getAllUserCart(userDetail.getUserId()).stream().collect(Collectors.toMap(UserCart::getProductId, Function.identity())));
        }
        Map<Long, ProductFeaturesView> productFeaturesViewMap = productFeaturesViewService.productFeaturesViewList().stream().collect(Collectors.toMap(ProductFeaturesView::getId, Function.identity()));
        List<Category> allCategories = categoryService.categoryListByParent(categoryId);
        allCategories.forEach(category -> {
            category.setImageUrl(baseUrl + CATEGORY_PATH + category.getId() + IMG_PATH + IMAGE_VERSION + AppUtils.extractTimeStamp(category.getImagePath()));
            List<Product> products = productService.productListBySubCategory(category.getId());
            products.forEach(p -> {
                p.setImages(imagePaths(p));
                if (!productUserCart.isEmpty() && productUserCart.containsKey(p.getId())) {
                    p.setInCart(YNStatus.YES.getStatus());
                }
                productFeaturesMapper.map(productFeaturesViewMap.getOrDefault(p.getId(), new ProductFeaturesView()), p);
            });
            category.setProducts(products);
        });
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @ApiOperation("List all products for specific sub category")
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Category> getAllProductsByCategoryId(@ApiParam("Category Id") @PathVariable("categoryId") Long categoryId) throws InventoryException {
        LOG.info("List all product based on sub category - {}", this.getClass().getSimpleName());
        final Map<Long, UserCart> productUserCart = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UnitedSuppliesUserDetail userDetail = (UnitedSuppliesUserDetail) authentication.getPrincipal();
            productUserCart.putAll(userCartService.getAllUserCart(userDetail.getUserId()).stream().collect(Collectors.toMap(UserCart::getProductId, Function.identity())));
        }
        Category category = categoryService.findCategory(categoryId);
        category.setImageUrl(baseUrl + CATEGORY_PATH + categoryId + IMG_PATH + IMAGE_VERSION + AppUtils.extractTimeStamp(category.getImagePath()));
        Map<Long, ProductFeaturesView> productFeaturesViewMap = productFeaturesViewService.productFeaturesViewList().stream().collect(Collectors.toMap(ProductFeaturesView::getId, Function.identity()));
        List<Product> products = productService.productListByCategory(categoryId);
        products.forEach(p -> {
            p.setImages(imagePaths(p));
            if (!productUserCart.isEmpty() && productUserCart.containsKey(p.getId())) {
                p.setInCart(YNStatus.YES.getStatus());
            }
            productFeaturesMapper.map(productFeaturesViewMap.getOrDefault(p.getId(), new ProductFeaturesView()), p);
        });
        category.setProducts(products);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @ApiOperation("Get Category Image to Display")
    @GetMapping("/{categoryId}/img")
    public void getCategoryImage(HttpServletResponse response, @ApiParam("Category Id") @PathVariable("categoryId") Long categoryId) throws InventoryException {
        try {
            LOG.info("Get image for category - {}", this.getClass().getSimpleName());
            Category category = categoryService.findCategory(categoryId);
            if (category != null && !category.getImagePath().isEmpty()) {
                Path path = Paths.get(category.getImagePath());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(category.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", categoryId, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(category.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new InventoryException("IO Exception occurred while viewing category image " + e.getMessage());
        }
    }
}
