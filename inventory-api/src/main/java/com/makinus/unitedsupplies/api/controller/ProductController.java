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

import com.makinus.unitedsupplies.api.controller.response.ProductDetail;
import com.makinus.unitedsupplies.api.controller.response.ProductVendorDetail;
import com.makinus.unitedsupplies.api.controller.response.ShipmentDetail;
import com.makinus.unitedsupplies.api.data.service.security.UnitedSuppliesUserDetail;
import com.makinus.unitedsupplies.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productfeaturesview.ProductFeaturesViewService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.transport.TransportService;
import com.makinus.unitedsupplies.common.data.service.usercart.UserCartService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.api.utils.ApiUtils.imagePaths;
import static com.makinus.unitedsupplies.common.data.reftype.AddressCategory.DELIVERY_ADDRESS;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * Created by nizamabdul
 */
@RestController
@RequestMapping(value = "/products")
@Api(value = "Product API Controller")
public class ProductController {

    @Value("${us.app.base.url}")
    protected String baseUrl;

    @Autowired
    private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private ProductFeaturesViewService productFeaturesViewService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private AddressService addressService;

    @ApiOperation("Get single product to display product details")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetail> getProductByProductId(@ApiParam("Product Id") @PathVariable("productId") Long productId) throws UnitedSuppliesException {
        ProductDetail productDetail = new ProductDetail();
        Product product = productService.findProduct(productId);
        List<ProductVendor> productVendors = productVendorService.productVendorListByProduct(productId);
        ProductFeaturesView productFeaturesView = productFeaturesViewService.findProductFeaturesView(productId);
        productDetailMap(productDetail, product, productFeaturesView, productVendors);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UnitedSuppliesUserDetail userDetail = (UnitedSuppliesUserDetail) authentication.getPrincipal();
            Optional<UserCart> cartOptional = userCartService.getCartByProductIdAndUserId(userDetail.getUserId(), productId);
            if (cartOptional.isPresent()) {
                productDetail.setInCart(YNStatus.YES.getStatus());
            }
            productDetail.setDeliveryAddressAvailable(addressService.findDefaultAddressByUserAndCategory(userDetail.getUserId(), DELIVERY_ADDRESS.getStatus()).isPresent());
        }
        return new ResponseEntity<>(productDetail, HttpStatus.OK);
    }

    private void productDetailMap(ProductDetail productDetail, Product product, ProductFeaturesView productFeaturesView, List<ProductVendor> productVendors) {
        productDetail.setId(longToString(product.getId()));
        productDetail.setProductCode(product.getProductCode());
        productDetail.setProductName(product.getProductName());
        productDetail.setDescription(product.getDescription());
        productDetail.setImagePath(imagePaths(product));
        productDetail.setParentCategoryId(longToString(product.getParentCategory()));
        productDetail.setSubCategoryId(longToString(product.getSubCategory()));
        productDetail.setBrandId(longToString(productFeaturesView.getBrandId()));
        productDetail.setBrandName(productFeaturesView.getBrand());
        productDetail.setQualityId(longToString(productFeaturesView.getQualityId()));
        productDetail.setQualityName(productFeaturesView.getQuality());
        productDetail.setWeightId(longToString(productFeaturesView.getWeightId()));
        productDetail.setWeightName(productFeaturesView.getWeight());
        productDetail.setMaterialId(longToString(productFeaturesView.getMaterialId()));
        productDetail.setMaterialName(productFeaturesView.getMaterial());
        productDetail.setColorId(longToString(productFeaturesView.getColorId()));
        productDetail.setColorName(productFeaturesView.getColor());
        productDetail.setGradeId(longToString(productFeaturesView.getGradeId()));
        productDetail.setGradeName(productFeaturesView.getGrade());
        productDetail.setTypeId(longToString(productFeaturesView.getTypeId()));
        productDetail.setTypeName(productFeaturesView.getType());
        productDetail.setCrusherId(longToString(productFeaturesView.getCrusherId()));
        productDetail.setCrusherName(productFeaturesView.getCrusher());
        productDetail.setCrusherLocation(productFeaturesView.getCrusherLocation());
        productDetail.setUnitId(longToString(productFeaturesView.getUnitId()));
        productDetail.setUnitCode(productFeaturesView.getUnitCode());
        productDetail.setUnitName(productFeaturesView.getUnitName());
        productDetail.setSpecification(product.getSpecification());
        productDetail.setSize(product.getSize());
        productDetail.setDeliveryBy(byteToString(product.getDeliveryBy()));
        productDetail.setInStock(product.getInStock());
        productDetail.setHsnCode(product.getHsnCode());
        productDetail.setRateOfCgst(floatToString(product.getRateOfCgst()));
        productDetail.setRateOfSgst(floatToString(product.getRateOfSgst()));
        productDetail.setRateOfIgst(floatToString(product.getRateOfIgst()));
        productDetail.setMinOrderQty(shortToString(product.getMinOrderQty()));
        productDetail.setMaxOrderQty(shortToString(product.getMaxOrderQty()));
        productDetail.setTransGroup(product.getTransGroup());
        productDetail.setTaxInclusive(product.getTaxInclusive());
        productVendorDetailMap(productDetail, productVendors);
        shipmentDetailMap(productDetail, product.getTransGroup());
        productDetail.setQuantityList(ApiUtils.mapQuantityList(Integer.valueOf(product.getMinOrderQty())));
    }

    private void productVendorDetailMap(ProductDetail productDetail, List<ProductVendor> productVendors) {
        List<Vendor> vendors = vendorService.listActiveVendorsByVendorIds(productVendors.stream().map(ProductVendor::getVendorId).collect(Collectors.toList()));
        Map<Long, Vendor> vendorMap = vendors.stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        List<ProductVendorDetail> productVendorDetails = new ArrayList<>();
        productVendors.forEach(productVendor -> {
            ProductVendorDetail productVendorDetail = new ProductVendorDetail();
            productVendorDetail.setId(longToString(productVendor.getId()));
            productVendorDetail.setProductId(longToString(productVendor.getProductId()));
            productVendorDetail.setMrpRate(decimalToString(productVendor.getMrpRate()));
            productVendorDetail.setSaleRate(decimalToString(productVendor.getSaleRate()));
            if (productVendor.getVendorId() != null) {
                Vendor vendor = vendorMap.getOrDefault(productVendor.getVendorId(), new Vendor());
                productVendorDetail.setVendorId(longToString(vendor.getId()));
                productVendorDetail.setVendorCode(vendor.getVendorCode());
                productVendorDetail.setVendorName(vendor.getVendorName());
                productVendorDetail.setPinCode(vendor.getPinCode());
            }
            productVendorDetails.add(productVendorDetail);
        });
        productDetail.setProductVendorDetails(productVendorDetails);
    }

    private void shipmentDetailMap(ProductDetail productDetail, String transGroup) {
        List<Transport> transports = transportService.transportListByTransGroup(transGroup);
        List<ShipmentDetail> shipmentDetails = new ArrayList<>();
        transports.forEach(transport -> {
            ShipmentDetail shipmentDetail = new ShipmentDetail();
            shipmentDetail.setId(longToString(transport.getId()));
            shipmentDetail.setQuantity(intToString(transport.getQuantity()));
            shipmentDetail.setDistance(intToString(transport.getDistance()));
            shipmentDetail.setCharges(decimalToString(transport.getCharges()));
            shipmentDetail.setTransGroup(transport.getTransGroup());
            shipmentDetails.add(shipmentDetail);
        });
        productDetail.setShipmentDetails(shipmentDetails);
    }

    @ApiOperation("Get Product Image to Display")
    @GetMapping("/{productId}/{opt}/img")
    public void getProductImage(@ApiParam("Option") @PathVariable String opt, @ApiParam("Product Id") @PathVariable Long productId, HttpServletResponse response) throws UnitedSuppliesException {
        try {
            Product product = productService.findProductWithImages(productId);
            if (product != null && !product.getImagePath().isEmpty()) {
                Path path = Paths.get(product.getImagePath());
                if (opt.equalsIgnoreCase("opt1")) {
                    path = Paths.get(product.getOptImagePath1());
                    copy(product.getOptImage1(), response.getOutputStream());
                } else if (opt.equalsIgnoreCase("opt2")) {
                    path = Paths.get(product.getOptImagePath2());
                    copy(product.getOptImage2(), response.getOutputStream());
                } else if (opt.equalsIgnoreCase("opt3")) {
                    path = Paths.get(product.getOptImagePath3());
                    copy(product.getOptImage3(), response.getOutputStream());
                } else {
                    copy(product.getImage(), response.getOutputStream());
                }
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(product.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", productId, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
            }
        } catch (IOException e) {
            throw new UnitedSuppliesException(
                    "IO Exception occurred while viewing product image " + e.getMessage());
        }
    }
}
