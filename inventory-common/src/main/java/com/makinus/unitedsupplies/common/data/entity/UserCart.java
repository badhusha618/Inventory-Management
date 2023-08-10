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
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sabqiue
 */
@Entity
@Table(name = "USER_CART")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class UserCart {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PROD_VENDOR_ID")
    private Long prodVendorId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "TRANS_CHARGES")
    private BigDecimal transCharges;

    @Column(name = "LOADING_CHARGES")
    private BigDecimal loadingCharges;

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

    @ApiModelProperty(hidden = true)
    @Column(name = "DELETED")
    private String deleted;

    @Transient
    private Long vendorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getTransCharges() {
        return transCharges;
    }

    public void setTransCharges(BigDecimal transCharges) {
        this.transCharges = transCharges;
    }

    public BigDecimal getLoadingCharges() {
        return loadingCharges;
    }

    public void setLoadingCharges(BigDecimal loadingCharges) {
        this.loadingCharges = loadingCharges;
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

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCart userCart = (UserCart) o;
        return Objects.equals(id, userCart.id) &&
                Objects.equals(productId, userCart.productId) &&
                Objects.equals(prodVendorId, userCart.prodVendorId) &&
                Objects.equals(userId, userCart.userId) &&
                Objects.equals(quantity, userCart.quantity) &&
                Objects.equals(unitId, userCart.unitId) &&
                Objects.equals(transCharges, userCart.transCharges) &&
                Objects.equals(loadingCharges, userCart.loadingCharges) &&
                Objects.equals(createdBy, userCart.createdBy) &&
                Objects.equals(createdDate, userCart.createdDate) &&
                Objects.equals(updatedBy, userCart.updatedBy) &&
                Objects.equals(updatedDate, userCart.updatedDate) &&
                Objects.equals(deleted, userCart.deleted) &&
                Objects.equals(vendorId, userCart.vendorId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, productId, prodVendorId, userId, quantity, unitId, transCharges, loadingCharges, createdBy, createdDate, updatedBy, updatedDate, deleted, vendorId);
    }

    @Override
    public String toString() {
        return "UserCart{" +
                "id=" + id +
                ", productId=" + productId +
                ", prodVendorId=" + prodVendorId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", unitId=" + unitId +
                ", transCharges=" + transCharges +
                ", loadingCharges=" + loadingCharges +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deleted='" + deleted + '\'' +
                ", vendorId=" + vendorId +
                '}';
    }
}
