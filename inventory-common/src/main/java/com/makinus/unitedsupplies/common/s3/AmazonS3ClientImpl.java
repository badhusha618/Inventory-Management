/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author abuabdul
 */
@Component
public class AmazonS3ClientImpl implements AmazonS3Client {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void uploadObjectToS3(String key, File file) {
        LOG.info("Open uploadObjectToS3 {}", this.getClass().getSimpleName());
        amazonS3.putObject(new PutObjectRequest(bucketName, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public InputStreamReader readObjectFromS3(String key) {
        LOG.info("Open readObjectFromS3 {}", this.getClass().getSimpleName());
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, key));
        return new InputStreamReader(object.getObjectContent().getDelegateStream());
    }

    @Override
    public String getObjectUrlFromS3(String key) {
        LOG.info("Open getObjectUrlFromS3 {}", this.getClass().getSimpleName());
        URL url = amazonS3.getUrl(bucketName, key);
        return url.toExternalForm();
    }

    @Override
    public void deleteObjectFromS3(String key) {
        LOG.info("Open deleteObjectFromS3 {}", this.getClass().getSimpleName());
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
    }
}
