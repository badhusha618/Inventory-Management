/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.utils;

import com.makinus.unitedsupplies.common.data.entity.Product;
import com.makinus.unitedsupplies.common.data.entity.Transport;
import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.reftype.Distance;
import com.makinus.unitedsupplies.common.s3.AmazonS3Client;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Base64.getEncoder;

/**
 * Created by abuabdul
 */
public interface AppUtils {

    Logger LOG = LogManager.getLogger(AppUtils.class);
    String FILE_UPLOAD = "file_upload";
    String PRODUCT_PREFIX = "product_";
    String CATEGORY_PREFIX = "category_";
    String PROMOTION_PREFIX = "promotion_";
    String VENDOR_SIGNATURE_PREFIX = "vendor_signature_";
    String PROFILE_PREFIX = "profile_";
    String ORDER_CHANGE_LOG = "Vendor for the product {0} changed from {1} to {2} and so transport charges changed from {3} to {4}, sub total changed from {5} to {6} and total changed from {7} to {8}.";
    String BASE_64 = "data:image/png;base64,";
    String FULFILLMENT_REF = "001";
    String EMAIL_TYPE = "email";
    String PRODUCT_PATH = "/products/";
    String PROMOTION = "/promotions/";
    String CATEGORY_PATH = "/categories/";
    String USER_PATH = "/user/";
    String IMG_PATH = "/img";
    String PROD_IMG_PATH = "/m/img";
    String OPT1_IMG_PATH = "/opt1/img";
    String OPT2_IMG_PATH = "/opt2/img";
    String OPT3_IMG_PATH = "/opt3/img";
    String IMAGE_VERSION = "?v=";
    String INDIAN_CODE_WITHOUT_PLUS = "91";
    String FULFILLMENT_REF_INIT = "001";
    int SMS_VARIABLE_LENGTH = 30;
    String NEW_ORDER_SMS_BODY = "Hi {0}, Thanks for shopping with us! your Order No. {1} is successfully placed and will be confirmed shortly. -United Supplies & Ecommerce";
    String PAYMENT_FAILED_SMS_BODY = "Hi {0}, The payment for your Order No. {1} is failed and we will contact you shortly for alternate payment way. -United Supplies & Ecommerce";
    String ADMIN_NEW_ORDER_SMS_BODY = "A new order {0} has been placed by a customer ({1}). Payment is {2}. Contact Info: +91 {3}. -United Supplies & Ecommerce";
    String PAYMENT_STATUS_SUCCESSFUL = "successful";
    String PAYMENT_STATUS_FAILURE = "failure";
    String ORDER_REF_NO_PREFIX = "REF";
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    DateTimeFormatter formatterDDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    SimpleDateFormat formatDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatMMMMDDYYYY = new SimpleDateFormat("MMMM dd, yyyy");
    DateTimeFormatter formatterDDMMYYYYHHMM = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    SimpleDateFormat formatYYYYMMDDHHMMSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    SimpleDateFormat formatYYYYMMDDHHMM = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    static Date getInstant() {
        return Date.from(Instant.now());
    }

    static String localDateStringAsDDMMYYYY(LocalDate localDate) {
        return localDate.format(formatterDDMMYYYY);
    }

    static Date utcDateForDDMMYYYY(String dateString) throws ParseException {
        return Date.from(formatDDMMYYYY.parse(dateString).toInstant());
    }

    static Date utcDateForYYYYMMDDHHMMSSS(String dateString) throws ParseException {
        return Date.from(formatYYYYMMDDHHMMSSS.parse(dateString).toInstant());
    }

    static String getLocalTimezoneDate(Date date, TimeZone zone) {
        return formatterDDMMYYYYHHMM.format(date.toInstant().atZone(zone.toZoneId()));
    }

    static String utcDateForDDMMYYYY(Date date) {
        return formatDDMMYYYY.format(date);
    }

