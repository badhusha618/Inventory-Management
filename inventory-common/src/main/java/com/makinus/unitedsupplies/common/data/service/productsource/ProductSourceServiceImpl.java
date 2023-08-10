/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.productsource;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

import com.makinus.unitedsupplies.common.data.dao.ProductSourceRepository;
import com.makinus.unitedsupplies.common.data.entity.ProductSource;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by Bad_sha */
@Service
@Transactional
public class ProductSourceServiceImpl implements ProductSourceService {

  private final Logger LOG = LogManager.getLogger(ProductSourceServiceImpl.class);

  private final ProductSourceRepository productSourceRepository;

  public ProductSourceServiceImpl(@Autowired ProductSourceRepository productSourceRepository) {
    this.productSourceRepository = productSourceRepository;
  }

  @Override
  public ProductSource saveProductSource(ProductSource productSource) {
    LOG.info("Saving ProductSource in the database");
    ProductSource savedProductSource = productSourceRepository.save(productSource);
    LOG.info("Saved ProductSource in the database");
    return savedProductSource;
  }

  @Override
  public List<ProductSource> saveProductSourceList(List<ProductSource> productSources) {
    LOG.info("Save ProductSource List in the database");
    return productSourceRepository.saveAll(productSources);
  }

  @Override
  public List<ProductSource> updateProductSourceList(List<ProductSource> productSources) {
    LOG.info("Update ProductSource List in the database");
    return productSourceRepository.saveAll(productSources);
  }

  @Override
  public List<ProductSource> productSourceList() {
    LOG.info("List ProductSources from database");
    return productSourceRepository.listAllProductSources();
  }

  @Override
  public List<ProductSource> activeProductSourceList() {
    LOG.info("List of Active ProductSources from database");
    return productSourceRepository.listAllActiveProductSources();
  }

  @Override
  public Optional<ProductSource> findDefaultProductSource(Long prodId) {
    LOG.info("Get Default ProductSource from database");
    return productSourceRepository.findDefaultSourceByProdId(prodId);
  }

  @Override
  public List<ProductSource> findDefaultProductSources() {
    LOG.info("Get Default Product Sources from database");
    return productSourceRepository.findDefaultProductSources();
  }

  @Override
  public List<ProductSource> productSourceListByProduct(Long prodId) {
    LOG.info("Get ProductSource By Product Id from database");
    return productSourceRepository.findByProdId(prodId);
  }

  @Override
  public ProductSource updateProductSource(final ProductSource productSource) {
    LOG.info("Update existing productSource in the database");
    return productSourceRepository.save(productSource);
  }

  @Override
  public ProductSource findProductSource(Long id) throws InventoryException {
    Optional<ProductSource> productSourceOptional = productSourceRepository.findById(id);
    if (productSourceOptional.isPresent()) {
      return productSourceOptional.get();
    }
    throw new InventoryException(
        String.format("ProductSource is not found with the id %d", id));
  }

  @Override
  public ProductSource removeProductSource(Long id) throws InventoryException {
    Optional<ProductSource> productSourceOptional = productSourceRepository.findById(id);
    if (productSourceOptional.isPresent()) {
      ProductSource productSource = productSourceOptional.get();
      productSource.setDeleted(YNStatus.YES.getStatus());
      productSource.setUpdatedBy(getCurrentUser());
      productSource.setUpdatedDate(getInstant());
      return productSource;
    }
    throw new InventoryException(
        String.format("ProductSource is not found with the id %d", id));
  }
}
