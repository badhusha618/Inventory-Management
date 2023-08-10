/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.NO;
import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.YES;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

import com.makinus.unitedsupplies.admin.data.forms.ProductForm;
import com.makinus.unitedsupplies.admin.data.forms.ProductSourceForm;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.ProductSource;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.mapper.ListEntityWithExtraValueUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by abuabdul */
@Component
@Qualifier("ProductSourceMapper")
public class ProductSourceMapper
    implements EntityWithExtraValueMapper<List<ProductSourceForm>, Product, List<ProductSource>>,
        ListEntityWithExtraValueUpdateMapper<ProductForm, Product, ProductSource>,
        EntityRemapper<List<ProductSource>, List<ProductSourceForm>> {

  private final Logger LOG = LogManager.getLogger(ProductSourceMapper.class);

  @Autowired private ProductService productService;

  @Override
  public List<ProductSource> mapExtra(List<ProductSourceForm> productSourceForms, Product product)
      throws UnitedSuppliesException {
    LOG.info("Map ProductSource Forms to ProductSources Entity");
    List<ProductSource> productSourceList = new ArrayList<>();
    productSourceForms.forEach(
        ps -> {
          if (!ps.getPostelCode().isEmpty()) {
            ProductSource productSource = new ProductSource();
            productSource.setProductId(product.getId());
            productSource.setSourceName(ps.getSourceName());
            productSource.setPostelCode(ps.getPostelCode());
            productSource.setIsDefault(NO.getStatus());
            if (ps.getIsDefault() != null && !ps.getIsDefault().isEmpty()) {
              productSource.setIsDefault(
                  ps.getIsDefault().equals(YES.getStatus()) ? YES.getStatus() : NO.getStatus());
            }
            productSource.setCreatedBy(getCurrentUser());
            productSource.setCreatedDate(getInstant());
            productSource.setUpdatedBy(getCurrentUser());
            productSource.setUpdatedDate(getInstant());
            productSource.setDeleted(YNStatus.NO.getStatus());
            productSourceList.add(productSource);
          }
        });
    return productSourceList;
  }

  @Override
  public List<ProductSource> mapListUpdate(
      ProductForm productForm, Product product, List<ProductSource> productSourceList)
      throws UnitedSuppliesException {
    LOG.info("Map ProductSource Forms to Updated ProductSources Entity");
    Map<Long, ProductSource> productSourceMap =
        productSourceList.stream()
            .collect(Collectors.toMap(ProductSource::getId, Function.identity()));
    List<ProductSource> updatedProductSources = new ArrayList<>();
    productForm
        .getProductSourceForms()
        .forEach(
            ps -> {
              if (ps.getId() != null && !ps.getId().isEmpty()) {
                if (!ps.getPostelCode().isEmpty()) {
                  ProductSource productSource = productSourceMap.get(Long.valueOf(ps.getId()));
                  productSource.setSourceName(ps.getSourceName());
                  productSource.setPostelCode(ps.getPostelCode());
                  productSource.setIsDefault(NO.getStatus());
                  if (ps.getIsDefault() != null && !ps.getIsDefault().isEmpty()) {
                    productSource.setIsDefault(
                        ps.getIsDefault().equals(YES.getStatus())
                            ? YES.getStatus()
                            : NO.getStatus());
                  }
                  productSource.setUpdatedBy(getCurrentUser());
                  productSource.setUpdatedDate(getInstant());
                  updatedProductSources.add(productSource);
                  productSourceList.remove(productSource);
                }
              } else {
                if (!ps.getPostelCode().isEmpty()) {
                  ProductSource productSource = new ProductSource();
                  productSource.setProductId(product.getId());
                  productSource.setSourceName(ps.getSourceName());
                  productSource.setPostelCode(ps.getPostelCode());
                  productSource.setIsDefault(NO.getStatus());
                  if (ps.getIsDefault() != null && !ps.getIsDefault().isEmpty()) {
                    productSource.setIsDefault(
                        ps.getIsDefault().equals(YES.getStatus())
                            ? YES.getStatus()
                            : NO.getStatus());
                  }
                  productSource.setCreatedBy(getCurrentUser());
                  productSource.setCreatedDate(getInstant());
                  productSource.setUpdatedBy(getCurrentUser());
                  productSource.setUpdatedDate(getInstant());
                  productSource.setDeleted(YNStatus.NO.getStatus());
                  updatedProductSources.add(productSource);
                }
              }
            });
    productSourceList.forEach(
        ps -> {
          ps.setUpdatedBy(getCurrentUser());
          ps.setUpdatedDate(getInstant());
          ps.setDeleted(YES.getStatus());
        });
    updatedProductSources.addAll(productSourceList);
    return updatedProductSources;
  }

  @Override
  public List<ProductSourceForm> remap(List<ProductSource> productSources)
      throws UnitedSuppliesException {
    LOG.info("Map ProductSource Entity to ProductSource Form");
    List<ProductSourceForm> productSourceForms = new ArrayList<>();
    productSources.forEach(
        ps -> {
          ProductSourceForm productSourceForm = new ProductSourceForm();
          productSourceForm.setId(String.valueOf(ps.getId()));
          productSourceForm.setProdId(String.valueOf(ps.getProductId()));
          productSourceForm.setSourceName(String.valueOf(ps.getSourceName()));
          productSourceForm.setPostelCode(ps.getPostelCode());
          productSourceForm.setIsDefault(
              ps.getIsDefault().equals(YES.getStatus()) ? YES.getStatus() : null);
          productSourceForms.add(productSourceForm);
        });
    return productSourceForms;
  }
}
