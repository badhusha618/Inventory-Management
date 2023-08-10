/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.request;

import com.makinus.inventory.api.controller.response.ProductVendorDetail;
import com.makinus.inventory.api.utils.ApiUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Bad_sha
 */
public class ProductCharges {

    @ApiModelProperty(notes = "Product Id", example = "1", position = 1)
    private Long productId;

    @ApiModelProperty(notes = "Vendor Id", example = "22", position = 2)
    private Long vendorId;

    @ApiModelProperty(notes = "Vendor Id", example = "22", position = 3)
    private String vendorCode;

    @ApiModelProperty(notes = "Vendor Id", example = "22", position = 4)
    private String vendorName;

    @ApiModelProperty(notes = "Vendor Id", example = "22", position = 5)
    private String pinCode;

    @ApiModelProperty(notes = "Quantity", example = "5", position = 6)
    private Integer quantity;

    @ApiModelProperty(notes = "Unit ID", example = "1", position = 7)
    private String unitId;

    @ApiModelProperty(notes = "Unit Name", example = "Square Feet", position = 7)
    private String unitName;

    @ApiModelProperty(notes = "Unit Code", example = "Sq.Ft", position = 8)
    private String unitCode;

    @ApiModelProperty(notes = "Sale Rate", example = "400", position = 9)
    private String saleRate;

    @ApiModelProperty(notes = "Sub Total", example = "200", position = 10)
    private String subTotal;

    @ApiModelProperty(notes = "Product Transport Charge", example = "500", position = 11)
    private String transportCharge;

    @ApiModelProperty(notes = "Product Loading Charge, Optional", example = "500", position = 12)
    private String loadingCharge;

    @ApiModelProperty(notes = "Minimum Order Quantity", example = "5", position = 13)
    private String minOrderQty;

    @ApiModelProperty(notes = "Maximum Order Quantity", example = "600", position = 14)
    private String maxOrderQty;

    @ApiModelProperty(notes = "Tax Inclusive", example = "T", position = 15)
    private String taxInclusive;

    @ApiModelProperty(notes = "Tax Inclusive", example = "T", position = 16)
    private String transGroup;

    @ApiModelProperty(notes = "Product Vendor Detail", example = "[{id: '1',productId: '1',vendorId: '1',vendorCode: '341',vendorName: 'Sathik',mrpRate: '400',saleRate: '300',pinCode: '627005'}]", position = 17)
    private List<ProductVendorDetail> productVendorDetail;

    @ApiModelProperty(notes = "Show More Text in List of Quantity", example = "more", position = 18)
    private String showMoreText = ApiUtils.SHOW_MORE_TEXT;

    @ApiModelProperty(notes = "List of Quantity for Order", example = "[1,2,3,4,5]", position = 19)
    private List<String> quantityList;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTransportCharge() {
        return transportCharge;
    }

    public void setTransportCharge(String transportCharge) {
        this.transportCharge = transportCharge;
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

    public String getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(String maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public String getTaxInclusive() {
        return taxInclusive;
    }

    public void setTaxInclusive(String taxInclusive) {
        this.taxInclusive = taxInclusive;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public List<ProductVendorDetail> getProductVendorDetail() {
        return productVendorDetail;
    }

    public void setProductVendorDetail(List<ProductVendorDetail> productVendorDetail) {
        this.productVendorDetail = productVendorDetail;
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
}
