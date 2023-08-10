package com.makinus.Inventory.admin.data.forms;

public class TranProductForm {

    private String id;
    private String productId;
    private String transportId;
    private String quantity;
    private String distance;
    private String charges;
    private boolean deleted;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTransportId() { return transportId; }

    public void setTransportId(String transportId) { this.transportId = transportId; }

    public String getQuantity() { return quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getDistance() { return distance; }

    public void setDistance(String distance) { this.distance = distance; }

    public String getCharges() { return charges; }

    public void setCharges(String charges) { this.charges = charges; }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
