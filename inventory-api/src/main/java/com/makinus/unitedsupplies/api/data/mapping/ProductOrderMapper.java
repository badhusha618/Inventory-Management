/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.api.controller.request.ProductOrderRequest;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.mapper.ListEntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.category.CategoryService;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.unit.UnitService;
import com.makinus.unitedsupplies.common.data.service.unitmapping.UnitMappingService;
import com.makinus.unitedsupplies.common.data.service.vendor.VendorService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.utils.AppUtils.stringToLong;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("ProductOrderMapper")
public class ProductOrderMapper
        implements ListEntityWithExtraValueMapper<List<ProductOrderRequest>, OrderFulfillment, ProductOrder> {

    private final Logger LOG = LogManager.getLogger(ProductOrderMapper.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVendorService productVendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitMappingService unitMappingService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private VendorService vendorService;

    @Override
    public List<ProductOrder> map(List<ProductOrderRequest> productOrderRequests, OrderFulfillment orderFulfillment) throws UnitedSuppliesException {
        LOG.info("Map Product Order Requests to Product Order List Entity");
        List<ProductOrder> productOrders = new ArrayList<>();
        Map<Long, Vendor> vendorMap = vendorService.vendorList().stream().collect(Collectors.toMap(Vendor::getId, Function.identity()));
        List<ProductVendor> productVendors = productVendorService.productVendorListByProductIds(productOrderRequests.stream().map(p -> stringToLong(p.getProductId())).collect(Collectors.toList()));
        Map<Long, List<ProductVendor>> productVendorsMap = productVendors.stream().collect(Collectors.groupingBy(ProductVendor::getProductId));
        List<Product> products = productService.productListByIds(productOrderRequests.stream().map(p -> stringToLong(p.getProductId())).collect(Collectors.toList()));
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        Map<Long, Category> categoryMap = categoryService.categoryList().stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        Map<Long, Unit> unitMap = unitService.unitList().stream().collect(Collectors.toMap(Unit::getId, Function.identity()));
        productOrderRequests.forEach(por -> {
            ProductOrder productOrder = new ProductOrder();
            Product product = productMap.getOrDefault(stringToLong(por.getProductId()), new Product());
            Map<Long, ProductVendor> productVendorMap = productVendorsMap.getOrDefault(product.getId(), new ArrayList<>()).stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
            Unit unit = unitMap.getOrDefault(product.getUnit(), new Unit());
            ProductVendor productVendor = productVendorMap.getOrDefault(stringToLong(por.getProdVendorId()), new ProductVendor());
            Vendor vendor = vendorMap.getOrDefault(productVendor.getVendorId(), new Vendor());
            productOrder.setOrderRef(orderFulfillment.getOrderRef());
            productOrder.setFulfillmentId(orderFulfillment.getId());
            productOrder.setProductId(stringToLong(por.getProductId()));
            productOrder.setProductCode(product.getProductCode());
            productOrder.setProductName(product.getProductName());
            productOrder.setVendorCode(vendor.getVendorCode());
            productOrder.setVendorName(vendor.getVendorName());
            productOrder.setGstNo(vendor.getGstNo());
            productOrder.setPanNo(vendor.getPanNo());
            productOrder.setDeliveryBy(product.getDeliveryBy());
            productOrder.setInitProdVendorId(stringToLong(por.getProdVendorId()));
            productOrder.setProdVendorId(stringToLong(por.getProdVendorId()));
            productOrder.setProQuantity(por.getQuantity());
            productOrder.setInitProdSaleRate(productVendor.getSaleRate());
            productOrder.setProdSaleRate(productVendor.getSaleRate());
            productOrder.setPinCode(productVendor.getPinCode());
            productOrder.setTransportCharges(por.getTransportCharge());
            productOrder.setInitTransportCharges(por.getTransportCharge());
            productOrder.setLoadingCharges(por.getLoadingCharge());
            productOrder.setStatus(ProdOrderStatus.NEW.getStatus());
            productOrder.setFulfillmentStatus(YNStatus.NO.getStatus());
            productOrder.setDescription(product.getDescription());
            productOrder.setImagePath(product.getImagePath());
            productOrder.setOptImagePath1(product.getOptImagePath1());
            productOrder.setOptImagePath2(product.getOptImagePath2());
            productOrder.setOptImagePath3(product.getOptImagePath3());
            productOrder.setParentCategoryName(categoryMap.getOrDefault(product.getParentCategory(), new Category()).getCategoryName());
            productOrder.setSubCategoryName(categoryMap.getOrDefault(product.getSubCategory(), new Category()).getCategoryName());
            productOrder.setBrandName(product.getBrandName());
            productOrder.setQualityName(product.getQualityName());
            productOrder.setWeightName(product.getWeightName());
            productOrder.setMaterialName(product.getMaterialName());
            productOrder.setColorName(product.getColorName());
            productOrder.setGradeName(product.getGradeName());
            productOrder.setTypeName(product.getTypeName());
            productOrder.setSpecification(product.getSpecification());
            productOrder.setSize(product.getSize());
            productOrder.setCrusherName(product.getCrusherName());
            productOrder.setUnitId(product.getUnit());
            productOrder.setUnitName(unit.getUnitName());
            productOrder.setUnitCode(unit.getUnitCode());
            productOrder.setHsnCode(product.getHsnCode());
            productOrder.setRateOfCgst(product.getTaxInclusive().equals(YNStatus.YES.getStatus()) ? product.getRateOfCgst() : 0);
            productOrder.setRateOfSgst(product.getTaxInclusive().equals(YNStatus.YES.getStatus()) ? product.getRateOfSgst() : 0);
            productOrder.setRateOfIgst(product.getTaxInclusive().equals(YNStatus.YES.getStatus()) ? product.getRateOfIgst() : 0);
            productOrder.setTaxInclusive(product.getTaxInclusive());
            productOrder.setTransGroup(product.getTransGroup());
            productOrders.add(productOrder);
        });
        return productOrders;
    }
}