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

import static java.nio.file.Files.*;

import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Created by abuabdul */
@Component
public class WriterService implements Writer {

  private final Logger LOG = LogManager.getLogger(WriterService.class);

  @Value("${us.image.file.root.path.windows}")
  private String windowsRoot;

  @Value("${us.image.file.root.path.linux}")
  private String linuxRoot;

  @Override
  public String writeBytes(byte[] content, String folderDate, String fileName)
      throws UnitedSuppliesException {
    LOG.info("Writing Image/Doc/Pdf Files in the filesystem");
    try {
      return write(createFolderFirst(folderDate, fileName), content).toFile().getAbsolutePath();
    } catch (IOException e) {
      throw new UnitedSuppliesException(e.getMessage());
    }
  }

  private Path createFolderFirst(String folderDate, String fileName) throws IOException {
    LOG.info("Creating folder first in the filesystem");
    String imageFileRootPath =
        System.getProperty("os.name").contains("Win") ? windowsRoot : linuxRoot;
    LOG.debug("File Root Path: {}", imageFileRootPath);
    Path path =
        Paths.get(imageFileRootPath, folderDate, System.currentTimeMillis() + "_" + fileName);
    LOG.info("Parent Directory {}", path.getParent());
    createDirectories(path.getParent());
    return path;
  }

  @Override
  public byte[] readBytes(Path path) throws UnitedSuppliesException {
    LOG.info("Reading Image/Doc/Pdf Files from the filesystem");
    try {
      return isReadable(path) ? readAllBytes(path) : new byte[0];
    } catch (IOException e) {
      throw new UnitedSuppliesException(e.getMessage());
    }
  }
}
