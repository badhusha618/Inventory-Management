/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.product;

import com.makinus.unitedsupplies.common.data.dao.ProductRepository;
import com.makinus.unitedsupplies.common.data.dao.filter.product.ProductFilterDAO;
import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.form.ProductFilterForm;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.image.ImageWriter;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;
import static java.nio.file.Paths.get;

/**
 * Created by abuabdul
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ImageWriter imageWriter;
    private final ProductFilterDAO productFilterDAO;

    public ProductServiceImpl(
            @Autowired ProductRepository productRepository, @Autowired ImageWriter imageWriter, @Autowired ProductFilterDAO productFilterDAO) {
        this.productRepository = productRepository;
        this.imageWriter = imageWriter;
        this.productFilterDAO = productFilterDAO;
    }

    @Override
    public Product addNewProduct(Product product) {
        LOG.info("Add New Product in the catalog");
        Product savedProduct = productRepository.save(product);
        LOG.info("Saved New Product in the database");
        return savedProduct;
    }

    @Override
    public Product updateProduct(Product product) {
        LOG.info("Update existing product in the catalog");
        return productRepository.save(product);
    }

    @Override
    public List<Product> productList() {
        LOG.info("Get all Products in the catalog");
        return productRepository.findAllActiveProducts();
    }

    @Override
    public List<Product> productListByIds(List<Long> productIds) {
        LOG.info("Get all Products in the catalog");
        return productRepository.findAllActiveProductsByIds(productIds);
    }

    @Override
    public List<Product> productListBySubCategory(Long subCategory) {
        LOG.info("Get all Products by Sub category in the catalog");
        return productRepository.findActiveProductsBySubCategory(subCategory);
    }

    @Override
    public List<Product> productListByCategory(Long parentCategory) {
        LOG.info("Get all Products by category in the catalog");
        return productRepository.findActiveProductsByCategory(parentCategory);
    }

    @Override
    public List<Product> searchProductsByName(String searchQuery) {
        LOG.info("Search all Products by name in the catalog");
        return productRepository.findProductsBySearchQuery(searchQuery);
    }

    @Override
    public Product removeProduct(Long id) throws UnitedSuppliesException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(YNStatus.YES.getStatus());
            product.setUpdatedBy(getCurrentUser());
            product.setUpdatedDate(getInstant());
            return product;
        }
        throw new UnitedSuppliesException(format("Product is not found with the id %s", id));
    }

    @Override
    public boolean isProductAvailable(String productCode) {
        LOG.info("Check if UnitedSupplies product is available from database");
        Product product = productRepository.findActiveProduct(productCode);
        return product != null;
    }

    @Override
    public Product findProduct(Long id) throws UnitedSuppliesException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
//            product.setImage(imageWriter.readImage(get(product.getImagePath())));
            return product;
        }
        throw new UnitedSuppliesException(format("Product is not found with the id %d", id));
    }

    @Override
    public Product findProductWithImages(Long id) throws UnitedSuppliesException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setImage(imageWriter.readImage(get(product.getImagePath())));
            if (product.getOptImagePath1() != null) {
                product.setOptImage1(imageWriter.readImage(get(product.getOptImagePath1())));
            }
            if (product.getOptImagePath2() != null) {
                product.setOptImage2(imageWriter.readImage(get(product.getOptImagePath2())));
            }
            if (product.getOptImagePath3() != null) {
                product.setOptImage3(imageWriter.readImage(get(product.getOptImagePath3())));
            }
            return product;
        }
        throw new UnitedSuppliesException(format("Product is not found with the id %d", id));
    }

    @Override
    public List<Long> findProductIdsByCategory(Long categoryId) {
        LOG.info("Get product ids by category from database");
        return productRepository.findProductIdsByCategory(categoryId);
    }
    @Override
    public List<String> getTransGroupListByProductList (List<Long> productIds) {
        LOG.info("Get list of transportation group based on the product list");
        return productRepository.transportGroupListByProductList(productIds);

    }

    @Override
    public List<Product> filterProduct(ProductFilterForm productFilterForm) throws UnitedSuppliesException {
        LOG.info("Search Products by filter from the database");
        if (productFilterForm != null && (StringUtils.isNotEmpty(productFilterForm.getCategory()) || StringUtils.isNotEmpty(productFilterForm.getSubCategory()) || StringUtils.isNotEmpty(productFilterForm.getFromDate()) || StringUtils.isNotEmpty(productFilterForm.getToDate()) || StringUtils.isNotEmpty(productFilterForm.getProductName()) || StringUtils.isNotEmpty(productFilterForm.getProductCode()))) {
            return productFilterDAO.filterProduct(productFilterForm);
        }
        LOG.info("Get all Products in the catalog due to form is empty");
        return productRepository.findAllActiveProducts();
    }
}
