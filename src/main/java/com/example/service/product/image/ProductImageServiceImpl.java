package com.example.service.product.image;

import com.makinus.usm.nxg.common.exception.MakinusException;
import com.makinus.usm.nxg.common.file.ImageWriter;
import com.makinus.usm.nxg.models.entity.ProductImage;
import com.makinus.usm.nxg.product.data.dao.ProductImageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final Logger LOG = LogManager.getLogger(ProductImageServiceImpl.class);
    private final ProductImageRepository productImageRepository;

    @Autowired
    private ImageWriter imageWriter;

    public ProductImageServiceImpl(
            @Autowired ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> productImageList() {
        LOG.info("List ProductImages from database");
        return productImageRepository.listAllProductImages();
    }

    public List<ProductImage> imageListByProduct(Long productId) {
        this.LOG.info("Get Product Images by product id from database");
        return this.productImageRepository.findImagesByProduct(productId);
    }

    @Override
    public ProductImage updateProductImage(ProductImage productImage) {
        LOG.info("Update existing productImage in the catalog");
        return productImageRepository.save(productImage);
    }

    @Override
    public ProductImage findProductImage(Long id) throws MakinusException {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(id);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            productImage.setFileByte(imageWriter.readBytes(get(productImage.getImagePath())));
            return productImage;
        }
        throw new MakinusException(String.format("ProductImage is not found with the id %d", id));
    }

    public List<ProductImage> addProductImages(List<ProductImage> productImages) {
        this.LOG.info("Save list of Product Images in the database");
        return this.productImageRepository.saveAll(productImages);
    }

    public void deleteProductImagesByIds(List<Long> ids) {
        this.productImageRepository.deleteProductImagesByIds(ids);
    }
}
