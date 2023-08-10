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
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by abuabdul
 */
@Entity
@Table(name = "PROD_VENDOR")
@JsonIgnoreProperties({
        "createdBy",
        "createdDate",
        "updatedBy",
        "updatedDate",
        "deleted"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVendor {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROD_ID")
    private Long productId;

    @Column(name = "VENDOR_ID")
    private Long vendorId;

    @Column(name = "MRP_RATE")
    private BigDecimal mrpRate;

    @Column(name = "SALE_RATE")
    private BigDecimal saleRate;

    @Column(name = "ACTUAL_RATE")
    private BigDecimal actualRate;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "DISABLED")
    private String disableVendor;

    @Column(name = "DEFAULT_VENDOR")
    private String defaultVendor;

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

    public ProductVendor() {
    }

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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDisableVendor() {
        return disableVendor;
    }

    public void setDisableVendor(String disableVendor) {
        this.disableVendor = disableVendor;
    }

    public String getDefaultVendor() {
        return defaultVendor;
    }

    public void setDefaultVendor(String defaultVendor) {
        this.defaultVendor = defaultVendor;
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
        ProductVendor that = (ProductVendor) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(vendorId, that.vendorId) &&
                Objects.equals(mrpRate, that.mrpRate) &&
                Objects.equals(saleRate, that.saleRate) &&
                Objects.equals(actualRate, that.actualRate) &&
                Objects.equals(pinCode, that.pinCode) &&
                Objects.equals(disableVendor, that.disableVendor) &&
                Objects.equals(defaultVendor, that.defaultVendor) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getProductId(), getVendorId(), getMrpRate(), getSaleRate(), getActualRate(), getPinCode(), getDisableVendor(), getDefaultVendor(), getCreatedBy(), getCreatedDate(), getUpdatedBy(), getUpdatedDate(), getDeleted());
    }

    @Override
    public String toString() {
        return "ProductVendor{" +
                "id=" + id +
                ", productId=" + productId +
                ", vendorId=" + vendorId +
                ", mrpRate=" + mrpRate +
                ", saleRate=" + saleRate +
                ", actualRate=" + actualRate +
                ", pinCode='" + pinCode + '\'' +
                ", disableVendor='" + disableVendor + '\'' +
                ", defaultVendor='" + defaultVendor + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deleted='" + deleted + '\'' +
                '}';
    }
}

