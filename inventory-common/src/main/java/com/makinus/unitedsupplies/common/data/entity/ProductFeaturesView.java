/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by abuabdul
 */
@Entity
@Table(name = "PRODUCT_FEATURES_VIEW")
public class ProductFeaturesView {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "CRUSHER_ID")
    private Long crusherId;

    @Column(name = "CRUSHER")
    private String crusher;

    @Column(name = "CRUSHER_LOCATION")
    private String crusherLocation;

    @Column(name = "GRADE_ID")
    private Long gradeId;

    @Column(name = "GRADE")
    private String grade;

    @Column(name = "QUALITY_ID")
    private Long qualityId;

    @Column(name = "QUALITY")
    private String quality;

    @Column(name = "WEIGHT_ID")
    private Long weightId;

    @Column(name = "WEIGHT")
    private String weight;

    @Column(name = "TYPE_ID")
    private Long typeId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "UNIT_CODE")
    private String unitCode;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "MATERIAL_ID")
    private Long materialId;

    @Column(name = "MATERIAL")
    private String material;

    @Column(name = "COLOR_ID")
    private Long colorId;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "SALE_RATE")
    private BigDecimal saleRate;

    @Column(name = "MRP_RATE")
    private BigDecimal mrpRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCrusherId() {
        return crusherId;
    }

    public void setCrusherId(Long crusherId) {
        this.crusherId = crusherId;
    }

    public String getCrusher() {
        return crusher;
    }

    public void setCrusher(String crusher) {
        this.crusher = crusher;
    }

    public String getCrusherLocation() {
        return crusherLocation;
    }

    public void setCrusherLocation(String crusherLocation) {
        this.crusherLocation = crusherLocation;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getQualityId() {
        return qualityId;
    }

    public void setQualityId(Long qualityId) {
        this.qualityId = qualityId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Long getWeightId() {
        return weightId;
    }

    public void setWeightId(Long weightId) {
        this.weightId = weightId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(BigDecimal saleRate) {
        this.saleRate = saleRate;
    }

    public BigDecimal getMrpRate() {
        return mrpRate;
    }

    public void setMrpRate(BigDecimal mrpRate) {
        this.mrpRate = mrpRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFeaturesView that = (ProductFeaturesView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(brandId, that.brandId) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(crusherId, that.crusherId) &&
                Objects.equals(crusher, that.crusher) &&
                Objects.equals(crusherLocation, that.crusherLocation) &&
                Objects.equals(gradeId, that.gradeId) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(qualityId, that.qualityId) &&
                Objects.equals(quality, that.quality) &&
                Objects.equals(weightId, that.weightId) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(unitId, that.unitId) &&
                Objects.equals(unitCode, that.unitCode) &&
                Objects.equals(unitName, that.unitName) &&
                Objects.equals(materialId, that.materialId) &&
                Objects.equals(material, that.material) &&
                Objects.equals(colorId, that.colorId) &&
                Objects.equals(color, that.color) &&
                Objects.equals(saleRate, that.saleRate) &&
                Objects.equals(mrpRate, that.mrpRate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, brandId, brand, crusherId, crusher, crusherLocation, gradeId, grade, qualityId, quality, weightId, weight, typeId, type, unitId, unitCode, unitName, materialId, material, colorId, color, saleRate, mrpRate);
    }

    @Override
    public String toString() {
        return "ProductFeaturesView{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", brand='" + brand + '\'' +
                ", crusherId=" + crusherId +
                ", crusher='" + crusher + '\'' +
                ", crusherLocation='" + crusherLocation + '\'' +
                ", gradeId=" + gradeId +
                ", grade='" + grade + '\'' +
                ", qualityId=" + qualityId +
                ", quality='" + quality + '\'' +
                ", weightId=" + weightId +
                ", weight='" + weight + '\'' +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                ", unitId=" + unitId +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", materialId=" + materialId +
                ", material='" + material + '\'' +
                ", colorId=" + colorId +
                ", color='" + color + '\'' +
                ", saleRate=" + saleRate +
                ", mrpRate=" + mrpRate +
                '}';
    }
}
