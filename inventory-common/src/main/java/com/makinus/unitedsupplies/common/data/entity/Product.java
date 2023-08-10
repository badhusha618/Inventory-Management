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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Bad_sha
 */
@Entity
@Table(name = "PRODUCTS")
@JsonIgnoreProperties({
        "image",
        "originalFileName",
        "optImage1",
        "optImage1FileName",
        "optImage2",
        "optImage2FileName",
        "optImage3",
        "optImage3FileName",
        "createdDateAsFolderName",
        "imagePath",
        "optImagePath1",
        "optImagePath2",
        "optImagePath3",
        "createdBy",
        "createdDate",
        "updatedBy",
        "updatedDate",
        "deleted"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Serializable {

    private static final long serialVersionUID = 3454345656L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "DESCRIPTION")
    private String description;

    @ApiModelProperty(hidden = true)
    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @ApiModelProperty(hidden = true)
    @Column(name = "IMAGE_PATH_OPT1")
    private String optImagePath1;

    @ApiModelProperty(hidden = true)
    @Column(name = "IMAGE_PATH_OPT2")
    private String optImagePath2;

    @ApiModelProperty(hidden = true)
    @Column(name = "IMAGE_PATH_OPT3")
    private String optImagePath3;

    @Column(name = "PARENT_CATEGORY")
    private Long parentCategory;

    @Column(name = "SUB_CATEGORY")
    private Long subCategory;

    @Column(name = "BRAND_ID")
    private Long brand;

    @Column(name = "QUALITY_ID")
    private Long quality;

    @Column(name = "WEIGHT_ID")
    private Long weight;

    @Column(name = "MATERIAL_ID")
    private Long material;

    @Column(name = "COLOR_ID")
    private Long color;

    @Column(name = "GRADE_ID")
    private Long grade;

    @Column(name = "TYPE_ID")
    private Long type;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "CRUSHER_ID")
    private Long crusher;

    @Column(name = "UNIT_ID")
    private Long unit;

    @Column(name = "DELIVERY_BY")
    private Byte deliveryBy;

    @ApiModelProperty(hidden = true)
    @Column(name = "CREATED_BY")
    private String createdBy;

    @ApiModelProperty(hidden = true)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @ApiModelProperty(hidden = true)
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @ApiModelProperty(hidden = true)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "IN_STOCK")
    private String inStock;

    @Column(name = "HSN_CODE")
    private String hsnCode;

    @Column(name = "RATE_OF_CGST")
    private Float rateOfCgst;

    @Column(name = "RATE_OF_SGST")
    private Float rateOfSgst;

    @Column(name = "RATE_OF_IGST")
    private Float rateOfIgst;

    @Column(name = "MIN_ORDER_QTY")
    private short minOrderQty;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "TRANS_GROUP")
    private String transGroup;

    @ApiModelProperty(hidden = true)
    @Column(name = "DELETED")
    private String deleted;

    @Column(name = "TAX_INCLUSIVE")
    private String taxInclusive;

    @Column(name = "MAX_ORDER_QTY")
    private Short maxOrderQty;

    @ApiModelProperty(hidden = true)
    @Transient
    private byte[] image;

    @ApiModelProperty(hidden = true)
    @Transient
    private String originalFileName;

    @ApiModelProperty(hidden = true)
    @Transient
    private byte[] optImage1;

    @ApiModelProperty(hidden = true)
    @Transient
    private String optImage1FileName;

    @ApiModelProperty(hidden = true)
    @Transient
    private byte[] optImage2;

    @ApiModelProperty(hidden = true)
    @Transient
    private String optImage2FileName;

    @ApiModelProperty(hidden = true)
    @Transient
    private byte[] optImage3;

    @ApiModelProperty(hidden = true)
    @Transient
    private String optImage3FileName;

    @ApiModelProperty(hidden = true)
    @Transient
    private String createdDateAsFolderName;

    @Transient
    private String unitCode;
    @Transient
    private String unitName;
    @Transient
    private String brandName;
    @Transient
    private String crusherName;
    @Transient
    private String crusherLocation;
    @Transient
    private String gradeName;
    @Transient
    private String qualityName;
    @Transient
    private String weightName;
    @Transient
    private String materialName;
    @Transient
    private String colorName;
    @Transient
    private String typeName;
    @Transient
    private BigDecimal saleRate;
    @Transient
    private BigDecimal mrpRate;
    @Transient
    private String inCart = YNStatus.NO.getStatus();
    @Transient
    private List<String> images = new ArrayList<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOptImagePath1() {
        return optImagePath1;
    }

    public void setOptImagePath1(String optImagePath1) {
        this.optImagePath1 = optImagePath1;
    }

    public String getOptImagePath2() {
        return optImagePath2;
    }

    public void setOptImagePath2(String optImagePath2) {
        this.optImagePath2 = optImagePath2;
    }

    public String getOptImagePath3() {
        return optImagePath3;
    }

    public void setOptImagePath3(String optImagePath3) {
        this.optImagePath3 = optImagePath3;
    }

    public Long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Long parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Long getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Long subCategory) {
        this.subCategory = subCategory;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Long getQuality() {
        return quality;
    }

    public void setQuality(Long quality) {
        this.quality = quality;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getMaterial() {
        return material;
    }

    public void setMaterial(Long material) {
        this.material = material;
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

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCrusher() {
        return crusher;
    }

    public void setCrusher(Long crusher) {
        this.crusher = crusher;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getCrusherLocation() {
        return crusherLocation;
    }

    public void setCrusherLocation(String crusherLocation) {
        this.crusherLocation = crusherLocation;
    }

    public Long getUnit() { return unit; }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Byte getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(Byte deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public byte[] getOptImage1() {
        return optImage1;
    }

    public void setOptImage1(byte[] optImage1) {
        this.optImage1 = optImage1;
    }

    public String getOptImage1FileName() {
        return optImage1FileName;
    }

    public void setOptImage1FileName(String optImage1FileName) {
        this.optImage1FileName = optImage1FileName;
    }

    public byte[] getOptImage2() {
        return optImage2;
    }

    public void setOptImage2(byte[] optImage2) {
        this.optImage2 = optImage2;
    }

    public String getOptImage2FileName() {
        return optImage2FileName;
    }

    public void setOptImage2FileName(String optImage2FileName) {
        this.optImage2FileName = optImage2FileName;
    }

    public byte[] getOptImage3() {
        return optImage3;
    }

    public void setOptImage3(byte[] optImage3) {
        this.optImage3 = optImage3;
    }

    public String getOptImage3FileName() {
        return optImage3FileName;
    }

    public void setOptImage3FileName(String optImage3FileName) {
        this.optImage3FileName = optImage3FileName;
    }

    public String getCreatedDateAsFolderName() {
        return createdDateAsFolderName;
    }

    public void setCreatedDateAsFolderName(String createdDateAsFolderName) {
        this.createdDateAsFolderName = createdDateAsFolderName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCrusherName() {
        return crusherName;
    }

    public void setCrusherName(String crusherName) {
        this.crusherName = crusherName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getInCart() {
        return inCart;
    }

    public void setInCart(String inCart) {
        this.inCart = inCart;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public Float getRateOfCgst() {
        return rateOfCgst;
    }

    public void setRateOfCgst(Float rateOfCgst) {
        this.rateOfCgst = rateOfCgst;
    }

    public Float getRateOfSgst() {
        return rateOfSgst;
    }

    public void setRateOfSgst(Float rateOfSgst) {
        this.rateOfSgst = rateOfSgst;
    }

    public Float getRateOfIgst() {
        return rateOfIgst;
    }

    public void setRateOfIgst(Float rateOfIgst) {
        this.rateOfIgst = rateOfIgst;
    }

    public Short getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(short minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(String taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public Short getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(Short maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return minOrderQty == product.minOrderQty &&
                maxOrderQty == product.maxOrderQty &&
                Objects.equals(id, product.id) &&
                Objects.equals(productCode, product.productCode) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imagePath, product.imagePath) &&
                Objects.equals(optImagePath1, product.optImagePath1) &&
                Objects.equals(optImagePath2, product.optImagePath2) &&
                Objects.equals(optImagePath3, product.optImagePath3) &&
                Objects.equals(parentCategory, product.parentCategory) &&
                Objects.equals(subCategory, product.subCategory) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(quality, product.quality) &&
                Objects.equals(weight, product.weight) &&
                Objects.equals(material, product.material) &&
                Objects.equals(color, product.color) &&
                Objects.equals(grade, product.grade) &&
                Objects.equals(type, product.type) &&
                Objects.equals(specification, product.specification) &&
                Objects.equals(size, product.size) &&
                Objects.equals(crusher, product.crusher) &&
                Objects.equals(unit, product.unit) &&
                Objects.equals(deliveryBy, product.deliveryBy) &&
                Objects.equals(createdBy, product.createdBy) &&
                Objects.equals(createdDate, product.createdDate) &&
                Objects.equals(updatedBy, product.updatedBy) &&
                Objects.equals(updatedDate, product.updatedDate) &&
                Objects.equals(inStock, product.inStock) &&
                Objects.equals(hsnCode, product.hsnCode) &&
                Objects.equals(rateOfCgst, product.rateOfCgst) &&
                Objects.equals(rateOfSgst, product.rateOfSgst) &&
                Objects.equals(rateOfIgst, product.rateOfIgst) &&
                Objects.equals(deleted, product.deleted) &&
                Objects.equals(active, product.active) &&
                Objects.equals(transGroup, product.transGroup) &&
                Objects.equals(taxInclusive, product.taxInclusive) &&
                Arrays.equals(image, product.image) &&
                Objects.equals(originalFileName, product.originalFileName) &&
                Arrays.equals(optImage1, product.optImage1) &&
                Objects.equals(optImage1FileName, product.optImage1FileName) &&
                Arrays.equals(optImage2, product.optImage2) &&
                Objects.equals(optImage2FileName, product.optImage2FileName) &&
                Arrays.equals(optImage3, product.optImage3) &&
                Objects.equals(optImage3FileName, product.optImage3FileName) &&
                Objects.equals(createdDateAsFolderName, product.createdDateAsFolderName) &&
                Objects.equals(unitCode, product.unitCode) &&
                Objects.equals(unitName, product.unitName) &&
                Objects.equals(brandName, product.brandName) &&
                Objects.equals(crusherName, product.crusherName) &&
                Objects.equals(crusherLocation, product.crusherLocation) &&
                Objects.equals(gradeName, product.gradeName) &&
                Objects.equals(qualityName, product.qualityName) &&
                Objects.equals(weightName, product.weightName) &&
                Objects.equals(materialName, product.materialName) &&
                Objects.equals(colorName, product.colorName) &&
                Objects.equals(typeName, product.typeName) &&
                Objects.equals(saleRate, product.saleRate) &&
                Objects.equals(mrpRate, product.mrpRate) &&
                Objects.equals(inCart, product.inCart) &&
                Objects.equals(images, product.images);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, productCode, productName, description, imagePath, optImagePath1, optImagePath2, optImagePath3, parentCategory, subCategory, brand, quality, weight, material, color, grade, type, specification, size, crusher, unit, deliveryBy, createdBy, createdDate, updatedBy, updatedDate, inStock, hsnCode, rateOfCgst, rateOfSgst, rateOfIgst, minOrderQty, deleted, active,transGroup, taxInclusive, maxOrderQty, originalFileName, optImage1FileName, optImage2FileName, optImage3FileName, createdDateAsFolderName, unitCode, unitName, brandName, crusherName, crusherLocation, gradeName, qualityName, weightName, materialName, colorName, typeName, saleRate, mrpRate, inCart, images);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + Arrays.hashCode(optImage1);
        result = 31 * result + Arrays.hashCode(optImage2);
        result = 31 * result + Arrays.hashCode(optImage3);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", optImagePath1='" + optImagePath1 + '\'' +
                ", optImagePath2='" + optImagePath2 + '\'' +
                ", optImagePath3='" + optImagePath3 + '\'' +
                ", parentCategory=" + parentCategory +
                ", subCategory=" + subCategory +
                ", brand=" + brand +
                ", quality=" + quality +
                ", weight=" + weight +
                ", material=" + material +
                ", color=" + color +
                ", grade=" + grade +
                ", type=" + type +
                ", specification='" + specification + '\'' +
                ", size='" + size + '\'' +
                ", crusher=" + crusher +
                ", unit='" + unit + '\'' +
                ", deliveryBy=" + deliveryBy +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", inStock='" + inStock + '\'' +
                ", hsnCode='" + hsnCode + '\'' +
                ", rateOfCgst=" + rateOfCgst +
                ", rateOfSgst=" + rateOfSgst +
                ", rateOfIgst=" + rateOfIgst +
                ", minOrderQty=" + minOrderQty +
                ", deleted='" + deleted + '\'' +
                ", active='" + active + '\'' +
                ", transGroup='" + transGroup + '\'' +
                ", taxInclusive='" + taxInclusive + '\'' +
                ", maxOrderQty=" + maxOrderQty +
                ", image=" + Arrays.toString(image) +
                ", originalFileName='" + originalFileName + '\'' +
                ", optImage1=" + Arrays.toString(optImage1) +
                ", optImage1FileName='" + optImage1FileName + '\'' +
                ", optImage2=" + Arrays.toString(optImage2) +
                ", optImage2FileName='" + optImage2FileName + '\'' +
                ", optImage3=" + Arrays.toString(optImage3) +
                ", optImage3FileName='" + optImage3FileName + '\'' +
                ", createdDateAsFolderName='" + createdDateAsFolderName + '\'' +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", crusherName='" + crusherName + '\'' +
                ", crusherLocation='" + crusherLocation + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", qualityName='" + qualityName + '\'' +
                ", weightName='" + weightName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", colorName='" + colorName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", saleRate=" + saleRate +
                ", mrpRate=" + mrpRate +
                ", inCart='" + inCart + '\'' +
                ", images=" + images +
                '}';
    }
}
