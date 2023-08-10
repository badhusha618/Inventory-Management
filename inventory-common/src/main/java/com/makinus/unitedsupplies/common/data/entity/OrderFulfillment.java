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

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/** Created by Bad_sha */
@Entity
@Table(name = "ORDER_FULFILLMENT")
public class OrderFulfillment {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "FULFILLMENT_REF")
  private String fulfillmentRef;

  @Column(name = "PRODUCT_INVOICE_NO")
  private String productInvoiceNo;

  @Column(name = "PRODUCT_INVOICE_DATE")
  private Date productInvoiceDate;

  @Column(name = "SELL_SERV_INVOICE_NO")
  private String sellServInvoiceNo;

  @Column(name = "SELL_SERV_INVOICE_DATE")
  private Date sellServInvoiceDate;

  @Column(name = "SELL_SERV_INVOICE_AMOUNT")
  private String sellServInvoiceAmount;

  @Column(name = "ORDER_REF")
  private Long orderRef;

  @Column(name = "PROD_VENDOR_ID")
  private Long prodVendorId;

  @Column(name = "VENDOR_CODE")
  private String vendorCode;

  @Column(name = "VENDOR_NAME")
  private String vendorName;

  @Column(name = "COMPANY_NAME")
  private String companyName;

  @Column(name = "ADDRESS")
  private String address;

  @Column (name = "CITY")
  private String city;

  @Column (name = "PIN_CODE")
  private String pinCode;

  @Column(name = "GST_NO")
  private String gstNo;

  @Column(name = "PAN_NO")
  private String panNo;

  @Column(name = "ORDER_SUBTOTAL")
  private BigDecimal subTotal;

  @Column(name = "TRANS_CHARGES")
  private BigDecimal transportCharges;

  @Column(name = "SERV_CHARGES")
  private BigDecimal serviceCharges;

  @Column(name = "LOAD_CHARGES")
  private BigDecimal loadingCharges;

  @Column(name = "ORDER_TOTAL")
  private BigDecimal orderTotal;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "CREATED_DATE")
  private Date createdDate;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @Column(name = "UPDATED_DATE")
  private Date updatedDate;

  @Transient
  private String orderTotalInWords;

  @Transient
  private String cityDisplay;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFulfillmentRef() {
    return fulfillmentRef;
  }

  public void setFulfillmentRef(String fulfillmentRef) {
    this.fulfillmentRef = fulfillmentRef;
  }

  public String getProductInvoiceNo() {
    return productInvoiceNo;
  }

  public void setProductInvoiceNo(String productInvoiceNo) {
    this.productInvoiceNo = productInvoiceNo;
  }

  public Date getProductInvoiceDate() {
    return productInvoiceDate;
  }

  public void setProductInvoiceDate(Date productInvoiceDate) {
    this.productInvoiceDate = productInvoiceDate;
  }

  public String getSellServInvoiceNo() {
    return sellServInvoiceNo;
  }

  public void setSellServInvoiceNo(String sellServInvoiceNo) {
    this.sellServInvoiceNo = sellServInvoiceNo;
  }

  public Date getSellServInvoiceDate() {
    return sellServInvoiceDate;
  }

  public void setSellServInvoiceDate(Date sellServInvoiceDate) {
    this.sellServInvoiceDate = sellServInvoiceDate;
  }

  public String getSellServInvoiceAmount() {
    return sellServInvoiceAmount;
  }

  public void setSellServInvoiceAmount(String sellServInvoiceAmount) {
    this.sellServInvoiceAmount = sellServInvoiceAmount;
  }

  public Long getOrderRef() {
    return orderRef;
  }

  public void setOrderRef(Long orderRef) {
    this.orderRef = orderRef;
  }

  public Long getProdVendorId() {
    return prodVendorId;
  }

  public void setProdVendorId(Long prodVendorId) {
    this.prodVendorId = prodVendorId;
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

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public String getGstNo() {
    return gstNo;
  }

  public void setGstNo(String gstNo) {
    this.gstNo = gstNo;
  }

  public String getPanNo() {
    return panNo;
  }

  public void setPanNo(String panNo) {
    this.panNo = panNo;
  }

  public BigDecimal getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  public BigDecimal getTransportCharges() {
    return transportCharges;
  }

  public void setTransportCharges(BigDecimal transportCharges) {
    this.transportCharges = transportCharges;
  }

  public BigDecimal getServiceCharges() {
    return serviceCharges;
  }

  public void setServiceCharges(BigDecimal serviceCharges) {
    this.serviceCharges = serviceCharges;
  }

  public BigDecimal getLoadingCharges() {
    return loadingCharges;
  }

  public void setLoadingCharges(BigDecimal loadingCharges) {
    this.loadingCharges = loadingCharges;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
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

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getOrderTotalInWords() {
    return orderTotalInWords;
  }

  public void setOrderTotalInWords(String orderTotalInWords) {
    this.orderTotalInWords = orderTotalInWords;
  }

  public String getCityDisplay() {
    return cityDisplay;
  }

  public void setCityDisplay(String cityDisplay) {
    this.cityDisplay = cityDisplay;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderFulfillment that = (OrderFulfillment) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(fulfillmentRef, that.fulfillmentRef) &&
            Objects.equals(productInvoiceNo, that.productInvoiceNo) &&
            Objects.equals(productInvoiceDate, that.productInvoiceDate) &&
            Objects.equals(sellServInvoiceNo, that.sellServInvoiceNo) &&
            Objects.equals(sellServInvoiceDate, that.sellServInvoiceDate) &&
            Objects.equals(sellServInvoiceAmount, that.sellServInvoiceAmount) &&
            Objects.equals(orderRef, that.orderRef) &&
            Objects.equals(prodVendorId, that.prodVendorId) &&
            Objects.equals(vendorCode, that.vendorCode) &&
            Objects.equals(vendorName, that.vendorName) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(city, that.city) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(gstNo, that.gstNo) &&
            Objects.equals(panNo, that.panNo) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(transportCharges, that.transportCharges) &&
            Objects.equals(serviceCharges, that.serviceCharges) &&
            Objects.equals(loadingCharges, that.loadingCharges) &&
            Objects.equals(orderTotal, that.orderTotal) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fulfillmentRef, productInvoiceNo, productInvoiceDate, sellServInvoiceNo, sellServInvoiceDate, sellServInvoiceAmount, orderRef, prodVendorId, vendorCode, vendorName, companyName, address, city, pinCode, gstNo, panNo, subTotal, transportCharges, serviceCharges, loadingCharges, orderTotal, createdBy, createdDate, updatedBy, updatedDate);
  }

  @Override
  public String toString() {
    return "OrderFulfillment{" +
            "id=" + id +
            ", fulfillmentRef='" + fulfillmentRef + '\'' +
            ", productInvoiceNo='" + productInvoiceNo + '\'' +
            ", productInvoiceDate=" + productInvoiceDate +
            ", sellServInvoiceNo='" + sellServInvoiceNo + '\'' +
            ", sellServInvoiceDate=" + sellServInvoiceDate +
            ", sellServInvoiceAmount='" + sellServInvoiceAmount + '\'' +
            ", orderRef=" + orderRef +
            ", prodVendorId=" + prodVendorId +
            ", vendorCode='" + vendorCode + '\'' +
            ", vendorName='" + vendorName + '\'' +
            ", companyName='" + companyName + '\'' +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", pinCode='" + pinCode + '\'' +
            ", gstNo='" + gstNo + '\'' +
            ", panNo='" + panNo + '\'' +
            ", subTotal=" + subTotal +
            ", transportCharges=" + transportCharges +
            ", serviceCharges=" + serviceCharges +
            ", loadingCharges=" + loadingCharges +
            ", orderTotal=" + orderTotal +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", updatedBy='" + updatedBy + '\'' +
            ", updatedDate=" + updatedDate +
            '}';
  }
}