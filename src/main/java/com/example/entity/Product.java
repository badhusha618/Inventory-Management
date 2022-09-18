/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"productImages"})
@ToString(exclude = {"productImages"})
@Builder(toBuilder = true)
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE_PATH")
    private String primaryImage;
    @Column(name = "CATEGORY")
    private Long category;
    @Column(name = "BRAND")
    private Long brand;
    @Column(name = "UNIT")
    private String unit;
    @Column(name = "MRP_RATE")
    private BigDecimal mrpRate;
    @Column(name = "SALE_RATE")
    private BigDecimal saleRate;
    @Column(name = "IN_STOCK")
    private String inStock;
    @Column(name = "TAX_INCLUDED")
    private String taxIncluded;
    @Column(name = "HSN_CODE")
    private String hsnCode;
    @Type(type = "json")
    @Column(name = "VENDOR_NAME", columnDefinition = "json")
    private String vendorName;
    @Column(name = "GST_RATE")
    private BigDecimal gstRate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "ACTIVE")
    private String active;
    @Column(name = "DELETED")
    private String deleted;

    @OneToMany(mappedBy = "product")
    @Where(clause = "DELETED = 'F'")
    private Set<ProductImage> productImages = new HashSet<>();

    @Transient
    private byte[] fileByte;

    @Transient
    private String fileName;

    @Transient
    private String createdDateAsFolderName;

    @Transient
    private BigDecimal rating;
}








