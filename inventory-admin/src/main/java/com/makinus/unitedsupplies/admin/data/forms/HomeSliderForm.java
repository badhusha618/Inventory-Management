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
public class HomeSliderForm implements Serializable {

  private String sliderID;
  private String sliderName;
  private String description;
  private MultipartFile sliderImage;
  private boolean activeSlider;
  private String createdOn;

  public String getSliderID() {
    return sliderID;
  }

  public void setSliderID(String sliderID) {
    this.sliderID = sliderID;
  }

  public String getSliderName() {
    return sliderName;
  }

  public void setSliderName(String sliderName) {
    this.sliderName = sliderName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MultipartFile getSliderImage() {
    return sliderImage;
  }

  public void setSliderImage(MultipartFile sliderImage) {
    this.sliderImage = sliderImage;
  }

  public boolean isActiveSlider() {
    return activeSlider;
  }

  public void setActiveSlider(boolean activeSlider) {
    this.activeSlider = activeSlider;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }
}
