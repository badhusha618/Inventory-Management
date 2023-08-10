/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import com.makinus.unitedsupplies.admin.data.forms.InvoiceSettingsForm;
import com.makinus.unitedsupplies.admin.data.mapping.InvoiceSettingsMapper;
import com.makinus.unitedsupplies.common.data.entity.InvoiceSettings;
import com.makinus.unitedsupplies.common.data.service.invoice.settings.InvoiceSettingsService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author ibrahim
 */
@Controller
public class InvoiceSettingsController {

    private final Logger LOG = LogManager.getLogger(InvoiceSettingsController.class);

    private static final String SETTINGS_PAGE = "dashboard/invoice-settings/invoice-settings";

    @Autowired
    private InvoiceSettingsService invoiceSettingsService;

    @Autowired
    @Qualifier("InvoiceSettingsMapper")
    private InvoiceSettingsMapper invoiceSettingsMapper;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/{type}/invoice-settings.mk")
    public String getInvoiceSettingsListPage(ModelMap model, @PathVariable("type") String type) throws UnitedSuppliesException {
        LOG.info("Open InvoiceSettings List Page");
        List<InvoiceSettings> invoiceSettingsList = invoiceSettingsService.invoiceSettingsListByType(type);
        InvoiceSettingsForm invoiceSettingsForm = invoiceSettingsList.size() > 0 ? invoiceSettingsMapper.remap(invoiceSettingsList.get(0)) : new InvoiceSettingsForm(type);
        model.addAttribute("invoiceSettingsForm", invoiceSettingsForm);
        return SETTINGS_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/invoice-settings.mk")
    public String updateInvoiceSettingsPage(@ModelAttribute("invoiceSettingsForm") InvoiceSettingsForm invoiceSettingsForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        List<InvoiceSettings> invoiceSettingsList = invoiceSettingsService.invoiceSettingsListByType(invoiceSettingsForm.getSequenceType());
        InvoiceSettings updatedInvoiceSettings = invoiceSettingsService.updateInvoiceSettings(invoiceSettingsMapper.map(invoiceSettingsForm, invoiceSettingsList.size() > 0 ? invoiceSettingsList.get(0) : new InvoiceSettings()));
        redirectAttrs.addFlashAttribute("editFlag", Boolean.TRUE);
        return "redirect:/" + updatedInvoiceSettings.getSequenceType() + "/invoice-settings.mk";
    }

}