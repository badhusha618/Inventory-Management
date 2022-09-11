ALTER DATABASE `INVENTORY_MANAGEMENT` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `INVENTORY_MANAGEMENT`;

CREATE TABLE IF NOT EXISTS `OFFER` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   OFFER_NAME VARCHAR(200) NOT NULL,
   DESCRIPTION VARCHAR(500) NULL,
   IMAGE_PATH VARCHAR(500) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `PROMOTION` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   PROMOTION_NAME VARCHAR(200) NOT NULL,
   DESCRIPTION VARCHAR(500) NULL,
   IMAGE_PATH VARCHAR(500) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `PARENT_CATEGORY` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   CATEGORY_CODE VARCHAR(20) NOT NULL,
   CATEGORY_NAME VARCHAR(100) NOT NULL,
   IMAGE_PATH VARCHAR(500) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `CATEGORY` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   CATEGORY_CODE VARCHAR(20) NOT NULL,
   CATEGORY_NAME VARCHAR(100) NOT NULL,
   PARENT_CATEGORY BIGINT NULL,
   IMAGE_PATH VARCHAR(500) NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `BRAND` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   BRAND_CODE VARCHAR(10) NOT NULL,
   BRAND_NAME VARCHAR(50) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `PRODUCT` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   PRODUCT_NAME VARCHAR(50) NOT NULL,
   IMAGE_PATH VARCHAR(500) NOT NULL,
   DESCRIPTION VARCHAR(500) NULL,
   BRAND BIGINT NULL,
   CATEGORY BIGINT NOT NULL,
   UNIT VARCHAR(2) NOT NULL,
   MRP_RATE DECIMAL(10,2) NULL,
   SALE_RATE DECIMAL(10,2) NOT NULL,
   IN_STOCK VARCHAR(100) NOT NULL,
   TAX_INCLUDED VARCHAR(1) NOT NULL,
   HSN_CODE VARCHAR(30) NULL,
   GST_RATE DECIMAL(10,2) NULL,
   VENDOR_NAME JSON NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `PROD_IMAGE` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   PRODUCT_ID BIGINT NOT NULL,
   IMAGE_PATH VARCHAR(500) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `PROD_QUANTITY` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   PRODUCT_ID BIGINT NOT NULL,
   QUANTITY JSON NOT NULL,
   UNIT VARCHAR(2) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;

CREATE TABLE IF NOT EXISTS `USERS` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   FULL_NAME VARCHAR(200) NULL,
   PASSWORD VARCHAR(200) NOT NULL,
   MOBILE VARCHAR(20) NOT NULL,
   EMAIL VARCHAR(200) NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   ROLE VARCHAR(1) NULL,
   IMAGE_PATH VARCHAR(500) NULL,
   ONBOARDED VARCHAR(1) NOT NULL,
   RESET_PWD VARCHAR(1) NOT NULL,
   TOKEN VARCHAR(50) NULL,
   OTP SMALLINT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8;

CREATE INDEX `UNIQ_USR_I` ON `USERS` (FULL_NAME, DELETED);

CREATE TABLE IF NOT EXISTS `CUSTOMER` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   MOBILE VARCHAR(20) NOT NULL UNIQUE,
   EMAIL VARCHAR(200) NULL,
   PASSWORD VARCHAR(200) NOT NULL,
   NAME VARCHAR(100) NOT NULL,
   RESET_PWD VARCHAR(1) NOT NULL,
   IMAGE_PATH VARCHAR(500) NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (ID)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `ADMIN_USERS` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   FULL_NAME VARCHAR(200) NOT NULL,
   EMAIL VARCHAR(200) NULL,
   MOBILE VARCHAR(20) NOT NULL,
   USERNAME VARCHAR(200) NOT NULL,
   PASSWORD VARCHAR(200) NOT NULL,
   ROLE VARCHAR(1) NOT NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   ACTIVE VARCHAR(1) NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `ORDERS` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   USER_ID BIGINT NOT NULL,
   ORDER_NUMBER VARCHAR(20) NOT NULL,
   ORDER_DATE DATETIME NOT NULL,
   ORDER_SUBTOTAL DECIMAL(10,0) NOT NULL,
   DELIVERY_CHARGES DECIMAL(10,0) NOT NULL,
   DISCOUNT DECIMAL(10,0) NOT NULL,
   ORDER_TOTAL DECIMAL(10,0) NOT NULL,
   DELIVERY_ADDRESS JSON NOT NULL,
   BILLING_ADDRESS JSON NULL,
   IS_BILL_ADDR_SAME varchar(1) NOT NULL,
   PAYMENT_STATUS VARCHAR(1) NOT NULL,
   PAYMENT_TYPE VARCHAR(1) NOT NULL,
   ORDER_STATUS VARCHAR(1) NOT NULL,
   ORDER_TYPE VARCHAR(1) NOT NULL,
   ORDER_VIEWED VARCHAR(1) NOT NULL,
   PAID_AMOUNT DECIMAL(10,0) NOT NULL,
   PAYMENT_PROVIDER VARCHAR(1) NOT NULL,
   GW_ORDER_ID VARCHAR(50) NULL,
   CREATED_BY VARCHAR(200) NOT NULL,
   CREATED_DATE DATETIME NOT NULL,
   UPDATED_BY VARCHAR(200) NOT NULL,
   UPDATED_DATE DATETIME NOT NULL,
   DELETED VARCHAR(1) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8;

CREATE INDEX `UNIQ_ORD_I` ON `ORDERS` (USER_ID, DELETED);

CREATE TABLE `PRODUCT_ORDERS` (
   `ID` BIGINT NOT NULL AUTO_INCREMENT,
   ORDER_ID BIGINT NOT NULL,
   PROD_ID BIGINT NOT NULL,
   PRODUCT_NAME VARCHAR(200) NOT NULL,
   PRODUCT_IMAGE VARCHAR(200) NOT NULL,
   PROD_QTY INT NOT NULL,
   PROD_MEASURE VARCHAR(10) NOT NULL,
   PROD_SALE_RATE DECIMAL(10,0) NOT NULL,
   ORDER_STATUS VARCHAR(1) NOT NULL,
   VENDOR_NAME VARCHAR(200) NOT NULL,
   PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

CREATE INDEX `UNIQ_PRD_ORD_I` ON `PRODUCT_ORDERS` (ORDER_ID, PRODUCT_NAME);

CREATE TABLE IF NOT EXISTS `BUSINESS_DETAILS` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  COMPANY_NAME VARCHAR(50) NOT NULL,
  ADDRESS VARCHAR(500) NULL,
  PHONE_NO1 VARCHAR(20) NULL,
  PHONE_NO2 VARCHAR(20) NULL,
  GST_NO VARCHAR(100) NULL,
  PAN_NO VARCHAR(100) NULL,
  IMAGE_PATH VARCHAR(500) NULL,
  CREATED_BY VARCHAR(200) NOT NULL,
  CREATED_DATE DATETIME NOT NULL,
  UPDATED_BY VARCHAR(200) NOT NULL,
  UPDATED_DATE DATETIME NOT NULL,
  DELETED VARCHAR(1) NOT NULL,
  INCLUDE_TAX VARCHAR(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB  DEFAULT CHARSET=UTF8MB4;
