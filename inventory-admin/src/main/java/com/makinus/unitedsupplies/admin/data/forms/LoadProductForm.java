package com.makinus.unitedsupplies.admin.data.forms;

public class LoadProductForm {

    private String id;
    private String loadingChargesId;
    private String quantity;
    private String charges;
    private boolean deleted;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getLoadingChargesId() { return loadingChargesId; }

    public void setLoadingChargesId(String loadingChargesId) { this.loadingChargesId = loadingChargesId; }

    public String getQuantity() { return quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getCharges() { return charges; }

    public void setCharges(String charges) { this.charges = charges; }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
