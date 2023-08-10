/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.filter.product;

import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.makinus.unitedsupplies.common.utils.AppUtils.utcDateForDDMMYYYY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author nizamabdul
 */
public class ProductFilterCriteriaBuilder {

    private CriteriaBuilder builder;
    private Predicate predicate;
    private Root<Product> productRoot;
    private boolean paramSet = Boolean.FALSE;

    private ProductFilterCriteriaBuilder(CriteriaBuilder builder, CriteriaQuery<Product> query) {
        this.builder = builder;
        this.predicate = builder.conjunction();
        this.productRoot = query.from(Product.class);
    }

    public static ProductFilterCriteriaBuilder aProductFilterCriteria(CriteriaBuilder builder, CriteriaQuery<Product> query) {
        return new ProductFilterCriteriaBuilder(builder, query);
    }

    public ProductFilterCriteriaBuilder productId(String productId) {
        if (isNotEmpty(productId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("id"), Long.valueOf(productId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder products(List<Long> productId) {
        if (productId != null && !productId.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("id").in(productId));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder productName(String productName) {
        if (isNotEmpty(productName)) {
            predicate = builder.and(predicate, builder.like(productRoot.get("productName"), "%" + productName + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductFilterCriteriaBuilder brand(String brandId) {
        if (isNotEmpty(brandId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("brand"), Long.valueOf(brandId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder brands(List<Long> brands) {
        if (brands != null && !brands.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("brand").in(brands));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder quality(String qualityId) {
        if (isNotEmpty(qualityId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("quality"), Long.valueOf(qualityId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder qualities(List<Long> qualities) {
        if (qualities != null && !qualities.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("quality").in(qualities));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder grade(String gradeId) {
        if (isNotEmpty(gradeId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("grade"), Long.valueOf(gradeId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder grades(List<Long> grades) {
        if (grades != null && !grades.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("grade").in(grades));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder type(String typeId) {
        if (isNotEmpty(typeId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("type"), Long.valueOf(typeId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder types(List<Long> types) {
        if (types != null && !types.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("type").in(types));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder material(String materialId) {
        if (isNotEmpty(materialId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("material"), Long.valueOf(materialId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder materials(List<Long> materials) {
        if (materials != null && !materials.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("material").in(materials));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder size(String colorId) {
        if (isNotEmpty(colorId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("color"), Long.valueOf(colorId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder colors(List<Long> colors) {
        if (colors != null && !colors.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("color").in(colors));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder crusher(String crusherId) {
        if (isNotEmpty(crusherId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("crusher"), Long.valueOf(crusherId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder crushers(List<Long> crushers) {
        if (crushers != null && !crushers.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("crusher").in(crushers));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder unit(String unitId) {
        if (isNotEmpty(unitId)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("unit"), Long.valueOf(unitId)));
        }
        return this;
    }

    public ProductFilterCriteriaBuilder units(List<Long> units) {
        if (units != null && !units.isEmpty()) {
            predicate = builder.and(predicate, productRoot.get("unit").in(units));
        }
        return this;
    }

   /* public ProductFilterCriteriaBuilder priceRange(String minPrice, String maxPrice) {
        if (isNotEmpty(minPrice) && isNotEmpty(maxPrice)) {
            BigDecimal min = new BigDecimal(minPrice);
            BigDecimal max = new BigDecimal(maxPrice);
            predicate = builder.and(predicate, builder.between(productRoot.get("saleRate"), min, max));
        }
        return this;
    }*/

    public ProductFilterCriteriaBuilder inStock(String inStock) {
        if (isNotEmpty(inStock)) {
            String value = YNStatus.statusMatch(inStock).getStatus();
            predicate = builder.and(predicate, builder.equal(productRoot.get("inStock"), value));
        }
        return this;
    }

    public Predicate predicate() {
        predicate = builder.and(predicate, builder.equal(productRoot.get("deleted"), YNStatus.NO.getStatus()));
        return this.predicate;
    }

    public ProductFilterCriteriaBuilder productCode(String productCode) {
        if (isNotEmpty(productCode)) {
            predicate = builder.and(predicate, builder.like(productRoot.get("productCode"), "%" + productCode + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductFilterCriteriaBuilder category(String category) {
        if (isNotEmpty(category)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("parentCategory"), category));
            paramSet = Boolean.TRUE;
        }

        return this;
    }

    public ProductFilterCriteriaBuilder subCategory(String category) {
        if (isNotEmpty(category)) {
            predicate = builder.and(predicate, builder.equal(productRoot.get("subCategory"), category));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductFilterCriteriaBuilder dateRange(String fromDate, String toDate) throws ParseException {
        if (isNotEmpty(fromDate) && isNotEmpty(toDate)) {
            Date from = utcDateForDDMMYYYY(fromDate);
            Date to = utcDateForDDMMYYYY(toDate);
            predicate = builder.and(predicate, builder.between(productRoot.get("createdDate"), from, to));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public boolean isParamSet() {
        return this.paramSet;
    }
}
