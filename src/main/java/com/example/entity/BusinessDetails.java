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

import javax.persistence.*;
import java.util.Date;

/**
 * Created by BAD_SHA
 */
@Entity
@Table(name = "BUSINESS_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class BusinessDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NO1")
    private String phoneNo1;

    @Column(name = "PHONE_NO2")
    private String phoneNo2;

    @Column(name = "GST_NO")
    private String gstNo;

    @Column(name = "PAN_NO")
    private String panNo;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "DELETED")
    private String deleted;

    @Column(name = "INCLUDE_TAX")
    private String includeTax;

    @Transient
    private byte[] fileByte;

    @Transient
    private String fileName;

    @Transient
    private String createdDateAsFolderName;

}
