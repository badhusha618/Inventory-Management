/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.api.paytm.api.transaction.status.TxStatusResponse;
import com.makinus.unitedsupplies.common.data.entity.*;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by sabique
 */
@Component
@Qualifier("PaymentOrderMapper")
public class PaymentOrderMapper
        implements EntityWithExtraValueMapper<TxStatusResponse, Order, PaymentOrder> {

    private final Logger LOG = LogManager.getLogger(PaymentOrderMapper.class);

    @Override
    public PaymentOrder mapExtra(TxStatusResponse status, Order order)
            throws UnitedSuppliesException {
        LOG.info("Map Payment Order Request to Payment Order Entity");
        try {
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setOrderRef(order.getOrderRef());
            paymentOrder.setResultStatus(status.getBody().getResultInfo().getResultStatus());
            paymentOrder.setResultMsg(status.getBody().getResultInfo().getResultMsg());
            paymentOrder.setResultCode(status.getBody().getResultInfo().getResultCode());
            paymentOrder.setTxnId(status.getBody().getTxnId());
            paymentOrder.setBankTxnId(status.getBody().getBankTxnId());
            if (StringUtils.isNotEmpty(status.getBody().getTxnAmount())) {
                paymentOrder.setTxnAmount(new BigDecimal(status.getBody().getTxnAmount()));
            }
            paymentOrder.setTxnType(status.getBody().getTxnType());
            paymentOrder.setGatewayName(status.getBody().getGatewayName());
            paymentOrder.setBankName(status.getBody().getBankName());
            paymentOrder.setPaymentMode(status.getBody().getPaymentMode());
            if (StringUtils.isNotEmpty(status.getBody().getRefundAmount())) {
                paymentOrder.setRefundAmount(new BigDecimal(status.getBody().getRefundAmount()));
            }
            if (StringUtils.isNotEmpty(status.getBody().getTxnDate())) {
                paymentOrder.setTxnDate(utcDateForYYYYMMDDHHMMSSS(status.getBody().getTxnDate()));
            }
            paymentOrder.setSubsId(status.getBody().getSubsId());
            if (StringUtils.isNotEmpty(status.getBody().getPayableAmount())) {
                paymentOrder.setPayableAmount(new BigDecimal(status.getBody().getPayableAmount()));
            }
            paymentOrder.setPaymentPromo(status.getBody().getPaymentPromoCheckoutData());
            paymentOrder.setVirtualAccount(virtualAccount(status, paymentOrder));
            paymentOrder.setSourceAccount(sourceAccount(status, paymentOrder));
            paymentOrder.setTransferMode(status.getBody().getTransferMode());
            paymentOrder.setUtr(status.getBody().getUtr());
            if (StringUtils.isNotEmpty(status.getBody().getBankTransactionDate())) {
                paymentOrder.setBankTransactionDate(utcDateForYYYYMMDDHHMMSSS(status.getBody().getBankTransactionDate()));
            }
            paymentOrder.setCreatedBy(getCurrentUser());
            paymentOrder.setCreatedDate(getInstant());
            paymentOrder.setUpdatedBy(getCurrentUser());
            paymentOrder.setUpdatedDate(getInstant());
            return paymentOrder;
        } catch (Exception e) {
            LOG.warn("Payment Order Mapper throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
    }

    private SourceAccount sourceAccount(TxStatusResponse status, PaymentOrder paymentOrder) {
        if (status.getBody().getSourceAccountDetails() != null) {
            SourceAccount sourceAccount = new SourceAccount();
            sourceAccount.setPaymentOrder(paymentOrder);
            sourceAccount.setMaskedAccountNumber(status.getBody().getSourceAccountDetails().getMaskedAccountNumber());
            sourceAccount.setAccountHolderName(status.getBody().getSourceAccountDetails().getAccountHolderName());
            sourceAccount.setIfscCode(status.getBody().getSourceAccountDetails().getIfscCode());
            return sourceAccount;
        }
        return null;
    }

    private VirtualAccount virtualAccount(TxStatusResponse status, PaymentOrder paymentOrder) {
        if (status.getBody().getVanInfo() != null) {
            VirtualAccount virtualAccount = new VirtualAccount();
            virtualAccount.setPaymentOrder(paymentOrder);
            virtualAccount.setVan(status.getBody().getVanInfo().getVan());
            virtualAccount.setBeneficiaryName(status.getBody().getVanInfo().getBeneficiaryName());
            virtualAccount.setIfscCode(status.getBody().getVanInfo().getIfscCode());
            virtualAccount.setBankName(status.getBody().getVanInfo().getBankName());
            virtualAccount.setPurpose(status.getBody().getVanInfo().getPurpose());
            virtualAccount.setPaymentCustomer(paymentCustomer(status, virtualAccount));
            return virtualAccount;
        }
        return null;
    }

    private PaymentCustomer paymentCustomer(TxStatusResponse status, VirtualAccount virtualAccount) {
        if (status.getBody().getVanInfo() != null && (status.getBody().getVanInfo().getCustomerDetails() != null || status.getBody().getVanInfo().getUserDefinedFields() != null)) {
            PaymentCustomer paymentCustomer = new PaymentCustomer();
            paymentCustomer.setVirtualAccount(virtualAccount);
            if (status.getBody().getVanInfo().getCustomerDetails() != null) {
                paymentCustomer.setName(status.getBody().getVanInfo().getCustomerDetails().getName());
                paymentCustomer.setEmail(status.getBody().getVanInfo().getCustomerDetails().getEmail());
                paymentCustomer.setPhone(status.getBody().getVanInfo().getCustomerDetails().getPhone());
            }
            if (status.getBody().getVanInfo().getUserDefinedFields() != null) {
                paymentCustomer.setUdf1(status.getBody().getVanInfo().getUserDefinedFields().getUdf1());
                paymentCustomer.setUdf2(status.getBody().getVanInfo().getUserDefinedFields().getUdf2());
                paymentCustomer.setUdf3(status.getBody().getVanInfo().getUserDefinedFields().getUdf3());
                paymentCustomer.setUdf4(status.getBody().getVanInfo().getUserDefinedFields().getUdf4());
                paymentCustomer.setUdf5(status.getBody().getVanInfo().getUserDefinedFields().getUdf5());
            }
            return paymentCustomer;
        }
        return null;
    }
}
