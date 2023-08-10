/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.admin;

import com.makinus.Inventory.admin.data.forms.VendorForm;
import com.makinus.Inventory.admin.data.mapping.VendorMapper;
import com.makinus.Inventory.admin.data.service.excel.GenericWriter;
import com.makinus.Inventory.admin.data.service.excel.VendorExcelDTO;
import com.makinus.Inventory.common.data.entity.Vendor;
import com.makinus.Inventory.common.data.form.VendorFilterForm;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import com.makinus.Inventory.common.data.service.vendor.VendorService;
import com.makinus.Inventory.common.exception.InventoryException;
import com.makinus.Inventory.common.s3.AmazonS3Client;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.makinus.Inventory.common.utils.AppUtils.getInstant;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * @author Bad_sha
 */
@Controller
public class VendorController {

    private final Logger LOG = LogManager.getLogger(VendorController.class);

    private static final String LIST_VENDOR_PAGE = "dashboard/vendor/vendor-list";
    private static final String VENDOR_ADD_PAGE = "dashboard/vendor/vendor-add";
    private static final String VENDOR_EDIT_PAGE = "dashboard/vendor/vendor-edit";

    @Autowired
    private VendorService vendorService;

    @Autowired
    @Qualifier("VendorMapper")
    private VendorMapper vendorMapper;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    @Qualifier("VendorExcelWriter")
    private GenericWriter<List<VendorExcelDTO>> vendorExcelWriter;

