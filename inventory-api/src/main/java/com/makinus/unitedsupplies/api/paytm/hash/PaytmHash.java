package com.makinus.unitedsupplies.api.paytm.hash;

import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.paytm.pg.merchant.PaytmChecksum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PaytmHash {

    private static final Logger LOG = LogManager.getLogger(PaytmHash.class.getSimpleName());

    private PaytmHash() {

    }

    public static String generateChecksum(String requestBody, String mKey) throws UnitedSuppliesException {
        LOG.info("Paytm generateSignature");
        try {
            String paytmChecksum = PaytmChecksum.generateSignature(requestBody, mKey);
            LOG.info("Paytm generateSignature Returns {}", paytmChecksum);
            return paytmChecksum;
        } catch (Exception e) {
            throw new UnitedSuppliesException("Checksum is not generated, error occurred");
        }
    }

    public static boolean verifyChecksum(String requestBody, String mKey, String checksum) throws UnitedSuppliesException {
        LOG.info("Paytm verifySignature");
        try {
            boolean isVerifySignature = PaytmChecksum.verifySignature(requestBody, mKey, checksum);
            LOG.info("Paytm verifySignature Returns {}", isVerifySignature);
            return isVerifySignature;
        } catch (Exception e) {
            throw new UnitedSuppliesException("Checksum is not verified, error occurred");
        }
    }

}
