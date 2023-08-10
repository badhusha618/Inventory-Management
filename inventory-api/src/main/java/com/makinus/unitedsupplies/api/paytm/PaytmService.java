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

import com.makinus.unitedsupplies.api.paytm.api.init.transaction.InitTxResponse;
import com.makinus.unitedsupplies.api.paytm.api.merchant.transaction.TransactionRequest;
import com.makinus.unitedsupplies.api.paytm.api.merchant.transaction.TransactionResponse;
import com.makinus.unitedsupplies.api.paytm.api.transaction.status.TxStatusResponse;

/**
 * @author abuabdul
 */
public interface PaytmService {

    InitTxResponse initiateTxn(String body, String... params);

    TxStatusResponse txnStatus(String body, String... params);

    TransactionResponse merchantTxn(TransactionRequest paytmVerifyTxn);
}
