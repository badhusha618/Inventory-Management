/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.image;

import com.makinus.unitedsupplies.common.exception.InventoryException;
import java.nio.file.Path;

/** Created by Bad_sha */
public interface ImageWriter {

  String writeImage(final byte[] content, final String folderDate, final String fileName)
      throws InventoryException;

  byte[] readImage(final Path path) throws InventoryException;
}