    static String utcDateForMMMMDDYYYY(Date date) {
        return formatMMMMDDYYYY.format(date);
    }

    static String utcDateForMMMMDDYYYYHHMM(Date date) {
        return formatYYYYMMDDHHMM.format(date);
    }

    static String getLocalTimezoneDate(Date date) {
        return formatterDDMMYYYYHHMM.format(date.toInstant());
    }

    static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static String encodeByteAsBase64String(byte[] image) {
        return new String(getEncoder().encode(image));
    }

    static long timestamp() {
        return Instant.now().toEpochMilli();
    }

    static String extractTimeStamp(String url) {
        if (StringUtils.isNotEmpty(url)) {
            return url.split("_")[1];
        }
        return StringUtils.EMPTY;
    }

    static Product getProductFromMap(Map<Long, Product> productMap, Long productId) {
        if (productMap != null) {
            return productMap.getOrDefault(productId, new Product());
        }
        return new Product();
    }

    static Vendor getVendorFromMap(Map<Long, Vendor> vendorMap, Long id) {
        if (vendorMap != null) {
            return vendorMap.getOrDefault(id, new Vendor());
        }
        return new Vendor();
    }

    static String formattedPhoneNo(String phoneNo, String code) {
        String phoneNoWOSpaces = phoneNo.replaceAll("[\\-\\s]", "");
        int phoneNoLength = phoneNoWOSpaces.length() - 10;
        return phoneNoLength > 0 ? code + phoneNoWOSpaces.substring(phoneNoLength) : code + phoneNoWOSpaces;
    }


    static List<String> productNameSplitBySize(String text, int size) {
        List<String> productNames = new ArrayList<>();
        int length = text == null ? 0 : text.length();
        for (int i = 0; i < length; i += size) {
            productNames.add(text.substring(i, Math.min(length, i + size)));
        }
        return productNames;
    }

    @Deprecated
    static String formattedFulfillmentRef(String fulfillmentRef) {
        switch (fulfillmentRef.length()) {
            case 1:
                return "00" + fulfillmentRef;
            case 2:
                return "0" + fulfillmentRef;
            default:
                return fulfillmentRef;
        }
    }

    static BigDecimal stringToDecimal(String stringValue) {
        return StringUtils.isNotEmpty(stringValue) ? new BigDecimal(stringValue) : new BigDecimal("0");
    }

    static String decimalToString(BigDecimal decimalValue) {
        return decimalValue != null ? String.valueOf(decimalValue) : StringUtils.EMPTY;
    }

    static Integer decimalToInt(BigDecimal decimalValue) {
        return decimalValue != null ? decimalValue.intValue() : 0;
    }

    static String longToString(Long longValue) {
        return longValue != null ? String.valueOf(longValue) : StringUtils.EMPTY;
    }

    static Long stringToLong(String stringValue) {
        return StringUtils.isNotEmpty(stringValue) ? Long.parseLong(stringValue) : 0L;
    }

    static Integer stringToInt(String strValue) {
        return StringUtils.isNotEmpty(strValue) ? Integer.parseInt(strValue) : 0;
    }

    static String intToString(Integer intValue) {
        return intValue != null ? String.valueOf(intValue) : StringUtils.EMPTY;
    }

    static String doubleToString(Double doubleVale) {
        return doubleVale != null ? String.valueOf(doubleVale) : StringUtils.EMPTY;
    }

    static Double stringToDouble(String strValue) {
        return StringUtils.isNotEmpty(strValue) ? Double.parseDouble(strValue) : 0;
    }

    static String shortToString(Short shortValue) {
        return shortValue != null ? String.valueOf(shortValue) : StringUtils.EMPTY;
    }

    static String floatToString(Float floatValue) {
        return floatValue != null ? String.valueOf(floatValue) : StringUtils.EMPTY;
    }

    static String byteToString(Byte byteValue) {
        return byteValue != null ? String.valueOf(byteValue) : StringUtils.EMPTY;
    }

