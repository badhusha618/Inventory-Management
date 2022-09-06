package com.example.service.product.quantity;

import com.makinus.usm.nxg.common.exception.MakinusException;
import com.makinus.usm.nxg.models.entity.ProductQuantity;

import java.util.List;

public interface ProductQuantityService {

    List<ProductQuantity> quantityListByProduct(Long productId);

    List<ProductQuantity> getAllQuantityList();

    ProductQuantity updateProductQuantity(final ProductQuantity productQuantity);

    ProductQuantity findProductQuantity(Long id) throws MakinusException;

    List<ProductQuantity> addProductQuantities(List<ProductQuantity> productQuantities);

    void deleteProductQuantitiesByIds(List<Long> ids);
}
