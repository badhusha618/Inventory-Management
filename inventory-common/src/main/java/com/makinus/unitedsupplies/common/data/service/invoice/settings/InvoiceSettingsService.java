/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.invoice.settings;


import com.makinus.unitedsupplies.common.data.entity.InvoiceSettings;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;

public interface InvoiceSettingsService {

    List<InvoiceSettings> invoiceSettingsListByType(String sequenceType);

    InvoiceSettings findInvoiceSettings(Long id) throws UnitedSuppliesException;

    List<InvoiceSettings> protectedInvoiceSettingsListByType(String sequenceType);

    InvoiceSettings addNewInvoiceSettings(final InvoiceSettings invoiceSettings);

    InvoiceSettings updateInvoiceSettings(final InvoiceSettings invoiceSettings);
}
