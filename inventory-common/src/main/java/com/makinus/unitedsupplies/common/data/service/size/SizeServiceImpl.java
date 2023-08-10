/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.size;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

import com.makinus.unitedsupplies.common.data.dao.SizeRepository;
import com.makinus.unitedsupplies.common.data.entity.Size;
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
public class SizeServiceImpl implements SizeService {

  private final Logger LOG = LogManager.getLogger(SizeServiceImpl.class);

  private final SizeRepository sizeRepository;

  public SizeServiceImpl(@Autowired SizeRepository sizeRepository) {
    this.sizeRepository = sizeRepository;
  }

  @Override
  public Size saveSize(Size size) {
    LOG.info("Saving Size in the database");
    Size savedSize = sizeRepository.save(size);
    LOG.info("Saved Size in the database");
    return savedSize;
  }

  @Override
  public List<Size> sizeList() {
    LOG.info("List Sizes from database");
    return sizeRepository.listAllSizes();
  }

  @Override
  public List<Size> sizeListByCategory(Long categoryId) {
    LOG.info("List Sizes By Category from database");
    return sizeRepository.listAllSizesByCategory(categoryId);
  }

  @Override
  public Size updateSize(final Size size) {
    LOG.info("Update existing size in the catalog");
    return sizeRepository.save(size);
  }

  @Override
  public Size findSize(Long id) throws InventoryException {
    Optional<Size> sizeOptional = sizeRepository.findById(id);
    if (sizeOptional.isPresent()) {
      return sizeOptional.get();
    }
    throw new InventoryException(String.format("Size is not found with the id %d", id));
  }

  @Override
  public Size removeSize(Long id) throws InventoryException {
    Optional<Size> sizeOptional = sizeRepository.findById(id);
    if (sizeOptional.isPresent()) {
      Size size = sizeOptional.get();
      size.setDeleted(YNStatus.YES.getStatus());
      size.setUpdatedBy(getCurrentUser());
      size.setUpdatedDate(getInstant());
      return size;
    }
    throw new InventoryException(String.format("Promotion is not found with the id %d", id));
  }
}
