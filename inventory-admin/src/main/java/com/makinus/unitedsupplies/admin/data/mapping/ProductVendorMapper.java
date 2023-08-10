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

import com.makinus.unitedsupplies.admin.data.forms.ProductForm;
import com.makinus.unitedsupplies.admin.data.forms.ProductVendorForm;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.ProductVendor;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.mapper.ListEntityWithExtraValueUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.product.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.YES;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("ProductVendorMapper")
public class ProductVendorMapper implements EntityWithExtraValueMapper<List<ProductVendorForm>, Product, List<ProductVendor>>, ListEntityWithExtraValueUpdateMapper<ProductForm, Product, ProductVendor>, EntityRemapper<List<ProductVendor>, List<ProductVendorForm>> {

    private final Logger LOG = LogManager.getLogger(ProductVendorMapper.class);

    @Autowired
    private ProductService productService;

    @Override
    public List<ProductVendor> mapExtra(List<ProductVendorForm> productVendorForms, Product product) {
        LOG.info("Map ProductVendor Forms to ProductVendors Entity");
        List<ProductVendor> productVendorList = new ArrayList<>();
        productVendorForms.forEach(
                ps -> {
                    if (StringUtils.isNotEmpty(ps.getVendorId())) {
                        ProductVendor productVendor = new ProductVendor();
                        productVendor.setProductId(product.getId());
                        if (StringUtils.isNotEmpty(ps.getVendorId())) {
                            productVendor.setVendorId(Long.valueOf(ps.getVendorId()));
                        }
                        if (StringUtils.isNotEmpty(ps.getMrpRate())) {
                            productVendor.setMrpRate(new BigDecimal(ps.getMrpRate()));
                        }
                        if (StringUtils.isNotEmpty(ps.getSaleRate())) {
                            productVendor.setSaleRate(new BigDecimal(ps.getSaleRate()));
                        }
                        if (StringUtils.isNotEmpty(ps.getActualRate())) {
                            productVendor.setActualRate(new BigDecimal(ps.getActualRate()));
                        }
                        productVendor.setPinCode(ps.getPinCode());
                        productVendor.setDefaultVendor(ps.isDefaultVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                        productVendor.setDisableVendor(ps.isDisableVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                        productVendor.setCreatedBy(getCurrentUser());
                        productVendor.setCreatedDate(getInstant());
                        productVendor.setUpdatedBy(getCurrentUser());
                        productVendor.setUpdatedDate(getInstant());
                        productVendor.setDeleted(YNStatus.NO.getStatus());
                        productVendorList.add(productVendor);
                    }
                });
        return productVendorList;
    }

    @Override
    public List<ProductVendor> mapListUpdate(ProductForm productForm, Product product, List<ProductVendor> productVendorList) {
        LOG.info("Map ProductVendor Forms to Updated ProductVendors Entity");
        Map<Long, ProductVendor> productVendorMap = productVendorList.stream().collect(Collectors.toMap(ProductVendor::getId, Function.identity()));
        List<ProductVendor> updatedProductVendors = new ArrayList<>();
        productForm
                .getProductVendorForms()
                .forEach(
                        ps -> {
                            if (ps.getId() != null && !ps.getId().isEmpty()) {
                                if (StringUtils.isNotEmpty(ps.getVendorId())) {
                                    ProductVendor productVendor = productVendorMap.get(Long.valueOf(ps.getId()));
                                    productVendor.setVendorId(Long.valueOf(ps.getVendorId()));
                                    if (StringUtils.isNotEmpty(ps.getMrpRate())) {
                                        productVendor.setMrpRate(new BigDecimal(ps.getMrpRate()));
                                    }
                                    if (StringUtils.isNotEmpty(ps.getSaleRate())) {
                                        productVendor.setSaleRate(new BigDecimal(ps.getSaleRate()));
                                    }
                                    if (StringUtils.isNotEmpty(ps.getActualRate())) {
                                        productVendor.setActualRate(new BigDecimal(ps.getActualRate()));
                                    }
                                    productVendor.setDefaultVendor(ps.isDefaultVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                                    productVendor.setDisableVendor(ps.isDisableVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                                    productVendor.setPinCode(ps.getPinCode());
                                    productVendor.setUpdatedBy(getCurrentUser());
                                    productVendor.setUpdatedDate(getInstant());
                                    updatedProductVendors.add(productVendor);
                                    productVendorList.remove(productVendor);
                                }
                            } else {
                                if (StringUtils.isNotEmpty(ps.getVendorId())) {
                                    ProductVendor productVendor = new ProductVendor();
                                    productVendor.setProductId(product.getId());
                                    if (StringUtils.isNotEmpty(ps.getVendorId())) {
                                        productVendor.setVendorId(Long.valueOf(ps.getVendorId()));
                                    }
                                    if (StringUtils.isNotEmpty(ps.getMrpRate())) {
                                        productVendor.setMrpRate(new BigDecimal(ps.getMrpRate()));
                                    }
                                    if (StringUtils.isNotEmpty(ps.getSaleRate())) {
                                        productVendor.setSaleRate(new BigDecimal(ps.getSaleRate()));
                                    }
                                    if (StringUtils.isNotEmpty(ps.getActualRate())) {
                                        productVendor.setActualRate(new BigDecimal(ps.getActualRate()));
                                    }
                                    productVendor.setDefaultVendor(ps.isDefaultVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                                    productVendor.setDisableVendor(ps.isDisableVendor() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
                                    productVendor.setPinCode(ps.getPinCode());
                                    productVendor.setCreatedBy(getCurrentUser());
                                    productVendor.setCreatedDate(getInstant());
                                    productVendor.setUpdatedBy(getCurrentUser());
                                    productVendor.setUpdatedDate(getInstant());
                                    productVendor.setDeleted(YNStatus.NO.getStatus());
                                    updatedProductVendors.add(productVendor);
                                }
                            }
                        });
        productVendorList.forEach(
                ps -> {
                    ps.setUpdatedBy(getCurrentUser());
                    ps.setUpdatedDate(getInstant());
                    ps.setDeleted(YES.getStatus());
                });
        updatedProductVendors.addAll(productVendorList);
        return updatedProductVendors;
    }

    @Override
    public List<ProductVendorForm> remap(List<ProductVendor> productVendors) {
        LOG.info("Map ProductVendor Entity to ProductVendor Form");
        List<ProductVendorForm> productVendorForms = new ArrayList<>();
        productVendors.forEach(
                ps -> {
                    ProductVendorForm productVendorForm = new ProductVendorForm();
                    productVendorForm.setId(String.valueOf(ps.getId()));
                    productVendorForm.setVendorId(String.valueOf(ps.getVendorId()));
                    productVendorForm.setProdId(String.valueOf(ps.getProductId()));
                    if (ps.getVendorId() != null) {
                        productVendorForm.setVendorId(String.valueOf(ps.getVendorId()));
                    }
                    if (ps.getMrpRate() != null) {
                        productVendorForm.setMrpRate(String.valueOf(ps.getMrpRate()));
                    }
                    if (ps.getSaleRate() != null) {
                        productVendorForm.setSaleRate(String.valueOf(ps.getSaleRate()));
                    }
                    if (ps.getActualRate() != null) {
                        productVendorForm.setActualRate(String.valueOf(ps.getActualRate()));
                    }
                    productVendorForm.setDefaultVendor(ps.getDefaultVendor().equalsIgnoreCase(YNStatus.YES.getStatus()));
                    productVendorForm.setDisableVendor(ps.getDisableVendor().equalsIgnoreCase(YNStatus.YES.getStatus()));
                    productVendorForm.setPinCode(ps.getPinCode());
                    productVendorForms.add(productVendorForm);
                });
        return productVendorForms;
    }
}
