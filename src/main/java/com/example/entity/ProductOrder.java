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
import java.math.BigDecimal;

/** Created by BAD_SHA */
@Entity
@Table(name = "PRODUCT_ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {})
@ToString(exclude = {})
@Builder(toBuilder = true)
public class ProductOrder {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "ORDER_ID")
  private Orders order;
  @Column(name = "PROD_ID")
  private Long productId;
  @Column(name = "PRODUCT_NAME")
  private String productName;
  @Column(name = "PRODUCT_IMAGE")
  private String productImage;
  @Column(name = "PROD_QTY")
  private Integer proQuantity;
  @Column(name = "PROD_MEASURE")
  private String prodMeasure;
  @Column(name = "PROD_SALE_RATE")
  private BigDecimal prodSaleRate;
  @Column(name = "ORDER_STATUS")
  private String orderStatus;
  @Column(name = "VENDOR_NAME")
  private String vendorName;
}
