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

import com.makinus.unitedsupplies.common.data.dao.InvoiceSettingsRepository;
import com.makinus.unitedsupplies.common.data.entity.InvoiceSettings;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Transactional
public class InvoiceSettingsServiceImpl implements InvoiceSettingsService {

    private final Logger LOG = LogManager.getLogger(InvoiceSettingsService.class);

    private final InvoiceSettingsRepository invoiceSettingsRepository;

    public InvoiceSettingsServiceImpl(@Autowired InvoiceSettingsRepository invoiceSettingsRepository) {
        this.invoiceSettingsRepository = invoiceSettingsRepository;
    }

    @Override
    public List<InvoiceSettings> invoiceSettingsListByType(String sequenceType) {
        LOG.info("List InvoiceSettings by sequence type from database");
        return invoiceSettingsRepository.findAllInvoiceSettingsByType(sequenceType);
    }

    @Override
    public List<InvoiceSettings> protectedInvoiceSettingsListByType(String sequenceType) {
        LOG.info("List InvoiceSettings by sequence type from database with Pessimistic Lock");
        return invoiceSettingsRepository.findAllInvoiceSettingsByTypeWithLock(sequenceType);
    }

    @Override
    public InvoiceSettings findInvoiceSettings(Long id) throws InventoryException {
        Optional<InvoiceSettings> invoiceSettingssOptional = invoiceSettingsRepository.findById(id);
        if (invoiceSettingssOptional.isPresent()) {
            return invoiceSettingssOptional.get();
        }
        throw new InventoryException(format("InvoiceSettings is not found with the id %d", id));
    }

    @Override
    public InvoiceSettings addNewInvoiceSettings(InvoiceSettings branch) {
        LOG.info("Add New InvoiceSettings in the database");
        return invoiceSettingsRepository.save(branch);
    }

    @Override
    public InvoiceSettings updateInvoiceSettings(InvoiceSettings branch) {
        LOG.info("Update InvoiceSettings in the database");
        return invoiceSettingsRepository.save(branch);
    }
}
