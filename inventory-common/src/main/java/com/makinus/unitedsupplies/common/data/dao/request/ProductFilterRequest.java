/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.request;

/** @author nizamabdul */
@Deprecated
public class ProductFilterRequest {

  private String categoryId;
  private Long[] typeId;
  private Long[] gradeId;
  private Long[] brandId;
  private Long[] sizeId;
  private Long[] qualityId;
  private Long[] crusherId;
  private String inStock;
  private String minPrice;
  private String maxPrice;
  private String productName;

  /** @return the categoryId */
  public String getCategoryId() {
    return categoryId;
  }

  /** @param categoryId the categoryId to set */
  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  /** @return the typeId */
  public Long[] getTypeId() {
    return typeId;
  }

  /** @param typeId the typeId to set */
  public void setTypeId(Long[] typeId) {
    this.typeId = typeId;
  }

  /** @return the gradeId */
  public Long[] getGradeId() {
    return gradeId;
  }

  /** @param gradeId the gradeId to set */
  public void setGradeId(Long[] gradeId) {
    this.gradeId = gradeId;
  }

  /** @return the brandId */
  public Long[] getBrandId() {
    return brandId;
  }

  /** @param brandId the brandId to set */
  public void setBrandId(Long[] brandId) {
    this.brandId = brandId;
  }

  /** @return the sizeId */
  public Long[] getSizeId() {
    return sizeId;
  }

  /** @param sizeId the sizeId to set */
  public void setSizeId(Long[] sizeId) {
    this.sizeId = sizeId;
  }

  /** @return the qualityId */
  public Long[] getQualityId() {
    return qualityId;
  }

  /** @param qualityId the qualityId to set */
  public void setQualityId(Long[] qualityId) {
    this.qualityId = qualityId;
  }

  /** @return the inStock */
  public String getInStock() {
    return inStock;
  }

  /** @param inStock the inStock to set */
  public void setInStock(String inStock) {
    this.inStock = inStock;
  }

  /** @return the minPrice */
  public String getMinPrice() {
    return minPrice;
  }

  /** @param minPrice the minPrice to set */
  public void setMinPrice(String minPrice) {
    this.minPrice = minPrice;
  }

  /** @return the maxPrice */
  public String getMaxPrice() {
    return maxPrice;
  }

  /** @param maxPrice the maxPrice to set */
  public void setMaxPrice(String maxPrice) {
    this.maxPrice = maxPrice;
  }

  /** @return the crusherId */
  public Long[] getCrusherId() {
    return crusherId;
  }

  /** @param crusherId the crusherId to set */
  public void setCrusherId(Long[] crusherId) {
    this.crusherId = crusherId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
}
