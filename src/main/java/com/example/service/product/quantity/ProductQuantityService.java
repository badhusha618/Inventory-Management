package com.example.service.product.quantity;

import com.example.entity.ProductQuantity;
import com.example.exception.BazzarException;

import java.util.List;

public interface ProductQuantityService {

    List<ProductQuantity> quantityListByProduct(Long productId);

    List<ProductQuantity> getAllQuantityList();

    ProductQuantity updateProductQuantity(final ProductQuantity productQuantity);

    ProductQuantity findProductQuantity(Long id) throws BazzarException;

    List<ProductQuantity> addProductQuantities(List<ProductQuantity> productQuantities);

    void deleteProductQuantitiesByIds(List<Long> ids);
}
