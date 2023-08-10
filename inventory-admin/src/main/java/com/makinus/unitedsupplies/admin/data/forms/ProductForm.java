package com.makinus.Inventory.admin.data.forms;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Bad_sha
 */
public class ProductForm implements Serializable {

    private String productCode;
    private String productID;
    private String productName;
    private String productDescription;
    private String deliveryBy;
    private MultipartFile productImage;
    private MultipartFile editProductImage;
    private MultipartFile productImageOpt1;
    private MultipartFile editProductImageOpt1;
    private MultipartFile productImageOpt2;
    private MultipartFile editProductImageOpt2;
    private MultipartFile productImageOpt3;
    private MultipartFile editProductImageOpt3;
    private String parentCategory;
    private String parentSubCategory;
    private String brand;
    private String quality;
    private String grade;
    private String type;
    private String size;
    private String crusher;
    private Long unit;
    private String weight;
    private String material;
    private String color;
    private String specification;
    private String hsnCode;
    private String rateOfCgst;
    private String rateOfSgst;
    private String rateOfIgst;
    private String minOrderQty;
    private String maxOrderQty;
    private String transGroup;
    private boolean inStock;
    private boolean taxInclusive;
    private boolean activeProduct;
    private List<ProductSourceForm> productSourceForms;
    private List<ProductVendorForm> productVendorForms;
    private Date updatedDate;
    private String updatedBy;
    private Date createdDate;
    private String createdBy;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public MultipartFile getEditProductImage() {
        return editProductImage;
    }

    public void setEditProductImage(MultipartFile editProductImage) {
        this.editProductImage = editProductImage;
    }

    public MultipartFile getProductImageOpt1() {
        return productImageOpt1;
    }

    public void setProductImageOpt1(MultipartFile productImageOpt1) {
        this.productImageOpt1 = productImageOpt1;
    }

    public MultipartFile getEditProductImageOpt1() {
        return editProductImageOpt1;
    }

    public void setEditProductImageOpt1(MultipartFile editProductImageOpt1) {
        this.editProductImageOpt1 = editProductImageOpt1;
    }

    public MultipartFile getProductImageOpt2() {
        return productImageOpt2;
    }

    public void setProductImageOpt2(MultipartFile productImageOpt2) {
        this.productImageOpt2 = productImageOpt2;
    }

    public MultipartFile getEditProductImageOpt2() {
        return editProductImageOpt2;
    }

    public void setEditProductImageOpt2(MultipartFile editProductImageOpt2) {
        this.editProductImageOpt2 = editProductImageOpt2;
    }

    public MultipartFile getProductImageOpt3() {
        return productImageOpt3;
    }

    public void setProductImageOpt3(MultipartFile productImageOpt3) {
        this.productImageOpt3 = productImageOpt3;
    }

    public MultipartFile getEditProductImageOpt3() {
        return editProductImageOpt3;
    }

    public void setEditProductImageOpt3(MultipartFile editProductImageOpt3) {
        this.editProductImageOpt3 = editProductImageOpt3;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentSubCategory() {
        return parentSubCategory;
    }

    public void setParentSubCategory(String parentSubCategory) {
        this.parentSubCategory = parentSubCategory;
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

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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

    public String getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(String maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }


    public boolean isActiveProduct() {
        return activeProduct;
    }

    public void setActiveProduct(boolean activeProduct) {
        this.activeProduct = activeProduct;
    }

    public boolean isTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(boolean taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public List<ProductSourceForm> getProductSourceForms() {
        return productSourceForms;
    }

    public void setProductSourceForms(List<ProductSourceForm> productSourceForms) {
        this.productSourceForms = productSourceForms;
    }

    public List<ProductVendorForm> getProductVendorForms() {
        return productVendorForms;
    }

    public void setProductVendorForms(List<ProductVendorForm> productVendorForms) {
        this.productVendorForms = productVendorForms;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
