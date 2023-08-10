/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

import java.util.List;

/**
 * Created by nizamabdul
 */
public class ProductFeatureResponse {
    private List<BrandResponse> brands;
    private List<QualityResponse> qualities;
    private List<GradeResponse> grades;
    private List<TypeResponse> types;
    private List<CrusherResponse> crushers;
    private List<WeightResponse> weights;
    private List<MaterialResponse> materials;
    private List<ColorResponse> colors;
    private Double MinPrice;
    private Double MaxPrice;

    public ProductFeatureResponse(
            List<BrandResponse> brands,
            List<QualityResponse> qualities,
            List<GradeResponse> grades,
            List<TypeResponse> types,
            List<CrusherResponse> crushers,
            List<WeightResponse> weights,
            List<MaterialResponse> materials,
            List<ColorResponse> colors,
            Double MinPrice,
            Double MaxPrice) {
        this.brands = brands;
        this.qualities = qualities;
        this.grades = grades;
        this.types = types;
        this.crushers = crushers;
        this.weights = weights;
        this.materials = materials;
        this.colors = colors;
        this.MinPrice = MinPrice;
        this.MaxPrice = MaxPrice;
    }

    public List<BrandResponse> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandResponse> brands) {
        this.brands = brands;
    }

    public List<QualityResponse> getQualities() {
        return qualities;
    }

    public void setQualities(List<QualityResponse> qualities) {
        this.qualities = qualities;
    }

    public List<GradeResponse> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeResponse> grades) {
        this.grades = grades;
    }

    public List<TypeResponse> getTypes() {
        return types;
    }

    public void setTypes(List<TypeResponse> types) {
        this.types = types;
    }

    public List<CrusherResponse> getCrushers() {
        return crushers;
    }

    public void setCrushers(List<CrusherResponse> crushers) {
        this.crushers = crushers;
    }

    public List<WeightResponse> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightResponse> weights) {
        this.weights = weights;
    }

    public List<MaterialResponse> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialResponse> materials) {
        this.materials = materials;
    }

    public List<ColorResponse> getColors() {
        return colors;
    }

    public void setColors(List<ColorResponse> colors) {
        this.colors = colors;
    }

    public Double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(Double minPrice) {
        MinPrice = minPrice;
    }

    public Double getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        MaxPrice = maxPrice;
    }
}
