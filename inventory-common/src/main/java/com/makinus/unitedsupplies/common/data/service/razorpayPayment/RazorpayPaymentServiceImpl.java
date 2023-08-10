/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.razorpayPayment;

import com.makinus.unitedsupplies.common.data.dao.RazorpayPaymentRepository;
import com.makinus.unitedsupplies.common.data.entity.RazorpayPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author ibrahim
 */

@Service
@Transactional
public class RazorpayPaymentServiceImpl implements RazorpayPaymentService {

    private final Logger LOG = LoggerFactory.getLogger(RazorpayPaymentServiceImpl.class);

    private final RazorpayPaymentRepository razorpayPaymentRepository;

    public RazorpayPaymentServiceImpl(@Autowired RazorpayPaymentRepository razorpayPaymentRepository) {
        this.razorpayPaymentRepository = razorpayPaymentRepository;
    }

    @Override
    public RazorpayPayment saveRazorpayPayment(RazorpayPayment razorpayPayment) {
        LOG.info("Saving RazorpayPayment in the database");
        RazorpayPayment savedRazorpayPayment = razorpayPaymentRepository.save(razorpayPayment);
        LOG.info("Saved RazorpayPayment in the database");
        return savedRazorpayPayment;
    }

    @Override
    public Optional<RazorpayPayment> getRazorpayByOrderRef(Long orderRef) {
        LOG.info("Get Razorpay Payment by using order ref");
        return razorpayPaymentRepository.findRazorpayPaymentByOrderRef(orderRef);
    }
}