    @ModelAttribute("zone")
    public TimeZone injectTimeZone(TimeZone serverZone, @CookieValue(value = "us_user_timezone", required = false) String zoneID) {
        LOG.info("Setting Zone in model attribute - {}", this.getClass().getSimpleName());
        return isNotEmpty(zoneID) ? TimeZone.getTimeZone(zoneID) : serverZone;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/list/vendor.mk")
    public String listVendor(ModelMap model, @ModelAttribute("vendorList") ArrayList<Vendor> vendors, @ModelAttribute("vendorFilterForm") VendorFilterForm vendorFilterForm, @ModelAttribute("fromSearch") String fromSearch) throws InventoryException {
        LOG.info("List Vendor page - {}", this.getClass().getSimpleName());
        model.addAttribute("vendorList", new ArrayList<>());
        if (StringUtils.isNotEmpty(fromSearch)) {
            model.addAttribute("vendorList", vendors);
            model.addAttribute("vendorFilterForm", vendorFilterForm);
        } else {
            List<Vendor> vendorList = vendorService.vendorList().stream().sorted(Comparator.comparing(Vendor::getCreatedDate).reversed()).collect(Collectors.toList());
            model.addAttribute("vendorList", vendorList);
            model.addAttribute("vendorFilterForm", new VendorFilterForm());
        }
        return LIST_VENDOR_PAGE;
    }

    @PostMapping(value = {"/vendor/search.mk"})
    public String vendorSearch(@ModelAttribute("vendorFilterForm") VendorFilterForm vendorFilterForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Search vendor - {}", this.getClass().getSimpleName());
        List<Vendor> vendorList = vendorService.filterVendor(vendorFilterForm).stream().sorted(Comparator.comparing(Vendor::getCreatedDate).reversed()).collect(Collectors.toList());
        redirectAttrs.addFlashAttribute("vendorList", vendorList);
        redirectAttrs.addFlashAttribute("vendorFilterForm", vendorFilterForm);
        redirectAttrs.addFlashAttribute("fromSearch", Boolean.TRUE.toString());
        return "redirect:/list/vendor.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/vendor.mk")
    public String vendor(ModelMap model) {
        LOG.info("Open Vendor page- {}", this.getClass().getSimpleName());
        VendorForm vendorForm = new VendorForm();
        model.addAttribute("vendorForm", vendorForm);
        return VENDOR_ADD_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping(value = "/save/vendor.mk")
    public String addNewVendor(@ModelAttribute("vendorForm") VendorForm vendorForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Open Add New Vendor - {}", this.getClass().getSimpleName());
        Vendor savedVendor = vendorService.saveVendor(vendorMapper.map(vendorForm));
        redirectAttrs.addFlashAttribute("vendorName", savedVendor.getVendorName());
        LOG.debug("Vendor Details {}", savedVendor.toString());
        return "redirect:/list/vendor.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/edit/{id}/vendor.mk")
    public String editVendorPage(ModelMap model, HttpServletRequest request, @PathVariable("id") String Id) throws InventoryException {
        LOG.info("Open Edit Vendor page - {}", this.getClass().getSimpleName());
        VendorForm vendorFormEdit = vendorMapper.remap(vendorService.findVendor(Long.valueOf(Id)));
        model.addAttribute("editVendorForm", vendorFormEdit);
        return VENDOR_EDIT_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/update/vendor.mk")
    public String updateVendor(@ModelAttribute("editVendorForm") VendorForm vendorForm, RedirectAttributes redirectAttrs) throws InventoryException {
        LOG.info("Update Vendor page - {}", this.getClass().getSimpleName());
        Vendor updateVendor = vendorService.findVendor(Long.valueOf(vendorForm.getId()));
        if (updateVendor.getVendorSignature() != null && vendorForm.getEditSignature() != null && ! vendorForm.getEditSignature().isEmpty()) {
            String[] uriPaths = updateVendor.getVendorSignature().split("/");
            String imagePathKey = uriPaths[uriPaths.length - 1];
            amazonS3Client.deleteObjectFromS3(imagePathKey);
            LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
        }
        Vendor savedVendor = vendorService.updateVendor(vendorMapper.map(vendorForm, updateVendor));
        redirectAttrs.addFlashAttribute("vendorName", vendorForm.getVendorName());
        redirectAttrs.addFlashAttribute("editVendorFlag", Boolean.TRUE);
        LOG.debug("Updated Vendor Details {}", savedVendor.toString());
        return "redirect:/list/vendor.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/remove/{id}/vendor.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeVendor(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove Vendor from Database - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            Vendor removedVendor = vendorService.removeVendor(Long.valueOf(id));
            LOG.info("vendor is removed? {}", (removedVendor != null && removedVendor.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            if (removedVendor.getVendorSignature() != null) {
                String[] uriPaths = removedVendor.getVendorSignature().split("/");
                String imagePathKey = uriPaths[uriPaths.length - 1];
                amazonS3Client.deleteObjectFromS3(imagePathKey);
                LOG.info("Old image is removed  in s3 by key {}", imagePathKey);
            }
            map.put("valid", Boolean.TRUE);
        } catch (InventoryException e) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/vendor/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingVendor(HttpServletRequest request) throws InventoryException {
        LOG.info("Checking if the vendor exists - {}", this.getClass().getSimpleName());
        boolean isVendorAvailable = vendorService.isVendorAvailable(request.getParameter("vendorCode").trim());
        LOG.info("vendorCode is available? {}", isVendorAvailable);
        return !isVendorAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/view/{id}/vendor.mk")
    public void viewVendorSignature(HttpServletResponse response, @PathVariable String id) throws InventoryException {
        LOG.info("View Vendor from dashboard - {}", this.getClass().getSimpleName());
        try {
            Vendor vendor = vendorService.findVendorWithImages(Long.valueOf(id));
            if (vendor != null && isNotEmpty(vendor.getVendorSignature())) {
                Path path = Paths.get(vendor.getVendorSignature());
                response.setContentType(Files.probeContentType(path));
                response.setContentLength(vendor.getImage().length);
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%s\"", id, path.getFileName()));
                response.setCharacterEncoding("UTF-8");
                copy(vendor.getImage(), response.getOutputStream());
            }
        } catch (IOException e) {
            throw new InventoryException("IO Exception occurred while viewing vendor image " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/export/vendor.mk")
    public void exportVendorListToExcel(@ModelAttribute("vendorFilterForm") VendorFilterForm vendorFilterForm, HttpServletResponse response) throws InventoryException {
        LOG.info("Export vendor details in Excel - {}", this.getClass().getSimpleName());
        List<Vendor> vendorList = vendorService.filterVendor(vendorFilterForm).stream().sorted(Comparator.comparing(Vendor::getCreatedDate).reversed()).collect(Collectors.toList());

        List<VendorExcelDTO> vendorExcelDTOList = new ArrayList<>();
        vendorList.forEach(vendor -> {
            VendorExcelDTO vendorExcelDTO = new VendorExcelDTO();
            vendorExcelDTO.setId(String.valueOf(vendor.getId()));
            vendorExcelDTO.setVendorName(vendor.getVendorName());
            vendorExcelDTO.setCompanyName(vendor.getCompanyName());
            vendorExcelDTO.setMobileNo(vendor.getMobileNo());
            vendorExcelDTO.setEmail(vendor.getEmail());
            vendorExcelDTO.setGstNo(vendor.getGstNo());
            vendorExcelDTO.setPanNo(vendor.getPanNo());
            vendorExcelDTO.setIfscCode(vendor.getIfscCode());
            vendorExcelDTO.setBankAcNo(vendor.getBankAcNo());
            vendorExcelDTO.setPinCode(vendor.getPinCode());
            vendorExcelDTO.setAddress(vendor.getAddress());
            vendorExcelDTO.setCity(vendor.getCity());
            vendorExcelDTO.setCompanyName(vendor.getCompanyName());
            vendorExcelDTOList.add(vendorExcelDTO);
        });

        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s_%d.xls\"", "VendorDetails", getInstant().getTime()));
        response.setCharacterEncoding("UTF-8");
        vendorExcelWriter.write(vendorExcelDTOList, response);
    }
}
