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


import com.makinus.unitedsupplies.common.data.entity.RazorpayPayment;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.Optional;

/**
 * @author ibrahim
 */
public interface RazorpayPaymentService {

    RazorpayPayment saveRazorpayPayment(final RazorpayPayment razorpayPayment);

    Optional<RazorpayPayment> getRazorpayByOrderRef(Long OrderRef) throws UnitedSuppliesException;

}
