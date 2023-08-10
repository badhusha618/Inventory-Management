USE `UNITEDSUPPLIES`;

ALTER TABLE `PRODUCTS` ADD (`ACTIVE` VARCHAR(1) NOT NULL DEFAULT 'T');

ALTER TABLE `TRANSPORT` DROP COLUMN `PRODUCT_ID`;

ALTER TABLE `TRANSPORT` ADD `TRANS_GROUP` VARCHAR(30) NOT NULL;

ALTER TABLE `TRANSPORT` ADD `UNIT_ID` BIGINT NOT NULL;

ALTER TABLE `PRODUCTS` ADD (`TRANS_GROUP` VARCHAR(30) NOT NULL DEFAULT 'FREE');

ALTER TABLE `USER_CART` MODIFY `TRANS_CHARGES` DECIMAL(10,2);

ALTER TABLE `PROD_ORDERS` ADD (`TRANS_GROUP` VARCHAR(30) NOT NULL DEFAULT 'FREE');

