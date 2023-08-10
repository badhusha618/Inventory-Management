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

import com.makinus.inventory.api.controller.response.*;
import com.makinus.unitedsupplies.api.controller.response.*;
import com.makinus.inventory.api.data.mapping.ProductFeaturesMapper;
import com.makinus.inventory.api.data.service.security.UnitedSuppliesUserDetail;
import com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterRequest;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.ProductFeaturesView;
import com.makinus.unitedsupplies.common.data.entity.UserCart;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.filter.FilterService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productfeaturesview.ProductFeaturesViewService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.usercart.UserCartService;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.inventory.api.utils.ApiUtils.imagePaths;
import static com.makinus.unitedsupplies.common.utils.AppUtils.longToString;

/**
 * @author Bad_sha
 */
@RestController
@Api(value = "User Profile API Controller")
public class SearchController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final Double MINIMUM_PRICE = 0.0d;

    @Value("${us.app.base.url}")
    private String baseUrl;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private ProductFeaturesViewService productFeaturesViewService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private ProductFeaturesMapper productFeaturesMapper;

    @ApiOperation("Get Product List by Search")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductsBySearch(@ApiParam("Product Name") @RequestParam String productName) throws InventoryException {
        LOG.info("Search products by name - {}", this.getClass().getSimpleName());
        final Map<Long, UserCart> productUserCart = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UnitedSuppliesUserDetail userDetail = (UnitedSuppliesUserDetail) authentication.getPrincipal();
            productUserCart.putAll(userCartService.getAllUserCart(userDetail.getUserId()).stream().collect(Collectors.toMap(UserCart::getProductId, Function.identity())));
        }
        List<Product> products = filterService.searchProductsByName(productName);
        Map<Long, ProductFeaturesView> productFeaturesViewMap = productFeaturesViewService.productFeaturesViewList().stream().collect(Collectors.toMap(ProductFeaturesView::getId, Function.identity()));
        products.forEach(product -> {
            product.setImages(imagePaths(product));
            if (!productUserCart.isEmpty() && productUserCart.containsKey(product.getId())) {
                product.setInCart(YNStatus.YES.getStatus());
            }
            productFeaturesMapper.map(productFeaturesViewMap.getOrDefault(product.getId(), new ProductFeaturesView()), product);
        });
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @ApiOperation("Get product filter list to filter products")
    @GetMapping("/filter/list")
    public ResponseEntity<ProductFeatureResponse> getProductFeatures(@ApiParam("Category ID") @RequestParam Long categoryId) {
        List<Long> productIds = productService.findProductIdsByCategory(categoryId);
        List<ProductFeaturesView> productFeaturesViews = productIds.isEmpty() ? new ArrayList<>() : productFeaturesViewService.listProductFeaturesViewByProdIds(productIds);
        Double maximumPrice = productIds.isEmpty() ? MINIMUM_PRICE : productVendorService.findMaxSaleRateForProducts(productIds);
        return new ResponseEntity<>(mapProductFeatures(categoryId, productFeaturesViews, maximumPrice), HttpStatus.OK);
    }

    private ProductFeatureResponse mapProductFeatures(Long categoryId, List<ProductFeaturesView> productFeaturesViews, Double maximumPrice) {
        List<BrandResponse> brands = mapBrands(categoryId, productFeaturesViews);
        List<CrusherResponse> crushers = mapCrushers(categoryId, productFeaturesViews);
        List<GradeResponse> grades = mapGrades(categoryId, productFeaturesViews);
        List<QualityResponse> qualities = mapQualities(categoryId, productFeaturesViews);
        List<WeightResponse> weights = mapWeights(categoryId, productFeaturesViews);
        List<TypeResponse> types = mapTypes(categoryId, productFeaturesViews);
        List<MaterialResponse> materials = mapMaterials(categoryId, productFeaturesViews);
        List<ColorResponse> colors = mapColors(categoryId, productFeaturesViews);
        return new ProductFeatureResponse(brands, qualities, grades, types, crushers, weights, materials, colors, MINIMUM_PRICE, maximumPrice);
    }

    private List<BrandResponse> mapBrands(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getBrandId() != null && StringUtils.isNotEmpty(productFeaturesView.getBrand())) {
                BrandResponse brandResponse = new BrandResponse();
                brandResponse.setId(productFeaturesView.getBrandId().toString());
                brandResponse.setName(productFeaturesView.getBrand());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<CrusherResponse> mapCrushers(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getCrusherId() != null && StringUtils.isNotEmpty(productFeaturesView.getCrusher())) {
                CrusherResponse brandResponse = new CrusherResponse();
                brandResponse.setId(productFeaturesView.getCrusherId().toString());
                brandResponse.setName(productFeaturesView.getCrusher());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<GradeResponse> mapGrades(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getGradeId() != null && StringUtils.isNotEmpty(productFeaturesView.getGrade())) {
                GradeResponse brandResponse = new GradeResponse();
                brandResponse.setId(productFeaturesView.getGradeId().toString());
                brandResponse.setName(productFeaturesView.getGrade());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<QualityResponse> mapQualities(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getQualityId() != null && StringUtils.isNotEmpty(productFeaturesView.getQuality())) {
                QualityResponse brandResponse = new QualityResponse();
                brandResponse.setId(productFeaturesView.getQualityId().toString());
                brandResponse.setName(productFeaturesView.getQuality());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<WeightResponse> mapWeights(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getWeightId() != null && StringUtils.isNotEmpty(productFeaturesView.getWeight())) {
                WeightResponse brandResponse = new WeightResponse();
                brandResponse.setId(productFeaturesView.getWeightId().toString());
                brandResponse.setName(productFeaturesView.getWeight());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<TypeResponse> mapTypes(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getTypeId() != null && StringUtils.isNotEmpty(productFeaturesView.getType())) {
                TypeResponse brandResponse = new TypeResponse();
                brandResponse.setId(productFeaturesView.getTypeId().toString());
                brandResponse.setName(productFeaturesView.getType());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<MaterialResponse> mapMaterials(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getMaterialId() != null && StringUtils.isNotEmpty(productFeaturesView.getMaterial())) {
                MaterialResponse brandResponse = new MaterialResponse();
                brandResponse.setId(productFeaturesView.getMaterialId().toString());
                brandResponse.setName(productFeaturesView.getMaterial());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private List<ColorResponse> mapColors(Long categoryId, List<ProductFeaturesView> productFeaturesViews) {
        return productFeaturesViews.stream().map(productFeaturesView -> {
            if (productFeaturesView.getColorId() != null && StringUtils.isNotEmpty(productFeaturesView.getColor())) {
                ColorResponse brandResponse = new ColorResponse();
                brandResponse.setId(productFeaturesView.getColorId().toString());
                brandResponse.setName(productFeaturesView.getColor());
                brandResponse.setCategoryId(longToString(categoryId));
                return brandResponse;
            }
            return null;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @ApiOperation("Get Product List by Filter")
    @PostMapping("/filter/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByFilter(@ApiParam("Product Filter Details") @PathVariable("categoryId") String categoryId, @RequestBody ProductFilterRequest productFilterRequest) throws InventoryException {
        final Map<Long, UserCart> productUserCart = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UnitedSuppliesUserDetail userDetail = (UnitedSuppliesUserDetail) authentication.getPrincipal();
            productUserCart.putAll(userCartService.getAllUserCart(userDetail.getUserId()).stream().collect(Collectors.toMap(UserCart::getProductId, Function.identity())));
        }
        List<Long> productIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(productFilterRequest.getMaxPrice()) && StringUtils.isNotEmpty(productFilterRequest.getMinPrice())) {
            productIds = productVendorService.findMaxSaleRateForProducts(new BigDecimal(productFilterRequest.getMaxPrice()), new BigDecimal(productFilterRequest.getMinPrice()));
        }
        List<Product> products = filterService.searchProduct(productFilterRequest, productIds, categoryId);
        Map<Long, ProductFeaturesView> productFeaturesViewMap = productFeaturesViewService.productFeaturesViewList().stream().collect(Collectors.toMap(ProductFeaturesView::getId, Function.identity()));
        products.forEach(product -> {
            product.setImages(imagePaths(product));
            if (!productUserCart.isEmpty() && productUserCart.containsKey(product.getId())) {
                product.setInCart(YNStatus.YES.getStatus());
            }
            productFeaturesMapper.map(productFeaturesViewMap.getOrDefault(product.getId(), new ProductFeaturesView()), product);
        });
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
