/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.paytm;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makinus.unitedsupplies.api.paytm.api.HttpClientAPIException;
import com.makinus.unitedsupplies.api.paytm.api.HttpClientAPIStrategy;
import com.makinus.unitedsupplies.api.paytm.api.init.transaction.InitTxResponse;
import com.makinus.unitedsupplies.api.paytm.api.merchant.transaction.TransactionRequest;
import com.makinus.unitedsupplies.api.paytm.api.merchant.transaction.TransactionResponse;
import com.makinus.unitedsupplies.api.paytm.api.transaction.status.TxStatusResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author abuabdul
 */
@Component
public class PaytmServiceImpl implements PaytmService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    @Qualifier("InitTransactionAPIStrategy")
    private HttpClientAPIStrategy initTxnAPI;

    @Autowired
    @Qualifier("TransactionStatusAPIStrategy")
    private HttpClientAPIStrategy txnStatusAPI;

    @Autowired
    @Qualifier("MerchantTxnStatusAPIStrategy")
    private HttpClientAPIStrategy merchantTxnStatusAPI;

    @Override
    public InitTxResponse initiateTxn(String body, String... params) {
        try {
            String transactionStatus = initTxnAPI.makeApiCall(body, params);
            InitTxResponse initTxResponse = mapper.readValue(transactionStatus, InitTxResponse.class);
            initTxResponse.setResponseString(transactionStatus);
            return initTxResponse;
        } catch (HttpClientAPIException | IOException e) {
            LOG.error("Error occurred while calling initiate transaction api {}", e.getMessage());
        }
        return null;
    }

    @Override
    public TxStatusResponse txnStatus(String body, String... params) {
        try {
            String transactionStatus = txnStatusAPI.makeApiCall(body, params);
            TxStatusResponse txStatusResponse = mapper.readValue(transactionStatus, TxStatusResponse.class);
            txStatusResponse.setResponseString(transactionStatus);
            return txStatusResponse;
        } catch (HttpClientAPIException | IOException e) {
            LOG.error("Error occurred while calling transaction status api {}", e.getMessage());
        }
        return null;
    }

    @Override
    public TransactionResponse merchantTxn(TransactionRequest txnRequest) {
        try {
            String body = mapper.writeValueAsString(txnRequest);
            String transactionStatus = merchantTxnStatusAPI.makeApiCall(body);
            return mapper.readValue(transactionStatus, TransactionResponse.class);
        } catch (HttpClientAPIException | IOException e) {
            LOG.error("Error occurred while calling merchant transaction api {}", e.getMessage());
        }
        return null;
    }
}
