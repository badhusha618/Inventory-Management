/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.encrypt.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/** Created by abuabdul */
public class JasyptEncryptorHelper {

  public static final String ENCRYPT_ALGORITHM = "PBEWITHMD5ANDDES";
  public static final String ENCRYPT_SALT_ENV = "APP_ENCRYPTION_PASSWORD";

  public static void main(String[] args) {
    StringEncryptor encryptor = new Encryptor().stringEncryptor();
    System.out.println("username -> " + encryptor.encrypt(args[0]));
    System.out.println("password -> " + encryptor.encrypt(args[1]));
    /*System.out.println("management username -> " + encryptor.encrypt(args[2]));
    System.out.println("management password -> " + encryptor.encrypt(args[3]));*/
  }

  private static class Encryptor {

    public EnvironmentStringPBEConfig encConfig() {
      EnvironmentStringPBEConfig encConfig = new EnvironmentStringPBEConfig();
      encConfig.setAlgorithm(ENCRYPT_ALGORITHM);
      encConfig.setPasswordEnvName(ENCRYPT_SALT_ENV); // ENVIRONMENT VARIABLE
      return encConfig;
    }

    public StringEncryptor stringEncryptor() {
      StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
      stringEncryptor.setConfig(encConfig());
      return stringEncryptor;
    }
  }
}
