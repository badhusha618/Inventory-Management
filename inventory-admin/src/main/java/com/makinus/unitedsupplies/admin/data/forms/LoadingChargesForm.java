package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Created by abuabdul */
public class LoadingChargesForm implements Serializable {

  private String id;
  private String productId;
  private String quantity;
  private String charges;
  private List<LoadProductForm> loadProductForm = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getCharges() {
    return charges;
  }

  public void setCharges(String charges) {
    this.charges = charges;
  }

  public List<LoadProductForm> getLoadProductForm() { return loadProductForm; }

  public void setLoadProductForm(List<LoadProductForm> loadProductForm) { this.loadProductForm = loadProductForm; }
}
