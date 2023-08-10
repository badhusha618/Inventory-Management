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

import com.makinus.unitedsupplies.common.data.form.ProductFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterCriteriaBuilder.aProductFilterCriteria;

/**
 * @author abuabdul
 */
@Repository
public class ProductFilterDAOImpl implements ProductFilterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> searchProduct(ProductFilterRequest productFilterRequest, List<Long> productIds, String categoryId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        ProductFilterCriteriaBuilder productCriteria =
                aProductFilterCriteria(builder, query)
                        .category(categoryId)
                        .brands(productFilterRequest.getBrandId())
                        .qualities(productFilterRequest.getQualityId())
                        .crushers(productFilterRequest.getCrusherId())
                        .grades(productFilterRequest.getGradeId())
                        .types(productFilterRequest.getTypeId())
                        .materials(productFilterRequest.getMaterialId())
                        .colors(productFilterRequest.getColorId())
                        .units(productFilterRequest.getUnitId())
                        .products(productIds)
                        .inStock(productFilterRequest.getInStock());
        return entityManager.createQuery(query.where(productCriteria.predicate())).getResultList();
    }

    @Override
    public List<Product> searchProductsByName(String productName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        ProductFilterCriteriaBuilder productCriteria =
                aProductFilterCriteria(builder, query).productName(productName);
        return entityManager.createQuery(query.where(productCriteria.predicate())).getResultList();
    }

    @Override
    public List<Product> productListByIds(List<Long> productIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        ProductFilterCriteriaBuilder productCriteria =
                aProductFilterCriteria(builder, query).products(productIds);
        return entityManager.createQuery(query.where(productCriteria.predicate())).getResultList();
    }

    @Override
    public List<Product> filterProduct(ProductFilterForm productFilterForm) throws UnitedSuppliesException {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            ProductFilterCriteriaBuilder productFilterCriteriaBuilder = aProductFilterCriteria(builder, query)
                    .productCode(productFilterForm.getProductCode())
                    .productName(productFilterForm.getProductName())
                    .category(productFilterForm.getCategory())
                    .subCategory(productFilterForm.getSubCategory())
                    .dateRange(productFilterForm.getFromDate(), productFilterForm.getToDate());
            if (productFilterCriteriaBuilder.isParamSet()) {
                return entityManager.createQuery(query.where(productFilterCriteriaBuilder.predicate())).getResultList();
            }
        } catch (ParseException ex) {
            throw new UnitedSuppliesException(ex.getMessage());
        }
        return new ArrayList<>();
    }
}
