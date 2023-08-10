/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makinus.unitedsupplies.common.s3;

import java.io.File;
import java.io.InputStreamReader;

/**
 * @author abuabdul
 */
public interface AmazonS3Client {

    void uploadObjectToS3(String key, File file);

    InputStreamReader readObjectFromS3(String key);

    String getObjectUrlFromS3(String key);

    void deleteObjectFromS3(String key);

}
