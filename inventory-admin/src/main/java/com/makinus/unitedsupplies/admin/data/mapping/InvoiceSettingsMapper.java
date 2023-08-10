/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.mapping;

import com.makinus.Inventory.admin.data.forms.InvoiceSettingsForm;
import com.makinus.Inventory.common.data.entity.InvoiceSettings;
import com.makinus.Inventory.common.data.mapper.EntityRemapper;
import com.makinus.Inventory.common.data.mapper.EntityUpdateMapper;
import com.makinus.Inventory.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * @author Bad_sha
 */
@Component
@Qualifier("InvoiceSettingsMapper")
public class InvoiceSettingsMapper implements EntityRemapper<InvoiceSettings, InvoiceSettingsForm>, EntityUpdateMapper<InvoiceSettingsForm, InvoiceSettings> {

    private final Logger LOG = LogManager.getLogger(InvoiceSettingsMapper.class);

    @Override
    public InvoiceSettingsForm remap(InvoiceSettings invoiceSettings) throws InventoryException {
        LOG.info("Map InvoiceSettingsForm Form to InvoiceSettings Entity");
        InvoiceSettingsForm invoiceSettingsForm = new InvoiceSettingsForm();
        try {
            invoiceSettingsForm.setPrefix(invoiceSettings.getPrefix());
            invoiceSettingsForm.setSequenceType(invoiceSettings.getSequenceType());
            invoiceSettingsForm.setSequenceNo(invoiceSettings.getSequenceNo());
            invoiceSettingsForm.setFinancialYear(invoiceSettings.getFinancialYear());
        } catch (Exception e) {
            LOG.warn("InvoiceSettings.map throws exception {}", e.getMessage());
            throw new InventoryException(e.getMessage());
        }
        return invoiceSettingsForm;
    }

    @Override
    public InvoiceSettings map(InvoiceSettingsForm invoiceSettingsForm, InvoiceSettings invoiceSettings) throws InventoryException {
        LOG.info("Map InvoiceSettingsForm Form to InvoiceSettings Entity");
        try {
            invoiceSettings.setPrefix(invoiceSettingsForm.getPrefix());
            invoiceSettings.setSequenceType(invoiceSettingsForm.getSequenceType());
            invoiceSettings.setSequenceNo(invoiceSettingsForm.getSequenceNo());
            invoiceSettings.setFinancialYear(invoiceSettingsForm.getFinancialYear());
        } catch (Exception e) {
            LOG.warn("InvoiceSettings.update map throws exception {}", e.getMessage());
            throw new InventoryException(e.getMessage());
        }
        return invoiceSettings;
    }

}