    static Byte stringToByte(String strValue) {
        return StringUtils.isNotEmpty(strValue) ? Byte.parseByte(strValue) : 0;
    }


    static BigDecimal transportChargeCalculationMethod(List<Transport> transportList, Integer productQuantity, Integer distance) {
        Integer maxQty = Collections.max(transportList.stream().map(Transport::getQuantity).collect(Collectors.toList()));
        if (maxQty == 0){
            return BigDecimal.ZERO;
        }
        Integer quotient = productQuantity / maxQty;
        BigDecimal transportChargeForMaxQty = BigDecimal.ZERO;
        if (productQuantity > maxQty) {
            productQuantity = productQuantity % maxQty;
            transportChargeForMaxQty = transportChargeCalculation(transportList, maxQty, distance);
        }
        BigDecimal transportCharge = transportChargeCalculation(transportList, productQuantity, distance);
        return transportCharge.add(transportChargeForMaxQty.multiply(new BigDecimal(quotient)));
    }

    static BigDecimal transportChargeCalculation(List<Transport> transportList, Integer productQuantity, Integer distance) {
        Map<Integer, List<Transport>> transportMap = transportList.stream().collect(Collectors.groupingBy(Transport::getQuantity));
        List<Integer> quantityList = getPossibleQuantitiesForTransport(transportList, productQuantity);
        Integer availableQuantity = 0;
        if (quantityList.isEmpty()) { //TODO: This function is deprecated and which is achieved by the previous method
            return BigDecimal.ZERO;
        }
        if (!transportList.isEmpty()) {
            availableQuantity = quantityList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()).get(0);
        }
        List<Transport> transportsByQuantity = transportMap.getOrDefault(availableQuantity, new ArrayList<>());
        Map<Integer, Transport> transportsMapByQuantity = transportsByQuantity.stream()
                .collect(Collectors.toMap(Transport::getDistance, Function.identity()));
        List<Integer> distanceList = getPossibleDistances(transportsByQuantity, distance);
        Integer availableDistance = 0;
        if (!transportsByQuantity.isEmpty()) {
            availableDistance = (distanceList.isEmpty() ? transportsByQuantity.stream().sorted(Comparator.comparing(Transport::getDistance).reversed()).collect(Collectors.toList()).get(0).getDistance()
                    : distanceList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()).get(0));
        }
        return transportsMapByQuantity.getOrDefault(availableDistance, new Transport()).getCharges();
    }

    static List<Integer> getPossibleQuantitiesForTransport(List<Transport> transportList, Integer productQuantity) {
        return transportList.stream().filter(t -> t.getQuantity() >= productQuantity).map(Transport::getQuantity).collect(Collectors.toList());
    }

    static List<Integer> getPossibleDistances(List<Transport> transportList, Integer distance) {
        return transportList.stream().filter(t -> t.getDistance() >= distance).map(Transport::getDistance).collect(Collectors.toList());
    }

    static Integer getDistanceByPincodeRange(String source, String destination) {
        Integer[] distance = Arrays.stream(Distance.values()).filter(d -> d.getSource().equals(source) && d.getDestination().equals(destination)).map(Distance::getDistance).toArray(Integer[]::new);
        return distance.length > 0 ? distance[0] : 0;
    }

    static String formatIntoTwoPrecision(BigDecimal number) {
        return decimalFormat.format(number);
    }

    static String getS3UrlFromAttachment(MultipartFile multipartFile, String typeName, AmazonS3Client amazonS3Client) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        File tempFile = File.createTempFile(FILE_UPLOAD, "." + extension);
        IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(tempFile));
        amazonS3Client.uploadObjectToS3(format("%s%d.%s", typeName, currentTimeMillis, extension), tempFile);
        return amazonS3Client.getObjectUrlFromS3(format("%s%d.%s", typeName, currentTimeMillis, extension));
    }
}
