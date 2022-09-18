package com.example.service.product.image;



import com.example.entity.ProductImage;
import com.example.exception.BazzarException;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> productImageList();

    List<ProductImage> imageListByProduct(Long productId);

    ProductImage updateProductImage(final ProductImage productImage);

    ProductImage findProductImage(Long id) throws BazzarException;

    List<ProductImage> addProductImages(List<ProductImage> productImages);

    void deleteProductImagesByIds(List<Long> ids);
}
