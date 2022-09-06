package com.example.service.product.image;

import com.makinus.usm.nxg.common.exception.MakinusException;
import com.makinus.usm.nxg.models.entity.ProductImage;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> productImageList();

    List<ProductImage> imageListByProduct(Long productId);

    ProductImage updateProductImage(final ProductImage productImage);

    ProductImage findProductImage(Long id) throws MakinusException;

    List<ProductImage> addProductImages(List<ProductImage> productImages);

    void deleteProductImagesByIds(List<Long> ids);
}
