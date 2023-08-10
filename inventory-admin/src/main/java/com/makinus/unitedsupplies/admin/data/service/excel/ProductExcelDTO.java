/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.service.excel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ammar
 */
public class ProductExcelDTO {

    private String id;
    private String productName;
    private String description;
    private String parentCategory;
    private String subCategory;
    private String brand;
    private String quality;
    private String grade;
    private String type;
    private String size;
    private String crusher;
    private String unit;
    private String weight;
    private String material;
    private String color;
    private String specification;
    private BigDecimal mrpRate;
    private BigDecimal saleRate;
    private String productCode;
    private String hsnCode;
    private String rateOfCgst;
    private String rateOfSgst;
    private String rateOfIgst;
    private String minOrderQty;
    private List<ProdVendorExcelDTO> prodVendorExcelDTOs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCrusher() {
        return crusher;
    }

    public void setCrusher(String crusher) {
        this.crusher = crusher;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getMrpRate() {
        return mrpRate;
    }

    public void setMrpRate(BigDecimal mrpRate) {
        this.mrpRate = mrpRate;
    }

    public BigDecimal getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(BigDecimal saleRate) {
        this.saleRate = saleRate;
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public String getMaterial() { return material; }

    public void setMaterial(String material) { this.material = material; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public String getSpecification() { return specification; }

    public void setSpecification(String specification) { this.specification = specification; }

    public String getHsnCode() { return hsnCode; }

    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

    public String getRateOfCgst() { return rateOfCgst; }

    public void setRateOfCgst(String rateOfCgst) { this.rateOfCgst = rateOfCgst; }

    public String getRateOfSgst() { return rateOfSgst; }

    public void setRateOfSgst(String rateOfSgst) { this.rateOfSgst = rateOfSgst; }

    public String getRateOfIgst() { return rateOfIgst; }

    public void setRateOfIgst(String rateOfIgst) { this.rateOfIgst = rateOfIgst; }

    public String getMinOrderQty() { return minOrderQty; }

    public void setMinOrderQty(String minOrderQty) { this.minOrderQty = minOrderQty; }

    public List<ProdVendorExcelDTO> getProdVendorExcelDTOs() {
        return prodVendorExcelDTOs;
    }

    public void setProdVendorExcelDTOs(List<ProdVendorExcelDTO> prodVendorExcelDTOs) {
        this.prodVendorExcelDTOs = prodVendorExcelDTOs;
    }
}
