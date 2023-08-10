/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.forms;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

/** Created by Bad_sha */
public class PromotionForm implements Serializable {

  private String promotionID;
  private String promotionName;
  private String description;
  private MultipartFile promotionImage;
  private MultipartFile editPromotionImage;
  private boolean activePromotion;
  private String createdOn;

  public String getPromotionID() {
    return promotionID;
  }

  public void setPromotionID(String promotionID) {
    this.promotionID = promotionID;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MultipartFile getPromotionImage() {
    return promotionImage;
  }

  public void setPromotionImage(MultipartFile promotionImage) {
    this.promotionImage = promotionImage;
  }

  public MultipartFile getEditPromotionImage() {
    return editPromotionImage;
  }

  public void setEditPromotionImage(MultipartFile editPromotionImage) {
    this.editPromotionImage = editPromotionImage;
  }

  public boolean isActivePromotion() {
    return activePromotion;
  }

  public void setActivePromotion(boolean activePromotion) {
    this.activePromotion = activePromotion;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }
}
