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
@Table(name = "TRANSPORT")
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
public class Transport {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANS_GROUP")
    private String transGroup;

    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "DISTANCE")
    private Integer distance;

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

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
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
        Transport transport = (Transport) o;
        return Objects.equals(id, transport.id) &&
                Objects.equals(transGroup, transport.transGroup) &&
                Objects.equals(unitId, transport.unitId) &&
                Objects.equals(quantity, transport.quantity) &&
                Objects.equals(distance, transport.distance) &&
                Objects.equals(charges, transport.charges) &&
                Objects.equals(createdBy, transport.createdBy) &&
                Objects.equals(createdDate, transport.createdDate) &&
                Objects.equals(updatedBy, transport.updatedBy) &&
                Objects.equals(updatedDate, transport.updatedDate) &&
                Objects.equals(active, transport.active) &&
                Objects.equals(deleted, transport.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, transGroup, unitId, quantity, distance, charges, createdBy, createdDate, updatedBy, updatedDate, active, deleted);
    }

    @Override
    public String toString() {
        return "Transport{"
                + "id="
                + id
                + ", transGroup="
                + transGroup
                + ", unitId="
                + unitId
                + ", distance="
                + distance
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
