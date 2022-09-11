package com.example.service.product.quantity;


import com.example.entity.ProductQuantity;
import com.example.exception.BazzarException;
import com.example.file.ImageWriter;
import com.example.repository.ProductQuantityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductQuantityServiceImpl implements ProductQuantityService {

    private final Logger LOG = LogManager.getLogger(ProductQuantityServiceImpl.class);
    private final ProductQuantityRepository productQuantityRepository;

    @Autowired
    private ImageWriter imageWriter;

    public ProductQuantityServiceImpl(
            @Autowired ProductQuantityRepository productQuantityRepository) {
        this.productQuantityRepository = productQuantityRepository;
    }

    public List<ProductQuantity> quantityListByProduct(Long productId) {
        this.LOG.info("Get Product Quantities by product id from database");
        return this.productQuantityRepository.findQuantitiesByProduct(productId);
    }

    @Override
    public List<ProductQuantity> getAllQuantityList() {
        this.LOG.info("Get All Product Quantities from database");
        return this.productQuantityRepository.findAllQuantities();
    }

    @Override
    public ProductQuantity updateProductQuantity(ProductQuantity productQuantity) {
        LOG.info("Update existing productQuantity in the catalog");
        return productQuantityRepository.save(productQuantity);
    }

    @Override
    public ProductQuantity findProductQuantity(Long id) throws BazzarException {
        Optional<ProductQuantity> productQuantityOptional = productQuantityRepository.findById(id);
        if (productQuantityOptional.isPresent()) {
            ProductQuantity productQuantity = productQuantityOptional.get();
            return productQuantity;
        }
        throw new BazzarException(String.format("ProductQuantity is not found with the id %d", id));
    }

    public List<ProductQuantity> addProductQuantities(List<ProductQuantity> productQuantities) {
        this.LOG.info("Save list of Product Quantities in the database");
        return this.productQuantityRepository.saveAll(productQuantities);
    }

    public void deleteProductQuantitiesByIds(List<Long> ids) {
        this.productQuantityRepository.deleteProductQuantitiesByIds(ids);
    }
}
