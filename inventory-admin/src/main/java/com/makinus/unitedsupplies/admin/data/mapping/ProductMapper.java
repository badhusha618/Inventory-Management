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
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import com.makinus.unitedsupplies.common.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by abuabdul
 */

@Component
@Qualifier("ProductMapper")
public class ProductMapper
        implements EntityMapper<ProductForm, Product>,
        EntityUpdateMapper<ProductForm, Product>,
        EntityRemapper<Product, ProductForm> {

    private final Logger LOG = LogManager.getLogger(ProductMapper.class);

    @Autowired
    private ImageWriter imageWriter;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public Product map(ProductForm productForm) throws UnitedSuppliesException {
        LOG.info("Map Product Form to Product Entity");
        Product product = new Product();
        try {
            product.setProductCode(productForm.getProductCode());
            product.setProductName(productForm.getProductName());
            product.setDescription(productForm.getProductDescription());
            product.setTransGroup(productForm.getTransGroup());
            if (isNotEmpty(productForm.getDeliveryBy())) {
                product.setDeliveryBy(Byte.valueOf(productForm.getDeliveryBy()));
            }
            product.setOriginalFileName(productForm.getProductImage().getOriginalFilename());
            product.setImage(productForm.getProductImage().getBytes());
            product.setCreatedDateAsFolderName(
                    localDateStringAsDDMMYYYY(LocalDate.now()).replace("/", ""));
            product.setImagePath((getS3UrlFromAttachment(productForm.getProductImage(), PRODUCT_PREFIX, amazonS3Client)));

            if (!productForm.getProductImageOpt1().isEmpty()) {
                product.setOptImage1FileName(productForm.getProductImageOpt1().getOriginalFilename());
                product.setOptImage1(productForm.getProductImageOpt1().getBytes());
                product.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replace("/", ""));
                product.setOptImagePath1((getS3UrlFromAttachment(productForm.getProductImageOpt1(), PRODUCT_PREFIX, amazonS3Client)));
            }

            if (!productForm.getProductImageOpt2().isEmpty()) {
                product.setOptImage2FileName(productForm.getProductImageOpt2().getOriginalFilename());
                product.setOptImage2(productForm.getProductImageOpt2().getBytes());
                product.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replace("/", ""));
                product.setOptImagePath2((getS3UrlFromAttachment(productForm.getProductImageOpt2(), PRODUCT_PREFIX, amazonS3Client)));
            }

            if (!productForm.getProductImageOpt3().isEmpty()) {
                product.setOptImage3FileName(productForm.getProductImageOpt3().getOriginalFilename());
                product.setOptImage3(productForm.getProductImageOpt3().getBytes());
                product.setCreatedDateAsFolderName(
                        localDateStringAsDDMMYYYY(LocalDate.now()).replace("/", ""));
                product.setOptImagePath3((getS3UrlFromAttachment(productForm.getProductImageOpt3(), PRODUCT_PREFIX, amazonS3Client)));
            }

            product.setParentCategory(Long.valueOf(productForm.getParentCategory()));
            if (isNotEmpty(productForm.getParentSubCategory())) {
                product.setSubCategory(Long.valueOf(productForm.getParentSubCategory()));
            }
            if (isNotEmpty(productForm.getBrand())) {
                product.setBrand(Long.valueOf(productForm.getBrand()));
            }
            if (isNotEmpty(productForm.getQuality())) {
                product.setQuality(Long.valueOf(productForm.getQuality()));
            }
            if (isNotEmpty(productForm.getGrade())) {
                product.setGrade(Long.valueOf(productForm.getGrade()));
            }
            if (isNotEmpty(productForm.getType())) {
                product.setType(Long.valueOf(productForm.getType()));
            }
            if (isNotEmpty(productForm.getWeight())) {
                product.setWeight(Long.valueOf(productForm.getWeight()));
            }
            if (isNotEmpty(productForm.getMaterial())) {
                product.setMaterial(Long.valueOf(productForm.getMaterial()));
            }
            if (isNotEmpty(productForm.getColor())) {
                product.setColor(Long.valueOf(productForm.getColor()));
            }
            product.setSpecification(productForm.getSpecification());
            product.setSize(productForm.getSize());
            if (isNotEmpty(productForm.getCrusher())) {
                product.setCrusher(Long.valueOf(productForm.getCrusher()));
            }
            product.setUnit(productForm.getUnit());
            product.setCreatedBy(getCurrentUser());
            product.setCreatedDate(getInstant());
            product.setUpdatedBy(getCurrentUser());
            product.setUpdatedDate(getInstant());
            product.setInStock(
                    productForm.isInStock() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setActive(
                    productForm.isActiveProduct() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setTaxInclusive(
                    productForm.isTaxInclusive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setDeleted(YNStatus.NO.getStatus());
            product.setHsnCode(productForm.getHsnCode());
            if (isNotEmpty(productForm.getRateOfCgst())) {
                product.setRateOfCgst(Float.valueOf(productForm.getRateOfCgst()));
            }
            if (isNotEmpty(productForm.getRateOfSgst())) {
                product.setRateOfSgst(Float.valueOf(productForm.getRateOfSgst()));
            }
            if (isNotEmpty(productForm.getRateOfIgst())) {
                product.setRateOfIgst(Float.valueOf(productForm.getRateOfIgst()));
            }
            product.setMinOrderQty(Short.valueOf(productForm.getMinOrderQty()));
            product.setMaxOrderQty(Short.valueOf(productForm.getMaxOrderQty()));

        } catch (IOException e) {
            LOG.warn("ProductMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return product;
    }

    private String imagePath(Product product) throws UnitedSuppliesException {
        return imageWriter.writeImage(product.getImage(), product.getCreatedDateAsFolderName(), String.valueOf(AppUtils.timestamp()));
    }

    private String optImage1Path(Product product) throws UnitedSuppliesException {
        return imageWriter.writeImage(product.getOptImage1(), product.getCreatedDateAsFolderName(), product.getOptImage1FileName());
    }

    private String optImage2Path(Product product) throws UnitedSuppliesException {
        return imageWriter.writeImage(product.getOptImage2(), product.getCreatedDateAsFolderName(), product.getOptImage2FileName());
    }

    private String optImage3Path(Product product) throws UnitedSuppliesException {
        return imageWriter.writeImage(product.getOptImage3(), product.getCreatedDateAsFolderName(), product.getOptImage3FileName());
    }

    @Override
    public Product map(ProductForm productForm, Product product) throws UnitedSuppliesException {
        LOG.info("Map Product Form to Updated Product Entity");
        try {
            product.setProductCode(productForm.getProductCode());
            product.setProductName(productForm.getProductName());
            product.setDescription(productForm.getProductDescription());
            product.setTransGroup(productForm.getTransGroup());
            if (isNotEmpty(productForm.getDeliveryBy())) {
                product.setDeliveryBy(Byte.valueOf(productForm.getDeliveryBy()));
            }
            if (productForm.getEditProductImage() != null && !productForm.getEditProductImage().isEmpty()) {
                // Just to ensure old image is preserved while editing
                product.setOriginalFileName(productForm.getEditProductImage().getOriginalFilename());
                product.setImage(productForm.getEditProductImage().getBytes());
                product.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                product.setImagePath((getS3UrlFromAttachment(productForm.getEditProductImage(), PRODUCT_PREFIX, amazonS3Client)));
            }

            if (productForm.getEditProductImageOpt1() != null && !productForm.getEditProductImageOpt1().isEmpty()) {
                // Just to ensure old image is preserved while editing
                product.setOptImage1FileName(productForm.getEditProductImageOpt1().getOriginalFilename());
                product.setOptImage1(productForm.getEditProductImageOpt1().getBytes());
                product.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                product.setOptImagePath1((getS3UrlFromAttachment(productForm.getEditProductImageOpt1(), PRODUCT_PREFIX, amazonS3Client)));
            }

            if (productForm.getEditProductImageOpt2() != null && !productForm.getEditProductImageOpt2().isEmpty()) {
                // Just to ensure old image is preserved while editing
                product.setOptImage2FileName(productForm.getEditProductImageOpt2().getOriginalFilename());
                product.setOptImage2(productForm.getEditProductImageOpt2().getBytes());
                product.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                product.setOptImagePath2((getS3UrlFromAttachment(productForm.getEditProductImageOpt2(), PRODUCT_PREFIX, amazonS3Client)));
            }

            if (productForm.getEditProductImageOpt3() != null && !productForm.getEditProductImageOpt3().isEmpty()) {
                // Just to ensure old image is preserved while editing
                product.setOptImage3FileName(productForm.getEditProductImageOpt3().getOriginalFilename());
                product.setOptImage3(productForm.getEditProductImageOpt3().getBytes());
                product.setCreatedDateAsFolderName(localDateStringAsDDMMYYYY(LocalDate.now()).replaceAll("/", ""));
                product.setOptImagePath3((getS3UrlFromAttachment(productForm.getEditProductImageOpt3(), PRODUCT_PREFIX, amazonS3Client)));
            }
            long emptyLong = 0L;
            product.setBrand(isNotEmpty(productForm.getBrand()) ? Long.valueOf(productForm.getBrand()) : emptyLong);
            product.setQuality(isNotEmpty(productForm.getQuality()) ? Long.valueOf(productForm.getQuality()) : emptyLong);
            product.setGrade(isNotEmpty(productForm.getGrade()) ? Long.valueOf(productForm.getGrade()) : emptyLong);
            product.setType(isNotEmpty(productForm.getType()) ? Long.valueOf(productForm.getType()) : emptyLong);
            product.setWeight(isNotEmpty(productForm.getWeight()) ? Long.valueOf(productForm.getWeight()) : emptyLong);
            product.setMaterial(isNotEmpty(productForm.getMaterial()) ? Long.valueOf(productForm.getMaterial()) : emptyLong);
            product.setColor(isNotEmpty(productForm.getColor()) ? Long.valueOf(productForm.getColor()) : emptyLong);
            product.setSize(productForm.getSize());
            product.setSpecification(productForm.getSpecification());
            product.setCrusher(isNotEmpty(productForm.getCrusher()) ? Long.valueOf(productForm.getCrusher()) : emptyLong);
            product.setUnit(productForm.getUnit());
            product.setUpdatedBy(getCurrentUser());
            product.setUpdatedDate(getInstant());
            product.setInStock(productForm.isInStock() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setActive(productForm.isActiveProduct() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setTaxInclusive(productForm.isTaxInclusive() ? YNStatus.YES.getStatus() : YNStatus.NO.getStatus());
            product.setHsnCode(productForm.getHsnCode());
            if (isNotEmpty(productForm.getRateOfCgst())) {
                product.setRateOfCgst(Float.valueOf(productForm.getRateOfCgst()));
            }
            if (isNotEmpty(productForm.getRateOfSgst())) {
                product.setRateOfSgst(Float.valueOf(productForm.getRateOfSgst()));
            }
            if (isNotEmpty(productForm.getRateOfIgst())) {
                product.setRateOfIgst(Float.valueOf(productForm.getRateOfIgst()));
            }
            product.setMinOrderQty(Short.valueOf(productForm.getMinOrderQty()));
            product.setMaxOrderQty(Short.valueOf(productForm.getMaxOrderQty()));
        } catch (IOException e) {
            LOG.warn("ProductMapper.map throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
        return product;
    }

    @Override
    public ProductForm remap(Product product) {
        LOG.info("Map Product Entity to Product Form");
        ProductForm productForm = new ProductForm();
        productForm.setProductID(String.valueOf(product.getId()));
        productForm.setProductCode(product.getProductCode());
        productForm.setProductName(product.getProductName());
        productForm.setProductDescription(product.getDescription());
        productForm.setDeliveryBy(product.getDeliveryBy().toString());
        productForm.setTransGroup(product.getTransGroup());
        productForm.setParentCategory(String.valueOf(product.getParentCategory()));
        if (product.getSubCategory() != null) {
            productForm.setParentSubCategory(String.valueOf(product.getSubCategory()));
        }
        if (product.getBrand() != null) {
            productForm.setBrand(String.valueOf(product.getBrand()));
        }
        if (product.getQuality() != null) {
            productForm.setQuality(String.valueOf(product.getQuality()));
        }
        if (product.getGrade() != null) {
            productForm.setGrade(String.valueOf(product.getGrade()));
        }
        if (product.getType() != null) {
            productForm.setType(String.valueOf(product.getType()));
        }
        if (product.getWeight() != null) {
            productForm.setWeight(String.valueOf(product.getWeight()));
        }
        if (product.getMaterial() != null) {
            productForm.setMaterial(String.valueOf(product.getMaterial()));
        }
        if (product.getColor() != null) {
            productForm.setColor(String.valueOf(product.getColor()));
        }
        productForm.setSize(product.getSize());
        productForm.setSpecification(product.getSpecification());
        if (product.getCrusher() != null) {
            productForm.setCrusher(String.valueOf(product.getCrusher()));
        }
        productForm.setUnit(product.getUnit());
        productForm.setInStock(product.getInStock().equalsIgnoreCase(YNStatus.YES.getStatus()));
        productForm.setActiveProduct(product.getActive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        productForm.setTaxInclusive(product.getTaxInclusive().equalsIgnoreCase(YNStatus.YES.getStatus()));
        productForm.setHsnCode(product.getHsnCode());
        productForm.setRateOfCgst(product.getRateOfCgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfCgst()));
        productForm.setRateOfSgst(product.getRateOfSgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfSgst()));
        productForm.setRateOfIgst(product.getRateOfIgst() == null ? StringUtils.EMPTY : String.valueOf(product.getRateOfIgst()));
        productForm.setMinOrderQty(product.getMinOrderQty().toString());
        productForm.setMaxOrderQty(product.getMaxOrderQty().toString());
        productForm.setUpdatedBy(product.getUpdatedBy());
        productForm.setUpdatedDate(product.getUpdatedDate());
        productForm.setCreatedBy(product.getCreatedBy());
        productForm.setCreatedDate(product.getCreatedDate());
        return productForm;
    }
}

