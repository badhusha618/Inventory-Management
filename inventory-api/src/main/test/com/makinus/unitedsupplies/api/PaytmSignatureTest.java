package com.makinus.unitedsupplies.api;

import com.makinus.unitedsupplies.api.paytm.hash.PaytmHash;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

public class PaytmSignatureTest {

    public void checkPaytmSignature() throws UnitedSuppliesException {
        String body = "{\"websiteName\":\"WEBSTAGING\",\"userInfo\":{\"custId\":\"9\",\"mobile\":\"9843871627\",\"email\":\"abu@makinus.com\"},\"requestType\":\"Payment\",\"orderId\":\"US7850196\",\"mid\":\"pjrHpU98783043471677\",\"callbackUrl\":\"https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=US7850196\",\"txnAmount\":{\"currency\":\"INR\",\"value\":\"3350\"}}";
        String checksum = PaytmHash.generateChecksum(body, "u37n88T%U&u1HTtD");
        boolean verifyChecksum = PaytmHash.verifyChecksum(body, "u37n88T%U&u1HTtD", checksum);
        System.out.println(verifyChecksum);
    }
}
