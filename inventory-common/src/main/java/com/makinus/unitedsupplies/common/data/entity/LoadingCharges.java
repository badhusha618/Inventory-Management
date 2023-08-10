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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 * @author Bad_sha
 */
@Entity
@Table(name = "LOADING_CHARGES")
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
        "optImagePath1",
        "optImagePath2",
        "optImagePath3",
        "createdBy",
        "createdDate",
        "updatedBy",
        "updatedDate",
        "deleted"
})
public class LoadingCharges {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "CHARGES")
    private BigDecimal charges;

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
    @Column(name = "ACTIVE")
    private String active;

    @ApiModelProperty(hidden = true)
    @Column(name = "DELETED")
    private String deleted;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadingCharges loadingCharges = (LoadingCharges) o;
        return Objects.equals(id, loadingCharges.id)
                && Objects.equals(productId, loadingCharges.productId)
                && Objects.equals(quantity, loadingCharges.quantity)
                && Objects.equals(charges, loadingCharges.charges)
                && Objects.equals(createdBy, loadingCharges.createdBy)
                && Objects.equals(createdDate, loadingCharges.createdDate)
                && Objects.equals(updatedBy, loadingCharges.updatedBy)
                && Objects.equals(updatedDate, loadingCharges.updatedDate)
                && Objects.equals(active, loadingCharges.active)
                && Objects.equals(deleted, loadingCharges.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                id,
                productId,
                quantity,
                charges,
                createdBy,
                createdDate,
                updatedBy,
                updatedDate,
                active,
                deleted);
    }

    @Override
    public String toString() {
        return "LoadingCharges{"
                + "id="
                + id
                + ", productId="
                + productId
                + ", quantity="
                + quantity
                + ", charges="
                + charges
                + ", createdBy='"
                + createdBy
                + '\''
                + ", createdDate="
                + createdDate
                + ", updatedBy='"
                + updatedBy
                + '\''
                + ", updatedDate="
                + updatedDate
                + ", active='"
                + active
                + '\''
                + ", deleted='"
                + deleted
                + '\''
                + '}';
    }
}
