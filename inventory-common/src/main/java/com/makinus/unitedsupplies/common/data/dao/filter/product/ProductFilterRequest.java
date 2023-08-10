/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.filter.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author nizamabdul
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFilterRequest {

    @ApiModelProperty(notes = "Type ID", example = "[1, 2, 3]", position = 1)
    private List<Long> typeId;

    @ApiModelProperty(notes = "Grade ID", example = "[1, 2, 3]", position = 2)
    private List<Long> gradeId;

    @ApiModelProperty(notes = "Brand ID", example = "[1, 2, 3]", position = 3)
    private List<Long> brandId;

    @ApiModelProperty(notes = "Material ID", example = "[1, 2, 3]", position = 4)
    private List<Long> materialId;

    @ApiModelProperty(notes = "Color ID", example = "[1, 2, 3]", position = 5)
    private List<Long> colorId;

    @ApiModelProperty(notes = "Quality ID", example = "[1, 2, 3]", position = 6)
    private List<Long> qualityId;

    @ApiModelProperty(notes = "Crusher ID", example = "[1, 2, 3]", position = 7)
    private List<Long> crusherId;

    @ApiModelProperty(notes = "Unit ID", example = "[1, 2, 3]", position = 8)
    private List<Long> unitId;

    @ApiModelProperty(notes = "In Stock", example = "T or F", position = 9)
    private String inStock;

    @ApiModelProperty(notes = "Min Price", example = "0", position = 10)
    private String minPrice;

    @ApiModelProperty(notes = "Max Price", example = "100000", position = 11)
    private String maxPrice;

    public List<Long> getTypeId() {
        return typeId;
    }

    public void setTypeId(List<Long> typeId) {
        this.typeId = typeId;
    }

    public List<Long> getGradeId() {
        return gradeId;
    }

    public void setGradeId(List<Long> gradeId) {
        this.gradeId = gradeId;
    }

    public List<Long> getBrandId() {
        return brandId;
    }

    public void setBrandId(List<Long> brandId) {
        this.brandId = brandId;
    }

    public List<Long> getMaterialId() {
        return materialId;
    }

    public void setMaterialId(List<Long> materialId) {
        this.materialId = materialId;
    }

    public List<Long> getColorId() {
        return colorId;
    }

    public void setColorId(List<Long> colorId) {
        this.colorId = colorId;
    }

    public List<Long> getQualityId() {
        return qualityId;
    }

    public void setQualityId(List<Long> qualityId) {
        this.qualityId = qualityId;
    }

    public List<Long> getCrusherId() {
        return crusherId;
    }

    public void setCrusherId(List<Long> crusherId) {
        this.crusherId = crusherId;
    }

    public List<Long> getUnitId() {
        return unitId;
    }

    public void setUnitId(List<Long> unitId) {
        this.unitId = unitId;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
