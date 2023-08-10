package com.makinus.unitedsupplies.api.controller.response;

import java.util.Objects;

/** Created by kingson */
public class WeightResponse {

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
        WeightResponse that = (WeightResponse) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                categoryId.equals(that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryId);
    }
}
