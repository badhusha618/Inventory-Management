package com.example.service;

import com.example.entity.ProductPricing;
import com.example.repository.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
/**
 * Created by Bad_sha 24/07/22
 */


@Transactional
@Service
public class ProductPricingService {

@Autowired
    private ProductPricingRepository productPricingRepository;


    public void insert( ProductPricing productPricing) {
        productPricingRepository.save(productPricing);
    }


    public Optional< ProductPricing> findById(int id) {
        return productPricingRepository.findById(id);
    }

    public Iterable<ProductPricing> findAll() {
        return productPricingRepository.findAll();
    }

    public void updateProductPricing( ProductPricing productPricing) {
        productPricingRepository.save(productPricing);
    }

    public void deleteProductPricing( ProductPricing productPricing) {
        productPricingRepository.delete(productPricing);
    }


}
