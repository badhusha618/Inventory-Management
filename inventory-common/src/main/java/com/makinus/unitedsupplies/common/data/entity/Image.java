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

import javax.persistence.Transient;

/** Created by abuabdul */
public class Image {

  @Transient private byte[] image;
  @Transient private String originalFileName;
  @Transient private String createdDateAsFolderName;

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getCreatedDateAsFolderName() {
    return createdDateAsFolderName;
  }

  public void setCreatedDateAsFolderName(String createdDateAsFolderName) {
    this.createdDateAsFolderName = createdDateAsFolderName;
  }
}
