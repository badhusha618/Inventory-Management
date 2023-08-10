/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.file;

import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.nio.file.Path;

/** Created by abuabdul */
public interface Writer {

  String writeBytes(final byte[] content, final String folderDate, final String fileName)
      throws UnitedSuppliesException;

  byte[] readBytes(final Path path) throws UnitedSuppliesException;
}
