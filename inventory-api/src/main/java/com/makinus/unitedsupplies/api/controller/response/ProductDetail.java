/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makinus.unitedsupplies.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;

import java.util.List;

/**
 * Created by sabique
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetail {

    private String id;
    private String productCode;
    private String productName;
    private String description;
    private List<String> imagePath;
    private String parentCategoryId;
    private String subCategoryId;
    private String brandId;
    private String brandName;
    private String qualityId;
    private String qualityName;
    private String weightId;
    private String weightName;
    private String materialId;
    private String materialName;
    private String colorId;
    private String colorName;
    private String gradeId;
    private String gradeName;
    private String typeId;
    private String typeName;
    private String specification;
    private String size;
    private String crusherId;
    private String crusherName;
    private String crusherLocation;
    private String unitId;
    private String unitCode;
    private String unitName;
    private String deliveryBy;
    private String inStock;
    private String hsnCode;
    private String rateOfCgst;
    private String rateOfSgst;
    private String rateOfIgst;
    private String minOrderQty;
    private String maxOrderQty;
    private String transGroup;
    private String taxInclusive;
    private String inCart = YNStatus.NO.getStatus();
    private List<ProductVendorDetail> productVendorDetails;
    private List<ShipmentDetail> shipmentDetails;
    private boolean deliveryAddressAvailable;
    private String showMoreText = ApiUtils.SHOW_MORE_TEXT;
    private List<String> quantityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getQualityId() {
        return qualityId;
    }

    public void setQualityId(String qualityId) {
        this.qualityId = qualityId;
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCrusherId() {
        return crusherId;
    }

    public void setCrusherId(String crusherId) {
        this.crusherId = crusherId;
    }

    public String getCrusherName() {
        return crusherName;
    }

    public void setCrusherName(String crusherName) {
        this.crusherName = crusherName;
    }

    public String getCrusherLocation() {
        return crusherLocation;
    }

    public void setCrusherLocation(String crusherLocation) {
        this.crusherLocation = crusherLocation;
    }

    public String getWeightId() {
        return weightId;
    }

    public void setWeightId(String weightId) {
        this.weightId = weightId;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getInCart() {
        return inCart;
    }

    public void setInCart(String inCart) {
        this.inCart = inCart;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getRateOfCgst() {
        return rateOfCgst;
    }

    public void setRateOfCgst(String rateOfCgst) {
        this.rateOfCgst = rateOfCgst;
    }

    public String getRateOfSgst() {
        return rateOfSgst;
    }

    public void setRateOfSgst(String rateOfSgst) {
        this.rateOfSgst = rateOfSgst;
    }

    public String getRateOfIgst() {
        return rateOfIgst;
    }

    public void setRateOfIgst(String rateOfIgst) {
        this.rateOfIgst = rateOfIgst;
    }

    public String getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public String getTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(String taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public List<ProductVendorDetail> getProductVendorDetails() {
        return productVendorDetails;
    }

    public void setProductVendorDetails(List<ProductVendorDetail> productVendorDetails) {
        this.productVendorDetails = productVendorDetails;
    }

    public List<ShipmentDetail> getShipmentDetails() {
        return shipmentDetails;
    }

    public void setShipmentDetails(List<ShipmentDetail> shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }

    public boolean isDeliveryAddressAvailable() {
        return deliveryAddressAvailable;
    }

    public void setDeliveryAddressAvailable(boolean deliveryAddressAvailable) {
        this.deliveryAddressAvailable = deliveryAddressAvailable;
    }

    public String getShowMoreText() {
        return showMoreText;
    }

    public void setShowMoreText(String showMoreText) {
        this.showMoreText = showMoreText;
    }

    public List<String> getQuantityList() {
        return quantityList;
    }

    public void setQuantityList(List<String> quantityList) {
        this.quantityList = quantityList;
    }

    public String getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(String maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }
}
