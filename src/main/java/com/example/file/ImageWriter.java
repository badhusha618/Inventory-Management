/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.file;

import com.example.exception.BazzarException;

import java.nio.file.Path;

/**
 * Created by BAD_SHA
 */
public interface ImageWriter {

    String writeBytes(final byte[] content, final String folderDate, final String fileName) throws BazzarException;

    byte[] readBytes(final Path path) throws BazzarException;

}
