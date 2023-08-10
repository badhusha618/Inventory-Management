package com.makinus.unitedsupplies.admin.data.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabique
 */
public class TransportForm implements Serializable {

    private String id;
    private String transGroup;
    private String unitId;
    private String quantity;
    private String distance;
    private String charges;

    private List<TranProductForm> tranProductForms = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public List<TranProductForm> getTranProductForms() {
        return tranProductForms;
    }

    public void setTranProductForms(List<TranProductForm> tranProductForms) {
        this.tranProductForms = tranProductForms;
    }
}
