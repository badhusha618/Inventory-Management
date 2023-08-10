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

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Bad_sha
 */
@Entity
@Table(name = "PROD_ORDERS")
public class ProductOrder {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_REF")
    private Long orderRef;

    @Column(name = "FULFILLMENT_ID")
    private Long fulfillmentId;

    @Column(name = "PROD_ID")
    private Long productId;

    @Column(name = "PROD_VEND_ID")
    private Long prodVendorId;

    @Column(name = "INIT_PROD_VEND_ID")
    private Long initProdVendorId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "FULFILLMENT_STATUS")
    private String fulfillmentStatus;

    @Column(name = "PRO_QTY")
    private Integer proQuantity;

    @Column(name = "PRO_SALE_RATE")
    private BigDecimal prodSaleRate;

    @Column(name = "INIT_PRO_SALE_RATE")
    private BigDecimal initProdSaleRate;

    @Column(name = "LOAD_CHARGES")
    private BigDecimal loadingCharges;

    @Column(name = "TRANS_CHARGES")
    private BigDecimal transportCharges;

    @Column(name = "INIT_TRANS_CHARGES")
    private BigDecimal initTransportCharges;

    @Column(name = "HSN_CODE")
    private String hsnCode;

    @Column(name = "RATE_OF_CGST")
    private Float rateOfCgst;

    @Column(name = "RATE_OF_SGST")
    private Float rateOfSgst;

    @Column(name = "RATE_OF_IGST")
    private Float rateOfIgst;

    @Column(name = "TAX_INCLUSIVE")
    private String taxInclusive;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "VENDOR_CODE")
    private String vendorCode;

    @Column(name = "VENDOR_NAME")
    private String vendorName;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "GST_NO")
    private String gstNo;

    @Column(name = "PAN_NO")
    private String panNo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DELIVERY_BY")
    private Byte deliveryBy;

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

    @Column(name = "PARENT_CATEGORY_NAME")
    private String parentCategoryName;

    @Column(name = "SUB_CATEGORY_NAME")
    private String subCategoryName;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Column(name = "QUALITY_NAME")
    private String qualityName;

    @Column(name = "WEIGHT_NAME")
    private String weightName;

    @Column(name = "MATERIAL_NAME")
    private String materialName;

    @Column(name = "COLOR_NAME")
    private String colorName;

    @Column(name = "GRADE_NAME")
    private String gradeName;

    @Column(name = "TYPE_NAME")
    private String typeName;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "CRUSHER_NAME")
    private String crusherName;

    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "UNIT_CODE")
    private String unitCode;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "TRANS_GROUP")
    private String transGroup;

    public ProductOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(Long orderRef) {
        this.orderRef = orderRef;
    }

    public Long getFulfillmentId() {
        return fulfillmentId;
    }

    public void setFulfillmentId(Long fulfillmentId) {
        this.fulfillmentId = fulfillmentId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProdVendorId() {
        return prodVendorId;
    }

    public void setProdVendorId(Long prodVendorId) {
        this.prodVendorId = prodVendorId;
    }

    public Long getInitProdVendorId() {
        return initProdVendorId;
    }

    public void setInitProdVendorId(Long initProdVendorId) {
        this.initProdVendorId = initProdVendorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public Integer getProQuantity() {
        return proQuantity;
    }

    public void setProQuantity(Integer proQuantity) {
        this.proQuantity = proQuantity;
    }

    public BigDecimal getProdSaleRate() {
        return prodSaleRate;
    }

    public void setProdSaleRate(BigDecimal prodSaleRate) {
        this.prodSaleRate = prodSaleRate;
    }

    public BigDecimal getInitProdSaleRate() {
        return initProdSaleRate;
    }

    public void setInitProdSaleRate(BigDecimal initProdSaleRate) {
        this.initProdSaleRate = initProdSaleRate;
    }

    public BigDecimal getLoadingCharges() {
        return loadingCharges;
    }

    public void setLoadingCharges(BigDecimal loadingCharges) {
        this.loadingCharges = loadingCharges;
    }

    public BigDecimal getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(BigDecimal transportCharges) {
        this.transportCharges = transportCharges;
    }

    public BigDecimal getInitTransportCharges() {
        return initTransportCharges;
    }

    public void setInitTransportCharges(BigDecimal initTransportCharges) {
        this.initTransportCharges = initTransportCharges;
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

    public String getTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(String taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(Byte deliveryBy) {
        this.deliveryBy = deliveryBy;
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

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setCrusherName(String crusherName) {
        this.crusherName = crusherName;
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

    public String getCrusherName() {
        return crusherName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderRef, that.orderRef) &&
                Objects.equals(fulfillmentId, that.fulfillmentId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(prodVendorId, that.prodVendorId) &&
                Objects.equals(initProdVendorId, that.initProdVendorId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(fulfillmentStatus, that.fulfillmentStatus) &&
                Objects.equals(proQuantity, that.proQuantity) &&
                Objects.equals(prodSaleRate, that.prodSaleRate) &&
                Objects.equals(initProdSaleRate, that.initProdSaleRate) &&
                Objects.equals(loadingCharges, that.loadingCharges) &&
                Objects.equals(transportCharges, that.transportCharges) &&
                Objects.equals(initTransportCharges, that.initTransportCharges) &&
                Objects.equals(hsnCode, that.hsnCode) &&
                Objects.equals(rateOfCgst, that.rateOfCgst) &&
                Objects.equals(rateOfSgst, that.rateOfSgst) &&
                Objects.equals(rateOfIgst, that.rateOfIgst) &&
                Objects.equals(taxInclusive, that.taxInclusive) &&
                Objects.equals(productCode, that.productCode) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(vendorCode, that.vendorCode) &&
                Objects.equals(vendorName, that.vendorName) &&
                Objects.equals(pinCode, that.pinCode) &&
                Objects.equals(gstNo, that.gstNo) &&
                Objects.equals(panNo, that.panNo) &&
                Objects.equals(description, that.description) &&
                Objects.equals(deliveryBy, that.deliveryBy) &&
                Objects.equals(imagePath, that.imagePath) &&
                Objects.equals(optImagePath1, that.optImagePath1) &&
                Objects.equals(optImagePath2, that.optImagePath2) &&
                Objects.equals(optImagePath3, that.optImagePath3) &&
                Objects.equals(parentCategoryName, that.parentCategoryName) &&
                Objects.equals(subCategoryName, that.subCategoryName) &&
                Objects.equals(brandName, that.brandName) &&
                Objects.equals(qualityName, that.qualityName) &&
                Objects.equals(weightName, that.weightName) &&
                Objects.equals(materialName, that.materialName) &&
                Objects.equals(colorName, that.colorName) &&
                Objects.equals(gradeName, that.gradeName) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(specification, that.specification) &&
                Objects.equals(size, that.size) &&
                Objects.equals(crusherName, that.crusherName) &&
                Objects.equals(unitId, that.unitId) &&
                Objects.equals(unitCode, that.unitCode) &&
                Objects.equals(unitName, that.unitName) &&
                Objects.equals(transGroup, that.transGroup);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderRef, fulfillmentId, productId, prodVendorId, initProdVendorId, status, fulfillmentStatus, proQuantity, prodSaleRate, initProdSaleRate, loadingCharges, transportCharges, initTransportCharges, hsnCode, rateOfCgst, rateOfSgst, rateOfIgst, taxInclusive, productCode, productName, vendorCode, vendorName, pinCode, gstNo, panNo, description, deliveryBy, imagePath, optImagePath1, optImagePath2, optImagePath3, parentCategoryName, subCategoryName, brandName, qualityName, weightName, materialName, colorName, gradeName, typeName, specification, size, crusherName, unitId, unitCode, unitName, transGroup);
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", orderRef=" + orderRef +
                ", fulfillmentId=" + fulfillmentId +
                ", productId=" + productId +
                ", prodVendorId=" + prodVendorId +
                ", initProdVendorId=" + initProdVendorId +
                ", status='" + status + '\'' +
                ", fulfillmentStatus='" + fulfillmentStatus + '\'' +
                ", proQuantity=" + proQuantity +
                ", prodSaleRate=" + prodSaleRate +
                ", initProdSaleRate=" + initProdSaleRate +
                ", loadingCharges=" + loadingCharges +
                ", transportCharges=" + transportCharges +
                ", initTransportCharges=" + initTransportCharges +
                ", hsnCode='" + hsnCode + '\'' +
                ", rateOfCgst=" + rateOfCgst +
                ", rateOfSgst=" + rateOfSgst +
                ", rateOfIgst=" + rateOfIgst +
                ", taxInclusive='" + taxInclusive + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", gstNo='" + gstNo + '\'' +
                ", panNo='" + panNo + '\'' +
                ", description='" + description + '\'' +
                ", deliveryBy=" + deliveryBy +
                ", imagePath='" + imagePath + '\'' +
                ", optImagePath1='" + optImagePath1 + '\'' +
                ", optImagePath2='" + optImagePath2 + '\'' +
                ", optImagePath3='" + optImagePath3 + '\'' +
                ", parentCategoryName='" + parentCategoryName + '\'' +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", qualityName='" + qualityName + '\'' +
                ", weightName='" + weightName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", colorName='" + colorName + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", specification='" + specification + '\'' +
                ", size='" + size + '\'' +
                ", crusherName='" + crusherName + '\'' +
                ", unitId=" + unitId +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", transGroup='" + transGroup + '\'' +
                '}';
    }
}
