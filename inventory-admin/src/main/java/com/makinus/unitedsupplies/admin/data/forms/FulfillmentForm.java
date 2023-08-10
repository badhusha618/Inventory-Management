package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;
import java.util.List;

/** Created by abuabdul */
public class FulfillmentForm implements Serializable {

  private String id;
  private String orderRef;
  private String prodVendorId;
  private List<String> prodOrderIds;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderRef() {
    return orderRef;
  }

  public void setOrderRef(String orderRef) {
    this.orderRef = orderRef;
  }

  public List<String> getProdOrderIds() {
    return prodOrderIds;
  }

  public void setProdOrderIds(List<String> prodOrderIds) {
    this.prodOrderIds = prodOrderIds;
  }

  public String getProdVendorId() {
    return prodVendorId;
  }

  public void setProdVendorId(String prodVendorId) {
    this.prodVendorId = prodVendorId;
  }
}
