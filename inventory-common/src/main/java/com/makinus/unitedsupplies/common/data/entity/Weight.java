package com.makinus.unitedsupplies.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

/**
 * @author kingson
 */

@Entity
@Table(name = "WEIGHT")
@JsonIgnoreProperties({"createdBy", "createdDate", "updatedBy", "updatedDate", "deleted"})
public class Weight {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WEIGHT")
    private String weight;

    @Column(name = "CATEGORY_ID")
    private Long category;

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
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
}