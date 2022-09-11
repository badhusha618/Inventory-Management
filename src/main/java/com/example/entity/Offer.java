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
@Table(name = "OFFER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"fileByte", "fileName", "createdDateAsFolderName"})
@ToString(exclude = {"fileByte", "fileName", "createdDateAsFolderName"})
@Builder(toBuilder = true)
public class Offer {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OFFER_NAME")
    private String offerName;

    @Column(name = "DESCRIPTION")
    private String description;

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

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "DELETED")
    private String deleted;

    @Transient
    private byte[] fileByte;
    @Transient
    private String fileName;
    @Transient
    private String createdDateAsFolderName;

}
