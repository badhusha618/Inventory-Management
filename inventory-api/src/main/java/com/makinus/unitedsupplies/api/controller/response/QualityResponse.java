/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import java.util.Objects;

/** Created by sabique */
public class QualityResponse {

  private String id;
  private String name;
  private String categoryId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QualityResponse that = (QualityResponse) o;
    return id.equals(that.id) &&
            name.equals(that.name) &&
            categoryId.equals(that.categoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, categoryId);
  }
}
