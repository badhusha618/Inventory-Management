/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.service.product;

import com.example.data.reftype.YNStatus;
import com.example.entity.Product;
import com.example.exception.BazzarException;
import com.example.file.ImageWriter;
import com.example.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.utils.AdminUtils.SYSTEM_USER;
import static com.example.utils.AdminUtils.getInstant;
import static java.lang.String.format;

/**
 * Created by BAD_SHA
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger LOG = LogManager.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    private ImageWriter imageWriter;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addNewProduct(Product product) {
        LOG.info("Add New Product in the catalog");
        Product savedProduct = productRepository.save(product);
        LOG.info("Saved New Product in the database");
        return savedProduct;
    }

    @Override
    @CachePut("productImage")
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
    public List<Product> productListByCategory(Long category) {
        LOG.info("Get all Products by category in the catalog");
        return productRepository.findActiveProductsByCategory(category);
    }

    @Override
    public Product removeProduct(Long id) throws BazzarException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(YNStatus.YES.getStatus());
            product.setUpdatedBy(SYSTEM_USER);
            product.setUpdatedDate(getInstant());
            return product;
        }
        throw new BazzarException(format("Product is not found with the id %s", id));
    }

    @Override
    public Product findProduct(Long id) throws BazzarException {
        LOG.info("Find Product by id with image");
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return product;
        }
        throw new BazzarException(format("Product is not found with the id %d", id));
    }

    @Override
    public Optional<Product> findOptionalProduct(Long id) throws BazzarException {
        LOG.info("Find Product by id with image");
        return productRepository.findById(id);
    }

    @Override
    public Product findProductWithImage(Long id) throws BazzarException {
        LOG.info("Find Product by id with image");
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getPrimaryImage() != null && !product.getPrimaryImage().isEmpty()) {
                //product.setFileByte(imageWriter.readBytes(get(product.getPrimaryImage())));
            }
            return product;
        }
        throw new BazzarException(format("Product is not found with the id %d", id));
    }

    @Override
    public List<Product> productListByIds(List<Long> productIds) {
        LOG.info("Get all Products in the catalog");
        return productRepository.findAllActiveProductsByIds(productIds);
    }
}
