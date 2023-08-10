/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

import com.makinus.inventory.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.entity.Base;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Bad_sha
 */
public class CartProductResponse extends Base {

    @ApiModelProperty(notes = "Cart ID", example = "12", position = 1)
    private String id;

    @ApiModelProperty(notes = "Product ID", example = "22", position = 2)
    private String productId;

    @ApiModelProperty(notes = "Product Vendor ID", example = "12", position = 3)
    private String prodVendorId;

    @ApiModelProperty(notes = "Product Code", example = "132", position = 4)
    private String productCode;

    @ApiModelProperty(notes = "Product Name", example = "Sand", position = 5)
    private String productName;

    @ApiModelProperty(notes = "Unit Id", example = "1", position = 6)
    private String unitId;

    @ApiModelProperty(notes = "Unit Code", example = "Sq.Ft", position = 6)
    private String unitCode;

    @ApiModelProperty(notes = "Unit Name", example = "Sq.Ft", position = 7)
    private String unitName;

    @ApiModelProperty(notes = "Product Sale Price", example = "78788", position = 8)
    private String saleRate;

    @ApiModelProperty(notes = "Cart Quantity", example = "1", position = 9)
    private String quantity;

    @ApiModelProperty(notes = "Delivery In Days", example = "2", position = 10)
    private String deliveryIn;

    @ApiModelProperty(notes = "Transport Charges", example = "7800", position = 11)
    private String transCharge;

    @ApiModelProperty(notes = "Loading Charges", example = "1000", position = 12)
    private String loadingCharge;

    @ApiModelProperty(notes = "Minimum Order Quantity", example = "5", position = 13)
    private String minOrderQty;

    @ApiModelProperty(notes = "Product Price", example = "5000", position = 14)
    private String productPrice;

    @ApiModelProperty(notes = "Maximum Order Quantity", example = "500", position = 15)
    private String maxOrderQuantity;

    @ApiModelProperty(notes = "Tax Inclusive", example = "T", position = 16)
    private String taxInclusive;

    @ApiModelProperty(notes = "Transport group", example = "MSAND", position = 17)
    private String transGroup;

    @ApiModelProperty(notes = "List of Product Vendors", example = "[{vendorId: '1', vendorCode: '021', vendorName: 'Sathikul'},{vendorId: '2', vendorCode: '213', vendorName: 'Abu'}]", position = 18)
    private List<ProductVendorDetail> productVendorDetails;

    @ApiModelProperty(notes = "Product Image Paths", example = "[https://makinus.com/img]", position = 19)
    private List<String> imagePath;

    @ApiModelProperty(notes = "Show More Text in List of Quantity", example = "more", position = 20)
    private String showMoreText = ApiUtils.SHOW_MORE_TEXT;

    @ApiModelProperty(notes = "List of Quantity for Order", example = "[1,2,3,4,5]", position = 21)
    private List<String> quantityList;

    public CartProductResponse() {
    }

    public CartProductResponse(String status) {
        super(status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProdVendorId() {
        return prodVendorId;
    }

    public void setProdVendorId(String prodVendorId) {
        this.prodVendorId = prodVendorId;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
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

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryIn() {
        return deliveryIn;
    }

    public void setDeliveryIn(String deliveryIn) {
        this.deliveryIn = deliveryIn;
    }

    public String getTransCharge() {
        return transCharge;
    }

    public void setTransCharge(String transCharge) {
        this.transCharge = transCharge;
    }

    public String getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(String loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public String getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getMaxOrderQuantity() {
        return maxOrderQuantity;
    }

    public void setMaxOrderQuantity(String maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
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

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
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

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

}
